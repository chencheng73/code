package com.zfpt.framework.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.alibaba.fastjson.JSON;

/**
 * 项目名称：ppsp_eonline 
 * 类名称：JsonUtils 
 * 类描述：用于转换输出对象 
 * 创建人：chens 
 * 创建时间：2014-9-4下午3:11:23 
 * @version
 */
public class JsonUtils {

	private static ObjectMapper objectMapper = new ObjectMapper();

	static{
		/**重写objectMapper 将null转换为"" **/
		 objectMapper.getSerializerProvider().setNullValueSerializer(
		    new JsonSerializer<Object>() {
			     public void serialize(Object value, JsonGenerator jsonGenerator,SerializerProvider serializerProvider) throws IOException,JsonProcessingException {
			      	jsonGenerator.writeString("");
			     }
		    }
		);
	}
	
	/**
	 * 方法名称: Object2JSON 
	 * 方法描述: 将对象转换为JSON 
	 * 返回类型: T
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T Object2JSON(Object object) {
		String jsonStr = null;
		try {
			jsonStr = objectMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonStr == null ? null : (T) jsonStr;
	}

	/**
	 * 方法名称: JSON2Object 
	 * 方法描述: 将JSON转换为对象 
	 * 返回类型: T
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T JSON2Object(String JSONStr, Class<T> claz) {
		Object object = null;
		try {
			object = objectMapper.readValue(JSONStr, claz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object != null ? (T) object : null;
	}
	
	/**
	 * 方法名称: JSON2List 
	 * 方法描述:  JSON 转换为List:   
	 * 返回类型: List<T> 
	 * @throws
	 */
	 public static <T> List<T> JSON2List(String jsonStr, Class<T> claz) {  
        List<T> result = null;  
        try{
        	JavaType t = objectMapper.getTypeFactory().
        			constructParametricType(List.class, claz);  
        	result = objectMapper.readValue(jsonStr, t);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}  
      return result==null?new ArrayList<T>():result;  
     }  

	/**
	 * 方法名称: Object2LinkMap 
	 * 方法描述: 将Object转换为LinkMap格式 
	 * 返回类型: T
	 * @throws
	 */
	public static MultiValueMap<String, Object> Object2LinkMap(Object object,
			String paramkey) {
		MultiValueMap<String, Object> linkMap = new LinkedMultiValueMap<String, Object>();
		String jsonResult = Object2JSON(object);
		if (jsonResult != null) {
			linkMap.add(paramkey, jsonResult);
		}
		return linkMap;

	}
	
    /**
     * 方法名称: toJSONString 
     * 方法描述: 将对象转换为String   
     * 返回类型: String 
     * @throws
     */
	public static final String toJSONString(Object object){
		return JSON.toJSONString(object);
	}
	
	/**
	 * 方法名称: toJSON 
	 * 方法描述: 将java对象转换为JSON   
	 * 返回类型: Object 
	 * @throws
	 */
	public static final Object toJSON(Object javaObject){
		return JSON.toJSON(javaObject);
	}
	
	
	 /**
     * 方法名称: parseObject 
     * 方法描述: 将String转为对象<T> 
     * 返回类型: T 
     * @throws
     */
	public static final <T> T parseObject(String jsonStr, Class<T> clazz){
		return JSON.parseObject(jsonStr,clazz);
	}
	
	/**
	 * 方法名称: parseArray 
	 * 方法描述: 将String转换List<T>:   
	 * 返回类型: List<T> 
	 * @throws
	 */
	public static final <T> List<T> parseArray(String jsonStr, Class<T> clazz){
		return JSON.parseArray(jsonStr, clazz);
	}


}
