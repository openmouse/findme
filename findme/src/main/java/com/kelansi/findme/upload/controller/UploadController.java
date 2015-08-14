package com.kelansi.findme.upload.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.common.ResultData;
import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.upload.service.UploadService;
import com.kelansi.findme.utils.excel.ExcelReader;
import com.kelansi.findme.utils.excel.ExcelReaderFactory;

@Controller
@RequestMapping(value="/upload")
public class UploadController {
	
	@Autowired
	private UploadService uploadService;

	@RequestMapping(value="/index")
	public String list(){
		return "upload";
	}
	 /*@RequestMapping(value="/upload", method = RequestMethod.POST)
	    public @ResponseBody LinkedList<FileMeta> upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		 LinkedList<FileMeta> files = new LinkedList<FileMeta>();
		    FileMeta fileMeta = null;
	        //1. build an iterator
	         Iterator<String> itr =  request.getFileNames();
	         MultipartFile mpf = null;
	 
	         //2. get each file
	         while(itr.hasNext()){
	 
	             //2.1 get next MultipartFile
	             mpf = request.getFile(itr.next()); 
	             System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());
	 
	             //2.2 if files > 10 remove the first from the list
	             if(files.size() >= 10)
	                 files.pop();
	 
	             //2.3 create new fileMeta
	             fileMeta = new FileMeta();
	             fileMeta.setFileName(mpf.getOriginalFilename());
	             fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
	             fileMeta.setFileType(mpf.getContentType());
	 
	             try {
	                fileMeta.setBytes(mpf.getBytes());
	 
	                 // copy file to local disk (make sure the path "e.g. D:/temp/files" exists)            
	                 FileCopyUtils.copy(mpf.getBytes(), new FileOutputStream("D:/temp/files/"+mpf.getOriginalFilename()));
	 
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	             //2.4 add to files
	             files.add(fileMeta);
	         }
	        // result will be like this
	        // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
	        return files;
	    }*/
	@RequestMapping(value="/upload", method = RequestMethod.POST)
    public @ResponseBody ResultData upload(MultipartHttpServletRequest mulRequest, HttpServletResponse response) {
		 Iterator<String> itr =  mulRequest.getFileNames();
         MultipartFile file = null;
         ResultData result=new ResultData();
         //2. get each file
         while(itr.hasNext()){
 
             //2.1 get next MultipartFile
        	 file = mulRequest.getFile(itr.next()); 
             try {
            	 ExcelReader excel = ExcelReaderFactory.getExcelReader(file.getOriginalFilename(), file.getInputStream());
            	 uploadService.importByExcel(excel);
            	 excel.close();
            	 result.setMessage(Message.SUCCESS_MESSAGE);
             } catch (IOException|CommonException e) {
            	 result.setMessage(Message.ERROR_MESSAGE);
            	 
             } 
         }
         return result;
	}
	 
	 public class FileMeta {
		 
		    private String fileName;
		    private String fileSize;
		    private String fileType;
		 
		    private byte[] bytes;

			public String getFileName() {
				return fileName;
			}

			public void setFileName(String fileName) {
				this.fileName = fileName;
			}

			public String getFileSize() {
				return fileSize;
			}

			public void setFileSize(String fileSize) {
				this.fileSize = fileSize;
			}

			public String getFileType() {
				return fileType;
			}

			public void setFileType(String fileType) {
				this.fileType = fileType;
			}

			public byte[] getBytes() {
				return bytes;
			}

			public void setBytes(byte[] bytes) {
				this.bytes = bytes;
			}
		 
		         //setters & getters
		}
}
