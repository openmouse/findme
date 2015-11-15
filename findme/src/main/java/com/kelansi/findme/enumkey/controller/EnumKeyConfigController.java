package com.kelansi.findme.enumkey.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelansi.findme.common.Message;
import com.kelansi.findme.domain.EnumEntryBean;
import com.kelansi.findme.enumkey.dao.EnumKeyConfigMapper;
import com.kelansi.findme.enumkey.service.EnumKeyConfigService;
import com.kelansi.findme.exception.CommonException;
import com.kelansi.findme.search.dao.SearchMapper;

@Controller
@RequestMapping(value = "/enumkey/config")
public class EnumKeyConfigController {

	@Autowired
	private SearchMapper searchMapper;
	
	@Autowired
	private EnumKeyConfigService enumKeyConfigService;
	
	@Autowired
	private EnumKeyConfigMapper enumKeyConfigMapper;
	
	@RequestMapping(value = "/add")
	public String add(ModelMap map, String msg){
		map.put("wordMappings", searchMapper.findAllWordMappings());
		String message = null;
		if("success".equals(msg)){
			message = "操作成功";
		}else if("invalid".equals(msg)){
			message = "参数错误";
		}else if(ERROR_MESSAGE.equals(msg)){
			message = "操作错误";
		}else{
			message = "";
		}
		map.put("message", message);
		return "config/add";
	}
	
	@RequestMapping(value = "/retrieveEnumKey")
	@ResponseBody
	public List<EnumEntryBean> retrieveEnumKey(Integer enumNum){
		if(enumNum == null){
			return Collections.emptyList();
		}
		return enumKeyConfigService.getEnumEntryBeanByNum(enumNum);
	}
	
//	@RequestMapping(value = "/delete")
//	@ResponseBody
//	public Message delete(){
//		
//	}
	
	private static final int FLOOR_HEIGHT = 3;
	private static final int SIZE = 5;
	private static final String ERROR_MESSAGE = "error";
	
	@RequestMapping(value = "/delete")
	public String delete(Long id){
		enumKeyConfigMapper.delete(id);
		return "redirect:add.htm?msg=success";
	}
	
	@RequestMapping(value = "/save")
	public String save(EnumEntryBean enumEntry, RedirectAttributes re){
		try{
			this.buildEnumEntry(enumEntry);
			if(!validateEnumEntry(enumEntry)){
				return "redirect:add.htm?msg=invalid";
			}
			if(enumEntry.getEnumNum() != FLOOR_HEIGHT && enumEntry.getEnumNum() != SIZE){
				enumEntry.setType(0);
			}else{
				enumEntry.setType(1);
			}
			//经销商字段
			if(enumEntry.getEnumValue() == -1){
				Integer maxEnumValue = enumKeyConfigMapper.getMaxEnumValueByNum(enumEntry.getEnumNum());
				if(maxEnumValue != null){
					enumEntry.setEnumValue(maxEnumValue + 1);
				}else{
					enumEntry.setEnumValue(1);
				}
				
			}
			enumKeyConfigMapper.save(enumEntry);
		}catch(CommonException e){
			return "redirect:add.htm?msg=" + ERROR_MESSAGE;
		}catch(Exception e){
			return "redirect:add.htm?msg=" + ERROR_MESSAGE;
		}
		return "redirect:add.htm?msg=success";
	}
	
	private void buildEnumEntry(EnumEntryBean enumEntry){
		if(enumEntry.getEnumValue() != -1){
			Integer enumNum = enumKeyConfigMapper.getEnumNumById(new Long(enumEntry.getEnumNum()));
			enumEntry.setEnumNum(enumNum);
		}
	}
	
	/**
	 * 验证bean合法性
	 * 
	 * @param enumEntry
	 * @return
	 */
	private boolean validateEnumEntry(EnumEntryBean enumEntry){
		if(StringUtils.isBlank(enumEntry.getEnumKey()) && (enumEntry.getNumBegin() == null || enumEntry.getNumEnd() == null)){
			return false;
		}
		if(FLOOR_HEIGHT == enumEntry.getEnumNum() || SIZE == enumEntry.getEnumNum()){
			if((enumEntry.getNumBegin() == null || enumEntry.getNumEnd() == null)){
				return false;
			}
			if(enumEntry.getNumBegin().compareTo(enumEntry.getNumEnd()) != -1){
				return false;
			}
		}else{
			if(StringUtils.isBlank(enumEntry.getEnumKey())){
				return false;
			}
		}
		return true;
	}
	
}
