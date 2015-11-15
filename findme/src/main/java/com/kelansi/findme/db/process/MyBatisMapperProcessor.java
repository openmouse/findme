package com.kelansi.findme.db.process;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component("myBatisMapperProcessor")
public class MyBatisMapperProcessor {

	/**
	 * 通过bean 讲值塞入 map 方便mybatis使用
	 * 
	 * @param map
	 * @param obj
	 * @param cls
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public Map<String, Object> processInsertMapper(Object obj, Class<?> cls) throws IllegalArgumentException, IllegalAccessException{
		Map<String, Object> map = new HashMap<String, Object>();
		Field[] fields = cls.getDeclaredFields();
		for(Field field : fields){
			boolean isStatic = Modifier.isStatic(field.getModifiers());
	        if(!isStatic) {
	        	map.put(field.getName(), field.get(obj));
	        }
		}
		return map;
	}
	
}
