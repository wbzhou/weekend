package com.littleWeekend.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.OptionsMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
@author :zhouwenbin
@time   :2018年12月15日
@comment:
**/
public final class HttpTookit
{
	private static final Logger log=LogManager.getLogger(HttpTookit.class);	

	private static MultiThreadedHttpConnectionManager connectionManger = new MultiThreadedHttpConnectionManager();

	private static class TrustAnyTrustManager implements X509TrustManager
	{
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException
		{
		}

		public X509Certificate[] getAcceptedIssuers()
		{
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier
	{
		public boolean verify(String hostname, SSLSession session)
		{
			return true;
		}
	}

	/**
	 * 执行一个HTTP GET请求，返回请求响应的HTML
	 * 
	 * @param url
	 *            请求的URL地址
	 * @param queryString
	 *            请求的查询参数,可以为null
	 * @param charset
	 *            字符集
	 * @param pretty
	 *            是否美化
	 * @return 返回请求响应的HTML
	 */
	public synchronized static String doGetHTML(String url, String queryString,
			String charset, boolean pretty) throws Exception
	{
		StringBuffer response = new StringBuffer();
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		try
		{
			if (StringUtils.isNotBlank(queryString))
			{
				// 对get请求参数做了http请求默认编码，好像没有任何问题，汉字编码后，就成为%式样的字符串
				method.setQueryString(URIUtil.encodeQuery(queryString));
			}

			method.setFollowRedirects(true);
			method.setRequestHeader("Activity-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
			client.getHttpConnectionManager().getParams().setSoTimeout(10000);
			client.executeMethod(method);
			if (method.getStatusCode() == HttpStatus.SC_OK)
			{
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(method.getResponseBodyAsStream(),
								charset));
				String line;
				while ((line = reader.readLine()) != null)
				{
					if (pretty)
						response.append(line).append(
								System.getProperty("line.separator"));
					else
						response.append(line);
				}
				reader.close();
			}
		} catch (URIException e)
		{
			log.error("执行HTTP Get请求时，编码查询字符串“" + queryString + "”发生异常！", e);
		} catch (IOException e)
		{
			log.error("执行HTTP Get请求" + url + "时，发生异常！", e);
		} finally
		{
			if (method != null)
			{
				method.releaseConnection();
			}
			if (client != null)
			{
				((SimpleHttpConnectionManager) client
						.getHttpConnectionManager()).shutdown();
				client = null;
			}
		}
		return response.toString();
	}

	public static String getRequestStr(String url)
	{
		String resp = "";
		// int code = 0;
		GetMethod get = new GetMethod(url);
		get.addRequestHeader("Authorization",
				"Basic NEFDOTFFRUMyMkEyQTZFNUM5NUY3RUEyNjQxNkI2NzE=");// Q0Y4NjBGMTgxRDFDMjUyMEY1OTIwMTE1RkUwNkVCOTc=
		get.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");
		HttpClient httpClient = new HttpClient();
		try
		{
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(100000);
			httpClient.executeMethod(get);
			resp = get.getResponseBodyAsString();
			get.releaseConnection();

		} catch (HttpException e)
		{
			e.printStackTrace();
			log.error("请求异常");
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (httpClient != null)
			{
				((SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		return resp;
	}

	public static String getRequestStr(String url, String encode)
	{
		String resp = "";
		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
			connection.connect();

			InputStream fin = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			StringBuffer buffer = new StringBuffer();
			String temp = null;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
			}
			resp = buffer.toString();
		} catch (IOException e)
		{
			log.error("", e);
		}
		return resp;
	}

	public static String sendGetRequest(String url)
	{
		if(url.startsWith("https"))
			return sendGetHttpsRequest(url);
		log.info("send url------>" + url);
		String resp = "";
		// int code = 0;
		// net.URLEncoder.encode();
		GetMethod get = new GetMethod(url);
		// get.addRequestHeader("Accept","text/html, application/xhtml+xml, */*");
		// get.addRequestHeader("Accept-Encoding","gzip, deflate");
		get.addRequestHeader("Accept-Language", "zh-CN");
		get.addRequestHeader("Connection", "Keep-Alive");
		get.addRequestHeader("Activity-Agent",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		// get.addRequestHeader("Content-Type",
		// "application/x-www-form-urlencoded;charset=UTF-8");
		HttpClient httpClient = new HttpClient();
		try
		{
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(20000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(30000);
			httpClient.executeMethod(get);
			resp = get.getResponseBodyAsString();
			// System.out.println("code="+code);
			get.releaseConnection();
		} catch (HttpException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			((SimpleHttpConnectionManager) httpClient
					.getHttpConnectionManager()).shutdown();
		}
		return resp;
	}
	
	public static String sendGetHttpsRequest(String url)
	{
		log.info("send url------>" + url);
		URL u = null;
		SSLContext sc;
		HttpsURLConnection con = null;

		// 尝试发送请求
		BufferedReader in = null;
		 String result = "";
		try
		{
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			u = new URL(url);
			con = (HttpsURLConnection) u.openConnection();
			con.setSSLSocketFactory(sc.getSocketFactory());
			con.setHostnameVerifier(new TrustAnyHostnameVerifier());
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
//			con.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
			
			// 设置通用的请求属性
			con.setRequestProperty("accept", "*/*");
			con.setRequestProperty("connection", "Keep-Alive");
			con.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
			con.connect();
			// 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
//            System.out.println("获取的结果为："+result);
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
			try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                //e2.printStackTrace();
            }
		}

		return result;
	}
	public static String sendRefGetRequest(String url, String ref)
	{
		log.info("send url------>" + url);
		String resp = "";
		// int code = 0;
		// net.URLEncoder.encode();
		GetMethod get = new GetMethod(url);
		get.addRequestHeader("Accept","*/*");
		// get.addRequestHeader("Accept-Encoding","gzip, deflate");
		get.addRequestHeader("Referer", ref);
		get.addRequestHeader("Accept-Language", "zh-CN");
		get.addRequestHeader("Connection", "Keep-Alive");
		get.addRequestHeader("Activity-Agent",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
		// get.addRequestHeader("Content-Type",
		// "application/x-www-form-urlencoded;charset=UTF-8");
		HttpClient httpClient = new HttpClient();
		try
		{
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(20000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(30000);
			httpClient.executeMethod(get);
			resp = get.getResponseBodyAsString();
			// System.out.println("code="+code);
			get.releaseConnection();
		} catch (HttpException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			((SimpleHttpConnectionManager) httpClient
					.getHttpConnectionManager()).shutdown();
		}
		return resp;
	}

	public static String getRequestWithAuthcode(String url, String authcode)
	{
		String resp = "";
		GetMethod get = new GetMethod(url);
		// get.addRequestHeader("Authorization",
		// "Basic MTZDMjg4QzA3MTI3MUQ4N0YzQ0I1REQwMUY3RjI4RUI=");
		get.addRequestHeader("Authorization", authcode);
		get.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=gbk");// gbk

		HttpClient httpClient = new HttpClient();
		try
		{
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(100000);
			httpClient.executeMethod(get);
			resp = get.getResponseBodyAsString();
			get.releaseConnection();

		} catch (HttpException e)
		{
			log.error("", e);
		} catch (IOException e)
		{
			log.error("", e);
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (httpClient != null)
			{
				((SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		return resp;

	}

	public static String getMethod(String url)
	{
		String resp = "";
		// int code = 0;
		GetMethod get = new GetMethod(url);
		get.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");
		HttpClient httpClient = new HttpClient();
		try
		{
			httpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(50000);
			httpClient.getHttpConnectionManager().getParams()
					.setSoTimeout(100000);
			httpClient.executeMethod(get);
			resp = get.getResponseBodyAsString();
			get.releaseConnection();

		} catch (HttpException e)
		{
			log.error("", e);
		} catch (IOException e)
		{
			log.error("", e);
		} finally
		{
			if (httpClient != null)
			{
				((SimpleHttpConnectionManager) httpClient
						.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		return resp;
	}

	public static String sendGet(String url, String param)
	{
		String result = "";
		BufferedReader in = null;
		try
		{
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 设置超时时间
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet())
			{
				log.info(key + "---&gt;" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
			{
				result += line;
			}
		} catch (Exception e)
		{
			log.error("", e);
		}
		// 使用finally块来关闭输入流
		finally
		{
			try
			{
				if (in != null)
				{
					in.close();
				}
			} catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 请求经纬度定位信息数据（注：请求返回结果GZIP压缩）
	 * 
	 * @param urls
	 * @return
	 */
	public static String doPostByJson(String urls)
	{
		URL url;
		String res = "";
		try
		{
			url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			GZIPInputStream gInputStream = new GZIPInputStream(
					connection.getInputStream());

			byte[] by = new byte[1024];
			int len = 0;
			StringBuffer sb = new StringBuffer("");
			while ((len = gInputStream.read(by)) != -1)
			{
				sb.append(new String(by, 0, len, "UTF-8"));
			}
			gInputStream.close();
			res = sb.toString();
			log.info(res);
			// 断开连接
			connection.disconnect();

		} catch (IOException e)
		{
			log.error("请求定位信息异常。。。", e);
			res = "";
		}

		return res;
	}

	public static String sendHttpPost(String url, String inputString,
			String _code)
	{
		if(_code.equals("json")){
			return sendPostByJson(url, getJsonStr(inputString));
		}
		StringBuffer buf = new StringBuffer();
		PostMethod post = new PostMethod(url);
		HttpClient httpClient = new HttpClient(connectionManger);
		httpClient.getHttpConnectionManager().getParams()
				.setConnectionTimeout(10000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
		try
		{
			post.setRequestHeader("Connection", "Keep-Alive");
			post.addRequestHeader("Content-Type",
					"application/json;charset=UTF-8");
			byte[] b = inputString.getBytes("UTF-8");
			InputStream is = new ByteArrayInputStream(b, 0, b.length);
			RequestEntity re = new InputStreamRequestEntity(is, b.length,
					"text/json; charset=utf-8");

			// 发送请求字符串到服务器
			post.setRequestEntity(re);

			httpClient.executeMethod(post);
			// 从服务器上取回响应
			BufferedReader in = new BufferedReader(new InputStreamReader(
					post.getResponseBodyAsStream(), "UTF-8"));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null)
			{
				buf.append(inputLine);
			}
			in.close();
		} catch (MalformedURLException ex)
		{
			log.error(url, ex);
			ex.printStackTrace();
		} catch (IOException ex)
		{
			log.error(url, ex);
			ex.printStackTrace();
		} finally
		{
			post.releaseConnection();
		}
		return buf.toString();
	}

	/**
	 * @param inputString
	 * @return
	 */
	private static String getJsonStr(String inputString) {
		try {
			String array[]=inputString.split("&");
			JSONObject reqJson=new JSONObject();
			for(int i=0;i<array.length;i++){
				
				String ent[]=array[i].split("=");
				reqJson.put(ent[0], ent[1]);
			}
			return reqJson.toJSONString();
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 描述：post请求针对AA特殊字符转义问题 <br/>
	 * 
	 * @param url
	 * @param params
	 * @return <br/>
	 *         创建日期：2012-10-30 <br/>
	 */
	public static String postRequestAA(String url, Map<String, String> params)
	{
		String resp = "";
		PostMethod post = new PostMethod(url);

		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=UTF-8");

		Iterator<Map.Entry<String, String>> iter = params.entrySet().iterator();
		while (iter.hasNext())
		{
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iter
					.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			post.addParameter(key, val);
		}

		HttpClient httpclient = new HttpClient();
		try
		{
			httpclient.executeMethod(post);
			resp = post.getResponseBodyAsString();
			post.releaseConnection();
		} catch (HttpException e)
		{
			log.error("HttpException" + e);
		} catch (IOException e)
		{
			log.error("I/O异常" + e);
		}
		return resp;
	}

	public static String sendPost(String url, Map<String, String> params)
	{
		if (url.startsWith("https"))
		{
			return sendHTTPSPostRequest(url, params);
		}
		return sendPostRequest(url, params);
	}

	/**
	 * 发送http post请求
	 */
	public static String sendPostRequest(String url, Map<String, String> params)
	{
		URL u = null;
		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null)
		{
			for (Entry<String, String> e : params.entrySet())
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}

		log.info("请求的参数" + JSONObject.toJSONString(params));
		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setConnectTimeout(2*60*1000);//2分钟
			con.setReadTimeout(2*60*1000);
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}
	
	/**
	 * 发送http post请求
	 */
	public static String sendPostRequest(String url, Map<String, String> params,Map<String,String> header)
	{
		URL u = null;
		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null)
		{
			for (Entry<String, String> e : params.entrySet())
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}

		log.info("请求的参数" + sb.toString());
//		System.out.println("请求的参数" + sb.toString());
		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			handleHeader(con,header);//设置头信息
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}
	
	public static void handleHeader(HttpURLConnection con,Map<String,String> header){
		if(null!=header){
			for(Map.Entry<String, String> entry: header.entrySet()){
				con.addRequestProperty(entry.getKey(),entry.getValue());
			}
		}
	}
	public static String sendPostRequest(String url, String params)
	{
		URL u = null;
		HttpURLConnection con = null;

		log.info("请求的参数" + params);
		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(params);
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}

	/**
	 * 发送https post请求
	 */
	public static String sendHTTPSPostRequest(String url,
			Map<String, String> params)
	{
		URL u = null;
		SSLContext sc;
		HttpsURLConnection con = null;
		// BufferedReader in = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null)
		{
			for (Entry<String, String> e : params.entrySet())
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		log.info("请求的参数" + sb.toString());
		// 尝试发送请求
		try
		{
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			u = new URL(url);
			con = (HttpsURLConnection) u.openConnection();
			con.setSSLSocketFactory(sc.getSocketFactory());
			con.setHostnameVerifier(new TrustAnyHostnameVerifier());
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}
	
	public static String sendPostByJson(String url, String params)
	{
		URL u = null;
		HttpURLConnection con = null;

//		log.info("请求的参数" + params);
		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type","application/json");
			con.setConnectTimeout(5*1000);//3分钟
			con.setReadTimeout(5*1000);
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(params);
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}
		// 读取返回内容
				StringBuffer buffer = new StringBuffer();
				try
				{
					BufferedReader br = new BufferedReader(new InputStreamReader(
							con.getInputStream(), "UTF-8"));
					String temp;
					while ((temp = br.readLine()) != null)
					{
						buffer.append(temp);
						buffer.append("\n");
					}
				} catch (Exception e)
				{
					log.error("", e);
				}

				return buffer.toString();
	}

	public static String sendHTTPSPostRequest(String url, String params)
	{
		URL u = null;
		SSLContext sc;
		HttpsURLConnection con = null;

		log.info("请求的参数" + params);
		// 尝试发送请求
		try
		{
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
					new java.security.SecureRandom());

			u = new URL(url);
			con = (HttpsURLConnection) u.openConnection();
			con.setSSLSocketFactory(sc.getSocketFactory());
			con.setHostnameVerifier(new TrustAnyHostnameVerifier());
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(params);
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}

	/**
	 * Httpclient检查指定url支持的HTTP请求类型（POST/GET/PUT/DELETE）
	 * 
	 * @param url
	 * @return
	 */
	public static String checkSupportMethods(String url)
	{
		HttpClient client = new HttpClient();
		OptionsMethod options = new OptionsMethod(url);
		StringBuffer sb = new StringBuffer();
		try
		{
			int resCode = client.executeMethod(options);

			if (resCode == HttpStatus.SC_OK)
			{
				InputStream ins = options.getResponseBodyAsStream();
				byte[] b = new byte[1024];
				int r_len = 0;
				while ((r_len = ins.read(b)) > 0)
				{

					sb.append(new String(b, 0, r_len, options
							.getResponseCharSet()));
				}
			} else
			{
				log.error("Response Code: " + resCode);
			}

			Enumeration<?> allowedMethods = options.getAllowedMethods();
			while (allowedMethods.hasMoreElements())
			{
				log.info(allowedMethods.nextElement().toString());
			}
			options.releaseConnection();
		} catch (HttpException e)
		{
			log.error("", e);
		} catch (IOException e)
		{
			log.error("", e);
		}
		return sb.toString();
	}

	/**
	 * 发送http delete请求，不能再报文体中加参数 <a href
	 * ="http://houxiyang.com/archives/131/">戳这里</a>
	 */
	public static String sendDeleteHttpRequest(String url)
	{
		HttpClient client = new HttpClient();
		DeleteMethod method = new DeleteMethod(url);
		InputStream ins = null;

		HttpMethodParams httpParams = new HttpMethodParams();
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler(3, false));

		method.setParams(httpParams);

		StringBuffer sb = new StringBuffer();

		try
		{

			int resCode = client.executeMethod(method);

			if (resCode == HttpStatus.SC_OK)
			{
				ins = method.getResponseBodyAsStream();
				byte[] b = new byte[1024];
				int r_len = 0;
				while ((r_len = ins.read(b)) > 0)
				{
					sb.append(new String(b, 0, r_len, method
							.getResponseCharSet()));
				}
			} else
			{
				log.error("Response Code: " + resCode);
			}

		} catch (HttpException e)
		{
			log.error(e.getMessage());
		} catch (IOException e)
		{
			log.error(e.getMessage());
		} finally
		{
			method.releaseConnection();
			if (ins != null)
			{
				try
				{
					ins.close();
				} catch (IOException e)
				{
					log.error(e.getMessage());
				}
			}
		}
		log.info(sb.toString());
		return sb.toString();
	}

	/**
	 * 发送HTTP PUT请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String sendPutRequest(String url, Map<String, String> params)
	{
		URL u = null;
		HttpURLConnection con = null;

		// 构建请求参数
		StringBuffer sb = new StringBuffer();
		if (params != null)
		{
			for (Entry<String, String> e : params.entrySet())
			{
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			sb.substring(0, sb.length() - 1);
		}
		log.info("send_url:" + url);
		log.info("send_data:" + sb.toString());

		// 尝试发送请求
		try
		{
			u = new URL(url);
			con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("PUT");
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setUseCaches(false);
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");

			osw.write(sb.toString());
			osw.flush();
			osw.close();
		} catch (Exception e)
		{
			log.error("", e);
		} finally
		{
			if (con != null)
			{
				con.disconnect();
			}
		}

		// 读取返回内容
		StringBuffer buffer = new StringBuffer();
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null)
			{
				buffer.append(temp);
				buffer.append("\n");
			}
		} catch (Exception e)
		{
			log.error("", e);
		}

		return buffer.toString();
	}
	
	public static String sendGetGzRequest(String url) throws IOException {
     	log.info("send url------>" + url);
 		String resp = "";
 		//net.URLEncoder.encode();
 		GetMethod get = new GetMethod(url);
 		get.addRequestHeader("Accept","textml, application/xhtml+xml, */*");
 		get.addRequestHeader("Accept-Encoding","gzip, deflate");
 		get.addRequestHeader("Accept-Language","zh-CN");
 		get.addRequestHeader("Connection","Keep-Alive");
 		get.addRequestHeader("Activity-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)");
 		get.addRequestHeader("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
 		HttpClient httpClient = new HttpClient();
 		try {
 			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(20000);
 			httpClient.getHttpConnectionManager().getParams().setSoTimeout(30000);
 			httpClient.executeMethod(get);
 			InputStream in = get.getResponseBodyAsStream();
 			GZIPInputStream gzin = new GZIPInputStream(in);
 			InputStreamReader isr = new InputStreamReader(gzin,get.getResponseCharSet());
 			BufferedReader br = new BufferedReader(isr);
 			StringBuffer sb = new StringBuffer();
 			String tempbf;
 			while ((tempbf = br.readLine()) != null) {
 				sb.append(tempbf);
 				sb.append("\r\n");
 				}
 			isr.close();
 			gzin.close();
 			resp =  sb.toString();
 			//System.out.println("code="+code);
 			get.releaseConnection();
 		} catch (HttpException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			resp = get.getResponseBodyAsString();
 			get.releaseConnection();
 		} finally {
 			((SimpleHttpConnectionManager)httpClient.getHttpConnectionManager()).shutdown();
 		}
 		return resp;
 	}

	public static void main(String[] args)
	{

	}
}
