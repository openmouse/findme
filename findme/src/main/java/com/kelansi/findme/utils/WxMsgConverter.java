package com.kelansi.findme.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kelansi.findme.domain.RoomDetailInfo;

/**
 * 工具类用于将bean 转为微信可接受XML
 * 
 * @author Roy
 *
 */
public class WxMsgConverter {

	/**
	 * <xml>
		<ToUserName><![CDATA[toUser]]></ToUserName>
		<FromUserName><![CDATA[fromUser]]></FromUserName>
		<CreateTime>12345678</CreateTime>
		<MsgType><![CDATA[image]]></MsgType>
		<Image>
		<MediaId><![CDATA[media_id]]></MediaId>
		</Image>
	   </xml>
	 * 
	 * @param infos
	 * @return
	 */
	public static String convertToXmlStr(List<RoomDetailInfo> infos, String toUserName, String fromUserName){
		StringBuilder sbd = new StringBuilder("");
		Date date = new Date();
		List<String> medias = new ArrayList<String>();
		for(RoomDetailInfo info : infos){
			if(StringUtils.isNotBlank(info.getMediaIds())){
				medias.addAll(Arrays.asList(info.getMediaIds().split(",")));
			}
		}
		for(String media : medias){
			sbd.append("<xml>"); 
			sbd.append("<ToUserName>");
			sbd.append(toUserName);
			sbd.append("</ToUserName>");
			sbd.append("<FromUserName>");
			sbd.append(fromUserName);
			sbd.append("</FromUserName>");
			sbd.append("<CreateTime>");
			sbd.append(date.getTime());
			sbd.append("</CreateTime>");
			sbd.append("<MsgType><![CDATA[image]]></MsgType>");
			sbd.append("<Image>");
			sbd.append("<MediaId>");
			sbd.append(media);
			sbd.append("</MediaId>");
			sbd.append("</Image>");
			sbd.append("</xml>");
		}
		return sbd.toString();
	}
	
}
