package com.kelansi.findme.imgUpload.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.imgUpload.service.ImgUploadService;

@Controller
@RequestMapping(value = "/imageUpload")
public class ImgUploadController {

	private static final Logger logger = LoggerFactory.getLogger(ImgUploadController.class);
	
	@Resource(name = "imgUploadServiceImpl")
	private ImgUploadService imgUploadService; 
	
	//TODO 批量导入商品图片
    /**
     * @param request
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Message batchUploadImages(HttpServletRequest request) {
        DefaultMultipartHttpServletRequest req=(DefaultMultipartHttpServletRequest)request;
        Collection<MultipartFile> files=req.getFileMap().values();
        List<MultipartFile> uploadList=new ArrayList<MultipartFile>(files);
        try {
            if (CollectionUtils.isNotEmpty(uploadList)) {
            	imgUploadService.batchUpload(uploadList);
            	return Message.SUCCESS_MESSAGE;
            }
        } catch (Exception e) {
        	logger.error(e.getMessage());
        	return Message.error(e.getMessage());
        }
        return Message.warn("未上传任何图片");
    }
	
}
