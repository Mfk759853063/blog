package com.vbn.blog.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpSendShort {
	private static Logger logger= LoggerFactory.getLogger(HttpSendShort.class);
	
	private static int timeOut = 7000;
	
	/**
	 * 获取网络图片的io流
	 * @param Url
	 * @return
	 * @throws Exception
	 */
	public static InputStream getPicFromInternet(String Url) throws Exception{
    	URL url = new URL(Url);  
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");  
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);  
        //通过输入流获取图片数据  
        InputStream inStream = conn.getInputStream();  
        return inStream;
    }
	
	/**
	 * 调用接口获取cookie集合
	 * @param url
	 * @param content
	 * @param params
	 * @param sBuffer
	 * @return
	 * @throws Exception
	 */
	public static List<String> getCookiePost(String url, String content, String params,StringBuffer sBuffer) throws Exception{
		List<String> mapResult = new ArrayList<String>();
		logger.info(url + "===" + content);
		boolean bResult = false;
		OutputStreamWriter out = null;
		BufferedReader reader=null;
		HttpURLConnection httpConnection=null;
		try {
			// 拼凑get请求的URL字串
			String getURL = url + "?" + content;
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			httpConnection = (HttpURLConnection) getUrl
					.openConnection();
			httpConnection.setConnectTimeout(timeOut);
			// 设置从主机读取数据超时(单位:毫秒)
			httpConnection.setReadTimeout(20000);
			httpConnection.setRequestMethod("POST"); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 建立与服务器的连接，并未发送数据
			out = new OutputStreamWriter(httpConnection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
			// 发送数据到服务器并使用Reader读取返回的数据
            int retCode = httpConnection.getResponseCode();
	        // receive ,用recIn判断是否需要返回流形式
	        if(retCode >=400){
	        	 reader = new BufferedReader(new InputStreamReader(
	 					httpConnection.getErrorStream()));
	        }else{
	        	 reader = new BufferedReader(new InputStreamReader(
	  					httpConnection.getInputStream()));
	        }
	        
	        Map<String,List<String>> map = httpConnection.getHeaderFields();
        	for(String str : map.get("Set-Cookie")){
        		logger.info("Set-Cookie" + str);
        		mapResult.add(str);
        	}
	        
			String lines;
			while ((lines = reader.readLine()) != null) {
				sBuffer.append(lines);
			}
			logger.info("post返回"+sBuffer.toString());
			bResult = true;
		} catch (Exception e) {
			logger.info(content+"----"+params);
			logger.info("sendStrOfPost"+url, e);
			throw new RuntimeException("调用服务出错");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 断开连接
			httpConnection.disconnect();
		}

		return mapResult;
    }
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfPost(String url, String content, String params,StringBuffer sBuffer
			){
		return sendStrOfPost(url, content, params, sBuffer,null,null);
	}
	
	public static boolean sendStrOfPost(String url, String content, String params,StringBuffer sBuffer,Map<String,String> headerMap
			){
		return sendStrOfPost(url, content, params, sBuffer,headerMap,null);
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfPostJson(String url, String content, String params,StringBuffer sBuffer
			){
		return sendStrOfPostJson(url, content, params, sBuffer,null);
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfGet(String url, String content,StringBuffer sBuffer
			){
		return sendStrOfGet(url, content, sBuffer,null);
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfPost(String url, String content, String params,StringBuffer sBuffer,Map<String,String> headerMap,List<String> cookieList
			) {
		logger.info(url + "===" + content);
		boolean bResult = false;
		OutputStreamWriter out = null;
		BufferedReader reader=null;
		HttpURLConnection httpConnection=null;
		try {
			// 拼凑get请求的URL字串
			String getURL = url + "?" + content;
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			httpConnection = (HttpURLConnection) getUrl
					.openConnection();
			httpConnection.setConnectTimeout(timeOut);
			// 设置从主机读取数据超时(单位:毫秒)
			httpConnection.setReadTimeout(20000);
			httpConnection.setRequestMethod("POST"); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			if(headerMap!=null){
        		for(String key : headerMap.keySet()){
        			httpConnection.setRequestProperty(key,
        					headerMap.get(key));
        		}
            	
            }
			if(cookieList!=null){
				for(String cookie : cookieList){
					httpConnection.setRequestProperty("Cookie", cookie);
				}
			}
			// 建立与服务器的连接，并未发送数据
			out = new OutputStreamWriter(httpConnection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
			// 发送数据到服务器并使用Reader读取返回的数据
            int retCode = httpConnection.getResponseCode();
	        // receive ,用recIn判断是否需要返回流形式
	        if(retCode >=400){
	        	 reader = new BufferedReader(new InputStreamReader(
	 					httpConnection.getErrorStream()));
	        }else{
	        	 reader = new BufferedReader(new InputStreamReader(
	  					httpConnection.getInputStream()));
	        }
			String lines;
			while ((lines = reader.readLine()) != null) {
				sBuffer.append(lines);
			}
			logger.info("post返回"+sBuffer.toString());
			bResult = true;
		} catch (Exception e) {
			logger.info(content+"----"+params);
			logger.info("sendStrOfPost"+url, e);
			throw new RuntimeException("调用服务出错");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 断开连接
			httpConnection.disconnect();
		}

		return bResult;
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param params
	 *            发送json格式字符串
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfPostJson(String url, String content, String params,StringBuffer sBuffer,Map<String,String> headerMap
			) {
		logger.info(url + "===" + content + "===" + params);
		boolean bResult = false;
		OutputStreamWriter out = null;
//		BufferedReader reader=null;
		HttpURLConnection httpConnection=null;
		try {
			// 拼凑get请求的URL字串
			String getURL = url + "?" + content;
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			httpConnection = (HttpURLConnection) getUrl
					.openConnection();
			httpConnection.setConnectTimeout(timeOut);
			// 设置从主机读取数据超时(单位:毫秒)
			httpConnection.setReadTimeout(20000);
			httpConnection.setRequestMethod("POST"); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			if(headerMap!=null){
        		for(String key : headerMap.keySet()){
        			httpConnection.setRequestProperty(key,
        					headerMap.get(key));
        		}
            	
            }
			
			// 建立与服务器的连接，并未发送数据
			out = new OutputStreamWriter(httpConnection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
			// 发送数据到服务器并使用Reader读取返回的数据

            int retCode = httpConnection.getResponseCode();
	        // receive ,用recIn判断是否需要返回流形式
	        if(retCode >=400){
//	        	 reader = new BufferedReader(new InputStreamReader(
//	 					httpConnection.getErrorStream()));
	        	 sBuffer.append(readStream(httpConnection.getErrorStream(), httpConnection.getContentEncoding()==null?"":httpConnection.getContentEncoding()));
	        }else{
//	        	 reader = new BufferedReader(new InputStreamReader(
//	  					httpConnection.getInputStream()));
	        	sBuffer.append(readStream(httpConnection.getInputStream(), httpConnection.getContentEncoding()==null?"":httpConnection.getContentEncoding()));
	        }
			
//			String lines;
//			while ((lines = reader.readLine()) != null) {
//				sBuffer.append(lines);
//			}
			logger.info("POST返回"+sBuffer);
			bResult = true;
		} catch (Exception e) {
			logger.info(content+"----"+params);
			logger.info("sendStrOfPost"+url, e);
			throw new RuntimeException("调用服务出错");
		}finally{
//			try {
////				reader.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			// 断开连接
			httpConnection.disconnect();
		}

		return bResult;
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfPut(String url, String content, String params,StringBuffer sBuffer,Map<String,String> headerMap
			) {
		logger.info(url + "===" + content + "===" + params);
		boolean bResult = false;
		OutputStreamWriter out = null;
		BufferedReader reader=null;
		HttpURLConnection httpConnection=null;
		try {
			// 拼凑get请求的URL字串
			String getURL = url + "?" + content;
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			httpConnection = (HttpURLConnection) getUrl
					.openConnection();
			httpConnection.setConnectTimeout(timeOut);
			// 设置从主机读取数据超时(单位:毫秒)
			httpConnection.setReadTimeout(20000);
			httpConnection.setRequestMethod("PUT"); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			if(headerMap!=null){
        		for(String key : headerMap.keySet()){
        			httpConnection.setRequestProperty(key,
        					headerMap.get(key));
        		}
            	
            }
			
			// 建立与服务器的连接，并未发送数据
			out = new OutputStreamWriter(httpConnection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
			// 发送数据到服务器并使用Reader读取返回的数据

            int retCode = httpConnection.getResponseCode();
	        // receive ,用recIn判断是否需要返回流形式
	        if(retCode >=400){
	        	 reader = new BufferedReader(new InputStreamReader(
	 					httpConnection.getErrorStream()));
	        }else{
	        	 reader = new BufferedReader(new InputStreamReader(
	  					httpConnection.getInputStream()));
	        }
			
			String lines;
			while ((lines = reader.readLine()) != null) {
				sBuffer.append(lines);
			}
			logger.info("POST返回"+sBuffer);
			bResult = true;
		} catch (Exception e) {
			logger.info(content+"----"+params);
			logger.info("sendStrOfPut"+url, e);
			throw new RuntimeException("调用服务出错");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 断开连接
			httpConnection.disconnect();
		}

		return bResult;
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfDelete(String url, String content, String params,StringBuffer sBuffer,Map<String,String> headerMap
			) {
		logger.info(url + "===" + content + "===" + params);
		boolean bResult = false;
		OutputStreamWriter out = null;
		BufferedReader reader=null;
		HttpURLConnection httpConnection=null;
		try {
			// 拼凑get请求的URL字串
			String getURL = url + "?" + content;
			URL getUrl = new URL(getURL);
			// 根据拼凑的URL，打开连接，URL.openConnection()函数会根据
			// URL的类型，返回不同的URLConnection子类的对象，在这里我们的URL是一个http，因此它实际上返回的是HttpURLConnection
			httpConnection = (HttpURLConnection) getUrl
					.openConnection();
			httpConnection.setConnectTimeout(timeOut);
			// 设置从主机读取数据超时(单位:毫秒)
			httpConnection.setReadTimeout(20000);
			httpConnection.setRequestMethod("DELETE"); // POST方式提交数据
			httpConnection.setDoOutput(true);
			httpConnection.setRequestProperty("Content-Type",
					"application/json");
			httpConnection.setRequestProperty("Accept-Charset", "utf-8");
			if(headerMap!=null){
        		for(String key : headerMap.keySet()){
        			httpConnection.setRequestProperty(key,
        					headerMap.get(key));
        		}
            	
            }
			
			// 建立与服务器的连接，并未发送数据
			out = new OutputStreamWriter(httpConnection.getOutputStream());
            // 发送请求参数
            out.write(params);
            // flush输出流的缓冲
            out.flush();
			// 发送数据到服务器并使用Reader读取返回的数据

            int retCode = httpConnection.getResponseCode();
	        // receive ,用recIn判断是否需要返回流形式
	        if(retCode >=400){
	        	 reader = new BufferedReader(new InputStreamReader(
	 					httpConnection.getErrorStream()));
	        }else{
	        	 reader = new BufferedReader(new InputStreamReader(
	  					httpConnection.getInputStream()));
	        }
			
			String lines;
			while ((lines = reader.readLine()) != null) {
				sBuffer.append(lines);
			}
			logger.info("POST返回"+sBuffer);
			bResult = true;
		} catch (Exception e) {
			logger.info(content+"----"+params);
			logger.info("sendStrOfDelete"+url, e);
			throw new RuntimeException("调用服务出错");
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 断开连接
			httpConnection.disconnect();
		}

		return bResult;
	}
	
	/**
	 * 
	 * 
	 * @param url
	 *            发送地址
	 * @param content
	 *            发送内容 拼接在url后面
	 * @param sBuffer
	 *            接收到的数据
	 * @return boolean 发送是否成功
	 */
	public static boolean sendStrOfGet(String url, String content,StringBuffer sBuffer,Map<String,String> headerMap
			) {
		logger.info(url + "===" + content);
		boolean result = false;
        try {
            String urlNameString = url + "?" + content;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setAllowUserInteraction(true);
            if(headerMap!=null){
        		for(String key : headerMap.keySet()){
        			connection.setRequestProperty(key,
        					headerMap.get(key));
        		}
            	
            }
            // 建立实际的连接
            connection.connect();
            
            String encoding = connection.getContentEncoding();
            
            sBuffer.append(readStream(connection.getInputStream(), encoding==null?"":encoding));
            // 定义 BufferedReader输入流来读取URL的响应
            logger.info("GET返回"+sBuffer);
            result = true;
        } catch (Exception e) {
            logger.info(content);
            logger.info("sendStrOfGet"+url, e);
        }
        return result;
	}
	
	public static String requestPost(String url,String token) {
		return requestPost(url,null,"application/x-www-form-urlencoded",token);
	}
	
	public static String requestPost(String url,String bodyContent,String contentType,String token) {	
		StringBuffer sBuffer=new StringBuffer();
		BufferedReader reader=null;
		OutputStreamWriter out=null;
		HttpURLConnection httpConnection=null;
		try {			
			httpConnection=getHttpURLConnection(url, contentType, token);			
			if(!StringUtils.isEmpty(bodyContent)){
				out = new OutputStreamWriter(httpConnection.getOutputStream());
	            out.write(bodyContent);
	            out.flush();
			}			
			
			// 发送数据到服务器并使用Reader读取返回的数据
			reader = new BufferedReader(new InputStreamReader(
					httpConnection.getInputStream()));
			String lines;			
			while ((lines = reader.readLine()) != null) {
				sBuffer.append(lines);
			}
		} catch (Exception e) {
			logger.info("requestPost", e);
		}finally{
			try {
				reader.close();				
				if(null!=out){
					out.close();
				}				
			} catch (Exception e) {
				logger.info("requestPost", e);
			}
			// 断开连接
			if(null!=httpConnection){
				httpConnection.disconnect();
			}
			
		}

		return sBuffer.toString();
	}
	
	private static HttpURLConnection getHttpURLConnection(String url,String contentType,String token) throws Exception{
		URL getUrl = new URL(url);			
		HttpURLConnection httpConnection = (HttpURLConnection) getUrl.openConnection();
		httpConnection.setConnectTimeout(timeOut);
		// 设置从主机读取数据超时(单位:毫秒)
		httpConnection.setReadTimeout(10000);
		httpConnection.setRequestMethod("POST"); // POST方式提交数据
		httpConnection.setDoOutput(true);
		httpConnection.setRequestProperty("Content-Type",contentType);	
		httpConnection.setRequestProperty("Authorization", token);		
		return httpConnection;
		
	}
	
	public static int requestPostResultStatusCode(String url,String bodyContent,String contentType,String token) throws Exception{
		int code=200;
		OutputStreamWriter out=null;
		HttpURLConnection httpConnection=null;
		try {	
			httpConnection=getHttpURLConnection(url, contentType, token);			
			if(!StringUtils.isEmpty(bodyContent)){
				out = new OutputStreamWriter(httpConnection.getOutputStream());
	            out.write(bodyContent);
	            out.flush();
			}			
			code=httpConnection.getResponseCode();			
		} catch (Exception e) {
			logger.info("requestPost", e);
		}finally{
			try {							
				if(null!=out){
					out.close();
				}				
			} catch (Exception e) {
				logger.info("requestPost", e);
			}
			// 断开连接
			if(null!=httpConnection){
				httpConnection.disconnect();
			}
		}		
		return code;
	}
	
	private static String readStream(InputStream inputStream, String encoding) throws Exception {
        StringBuffer buffer = new StringBuffer();
        InputStreamReader inputStreamReader = null;
        GZIPInputStream gZIPInputStream = null;
        if ("gzip".equals(encoding.toLowerCase())) {
            gZIPInputStream = new GZIPInputStream(inputStream);
            inputStreamReader = new InputStreamReader(gZIPInputStream, "UTF-8");
        } else {
            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
        }
        char[] c = new char[1024];
        int lenI;
        while ((lenI = inputStreamReader.read(c)) != -1) {
            buffer.append(new String(c, 0, lenI));
        }
        if (inputStream != null) {
            inputStream.close();
        }
        if (gZIPInputStream != null) {
            gZIPInputStream.close();
        }
        return buffer.toString();


    }
	
}
