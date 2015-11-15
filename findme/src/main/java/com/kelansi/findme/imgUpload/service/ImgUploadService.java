package com.kelansi.findme.imgUpload.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ImgUploadService {

	void batchUpload(List<MultipartFile> uploadList)  throws IllegalStateException, IOException;

}
