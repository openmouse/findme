package com.kelansi.findme.upload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/upload")
public class UploadController {

	@RequestMapping(value="/index")
	public String list(){
		return "upload";
	}
}
