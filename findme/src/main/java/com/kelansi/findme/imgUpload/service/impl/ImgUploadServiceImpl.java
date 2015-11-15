package com.kelansi.findme.imgUpload.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.kelansi.findme.common.Setting;
import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.imgUpload.service.ImgUploadService;
import com.kelansi.findme.plugin.HttpPlugin;
import com.kelansi.findme.upload.dao.UploadMapper;
import com.kelansi.findme.utils.HttpUtils;
import com.kelansi.findme.wx.api.WxRequestApiBean;
import com.kelansi.findme.wx.api.token.TokenAccessApiProcessor;


@Service("imgUploadServiceImpl")
public class ImgUploadServiceImpl implements ImgUploadService,ServletContextAware {

	private static final Logger logger = LoggerFactory.getLogger(ImgUploadServiceImpl.class);
	
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	
	/** 微信存储 */
	private static final String MEDIA_ID = "media_id";
	
	@Autowired
	private UploadMapper uploadMapper;
	
	/** errmsg */
	private static final String ERRMSG = "errmsg";

	/** servletContext */
	private ServletContext servletContext;
	
	@Autowired
	private TokenAccessApiProcessor tokenProcessor;
	
	/* (non-Javadoc)
     * @see com.hengtiansoft.xinyunlian.service.ProductImageService#batchUpload(java.util.List, boolean)
     */
    @Override
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
    public void batchUpload(List<MultipartFile> imageFiles) throws IllegalStateException, IOException {
        Assert.notEmpty(imageFiles);
        //多媒体Id和 图片路径 对应关系
        final Map<String, String> mediaIdMap = new HashMap<String, String>();
        for(MultipartFile multipartFile:imageFiles){
            String uploadPath = Setting.imageUploadPath;
            String fileName=multipartFile.getOriginalFilename();
            if(StringUtils.isNotBlank(fileName)){
                //去掉后缀名
                int index=fileName.trim().lastIndexOf(".");
                fileName=fileName.substring(0, index);
            }
            //String uuid = UUID.randomUUID().toString();
            String filePath = uploadPath + fileName + "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String sourcePath = servletContext.getRealPath("/") + filePath;
            File pictFile = new File(sourcePath);
            //上传图片
//            File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + uuid + "-source." + DEST_EXTENSION);
//            if (!tempFile.getParentFile().exists()) {
//                tempFile.getParentFile().mkdirs();
//            }
            if (!pictFile.getParentFile().exists()) {
            	pictFile.getParentFile().mkdirs();
            }
            multipartFile.transferTo(pictFile); 
            Map<String, String> map = new HashMap<String, String>();
            map.put("access_token", tokenProcessor.getAccessToken(new WxRequestApiBean()));
            map.put("type", "image");
            HttpUtils httpUtils = new HttpUtils();
            Map<String, String> returnMap = httpUtils.postMultipart(sourcePath, map);
            //上传发生错误
            if(returnMap.get(ERRMSG) != null){
            	throw new CommonException(returnMap.get(ERRMSG));
            }
            if(returnMap.get(MEDIA_ID) != null){
            	 mediaIdMap.put(filePath.substring(1, filePath.length()), returnMap.get(MEDIA_ID));
            }
        }
        executor.execute(new Runnable() {
			@Override
			public void run() {
				for(Entry<String, String> entry : mediaIdMap.entrySet()){
		        	Map<String, Object> params = new HashMap<String, Object>();
		        	params.put("mediaId", entry.getValue());
		        	params.put("picturePathStr", "%"+entry.getKey()+"%");
		        	uploadMapper.updateMediaId(params);
		        }
			}
		});
    }
    
    @Resource(name = "httpPlugin")
    private HttpPlugin httpPlugin;
    
    private void addTask(final String sourcePath, final File tempFile, final String contentType) {
		try {
			try {
				httpPlugin.upload(sourcePath, tempFile, contentType);
			} finally {
				FileUtils.deleteQuietly(tempFile);
			}			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
