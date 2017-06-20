package com.inspur.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * NetTool:封装一个类搞定90%安卓客户端与服务器端交互
 *
 */
public class NetTool {
	private static final int TIMEOUT = 10000;// 10秒

	/**
	 * 传送文本,例如Json,xml等
	 */
	public static String sendTxt(String urlPath, String txt, String encoding)
			throws Exception {
		byte[] sendData = txt.getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(TIMEOUT);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", encoding);
		conn.setRequestProperty("Content-Length",
				String.valueOf(sendData.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(sendData);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encoding));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			return responseData;
		}
		return "sendText error!";
	}

	/**
	 * 上传文件
	 */
	public static String sendFile(String urlPath, String filePath,
								  String newName) throws Exception {

		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";

		URL url = new URL(urlPath);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		/* 允许Input、Output，不使用Cache */
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		/* 设置传送的method=POST */
		con.setRequestMethod("POST");
		/* setRequestProperty */

		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		con.setRequestProperty("Content-Type", "multipart/form-data;boundary="
				+ boundary);
		/* 设置DataOutputStream */
		DataOutputStream ds = new DataOutputStream(con.getOutputStream());
		ds.writeBytes(twoHyphens + boundary + end);
		ds.writeBytes("Content-Disposition: form-data; "
				+ "name=\"file1\";filename=\"" + newName + "\"" + end);
		ds.writeBytes(end);

		/* 取得文件的FileInputStream */
		FileInputStream fStream = new FileInputStream(filePath);
		/* 设置每次写入1024bytes */
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];

		int length = -1;
		/* 从文件读取数据至缓冲区 */
		while ((length = fStream.read(buffer)) != -1) {
			/* 将资料写入DataOutputStream中 */
			// System.out.println("NetTool----while---");
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(end);
		ds.writeBytes(twoHyphens + boundary + twoHyphens + end);

		/* close streams */
		fStream.close();
		ds.flush();

		/* 取得Response内容 */
		InputStream is = con.getInputStream();
		int ch;
		StringBuffer b = new StringBuffer();
		while ((ch = is.read()) != -1) {
			b.append((char) ch);
		}
		/* 关闭DataOutputStream */
		ds.close();
		return b.toString();
	}

	/**
	 * 上传文件
	 *
	 * @param url
	 * @param filesMap
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String uploadFile(String url, Map<String, File> filesMap)
			throws ClientProtocolException, IOException {
		String result = null;
		HttpClient httpClient = new DefaultHttpClient();
		// 设置通信协议版本
		httpClient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		HttpPost httpPost = new HttpPost(url);
		// 文件传输
		MultipartEntity mEntity = new MultipartEntity();
		Iterator<String> iterator = filesMap.keySet().iterator();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			ContentBody cBody = new FileBody(filesMap.get(fileName));
			mEntity.addPart(fileName, cBody);
			System.out.println("while---uf--iterator fileName:" + fileName
					+ "  file:" + filesMap.get(fileName));
		}
		System.out.println("file length----->" + mEntity.getContentLength());
		httpPost.setEntity(mEntity);
		HttpResponse httpRes = httpClient.execute(httpPost);
		HttpEntity httpEntity = httpRes.getEntity();
		System.out.println("status line " + httpRes.getStatusLine());
		if (httpEntity != null) {
			result = EntityUtils.toString(httpEntity, "utf-8");
			httpEntity.consumeContent();
		}
		// 关闭连接
		httpClient.getConnectionManager().shutdown();
		return result;
	}

	/**
	 * 上传参数和文件
	 *
	 * @param url
	 * @param paramters
	 * @param filesMap
	 * @return
	 * @throws Exception
	 */
	public static String uploadFile2(String url, Map<String, String> paramters,
									 Map<String, File> filesMap) throws Exception {
		System.out.println("uploadFile2---------------");
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		/* 建立HTTPPost对象 */
		HttpPost httpRequest = new HttpPost(url);
		String strResult = "doPostError";
		// 文件传输
		MultipartEntity mEntity = new MultipartEntity();
		// 添加上传参数
		for (Map.Entry<String, String> entry : paramters.entrySet()) {
			ContentBody strBody = new StringBody(entry.getValue(),
					Charset.forName("UTF-8"));
			mEntity.addPart(entry.getKey(), strBody);
		}

		Iterator<String> iterator = filesMap.keySet().iterator();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			ContentBody cBody = new FileBody(filesMap.get(fileName));
			mEntity.addPart(fileName, cBody);
			System.out.println("while-----iterator fileName:" + fileName
					+ "  file:" + filesMap.get(fileName));
		}
		System.out.println("file length----->" + mEntity.getContentLength());
		httpRequest.setEntity(mEntity);
		/* 发送请求并等待响应 */
		HttpResponse httpResponse = httpClient.execute(httpRequest);
		/* 若状态码为200 ok */
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			/* 读返回数据 */
			strResult = EntityUtils.toString(httpResponse.getEntity());
		} else {
			strResult = "Error Response: "
					+ httpResponse.getStatusLine().toString();
		}
		//Log.d("NetTool------>uploadFile2", strResult);
		// 关闭连接
		httpClient.getConnectionManager().shutdown();
		return strResult;

	}

	/**
	 * 通过get方式提交参数给服务器
	 */
	public static String sendGetRequest(String urlPath,
										Map<String, String> params, String encoding) throws Exception {

		// 使用StringBuilder对象
		StringBuilder sb = new StringBuilder(urlPath);
		sb.append('?');

		// 迭代Map
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), encoding))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		// 打开链接
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", encoding);
		conn.setConnectTimeout(TIMEOUT);
		// 如果请求响应码是200，则表示成功
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encoding));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			return responseData;
		}
		return "sendGetRequest error!";

	}

	/**
	 * 通过Post方式提交参数给服务器,也可以用来传送json或xml文件
	 */
	public static String sendPostRequest(String urlPath,
										 Map<String, String> params, String encoding) throws Exception {
		StringBuilder sb = new StringBuilder();
		// 如果参数不为空
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				// Post方式提交参数的话，不能省略内容类型与长度
				sb.append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue(), encoding))
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		// 得到实体的二进制数据
		byte[] entitydata = sb.toString().getBytes();
		URL url = new URL(urlPath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(TIMEOUT);
		// 如果通过post提交数据，必须设置允许对外输出数据
		conn.setDoOutput(true);
		// 这里只设置内容类型与内容长度的头字段
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		// conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", encoding);
		conn.setRequestProperty("Content-Length",
				String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		// 把实体数据写入是输出流
		outStream.write(entitydata);
		// 内存中的数据刷入
		outStream.flush();
		outStream.close();
		// 如果请求响应码是200，则表示成功
		if (conn.getResponseCode() == 200) {
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), encoding));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			return responseData;
		}
		return "sendText error!";
	}

	/**
	 * 在遇上HTTPS安全模式或者操作cookie的时候使用HTTPclient会方便很多 使用HTTPClient（开源项目）向服务器提交参数
	 */
	public static String sendHttpClientPost(String urlPath,
											Map<String, String> params, String encoding) throws Exception {
		// 需要把参数放到NameValuePair
		List<NameValuePair> paramPairs = new ArrayList<NameValuePair>();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				paramPairs.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
		}
		// 对请求参数进行编码，得到实体数据
		UrlEncodedFormEntity entitydata = new UrlEncodedFormEntity(paramPairs,
				encoding);
		// 构造一个请求路径
		HttpPost post = new HttpPost(urlPath);
		// 设置请求实体
		post.setEntity(entitydata);
		// 浏览器对象
		DefaultHttpClient client = new DefaultHttpClient();
		// 执行post请求
		HttpResponse response = client.execute(post);
		// 从状态行中获取状态码，判断响应码是否符合要求
		if (response.getStatusLine().getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();
			InputStreamReader inputStreamReader = new InputStreamReader(
					inputStream, encoding);
			BufferedReader reader = new BufferedReader(inputStreamReader);// 读字符串用的。
			String s;
			String responseData = "";
			while (((s = reader.readLine()) != null)) {
				responseData += s;
			}
			reader.close();// 关闭输入流
			return responseData;
		}
		return "sendHttpClientPost error!";
	}

	/**
	 * 根据URL直接读文件内容，前提是这个文件当中的内容是文本，函数的返回值就是文件当中的内容
	 */
	public static String readTxtFile(String urlStr, String encoding)
			throws Exception {
		// System.out.println("readTextFile---->urlStr:"+urlStr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			// 创建一个URL对象
			URL url = new URL(urlStr);
			// 创建一个Http连接
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			// 使用IO流读取数据
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), encoding));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				buffer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 该函数返回整形 -1：代表下载文件出错 0：代表下载文件成功 1：代表文件已经存在
	 */
	public static int downloadFile(String urlStr, String path, String fileName)
			throws Exception {
		InputStream inputStream = null;
		try {
			inputStream = getInputStreamFromUrl(urlStr);
			File resultFile = write2SDFromInput(path, fileName, inputStream);
			if (resultFile == null) {
				return -1;
			}

		} catch (Exception e) {
			return -1;
		} finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				throw e;
			}
		}
		return 0;
	}

	/**
	 * 根据URL得到输入流
	 *
	 * @param urlStr
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static InputStream getInputStreamFromUrl(String urlStr)
			throws MalformedURLException, IOException {
		URL url = new URL(urlStr);
		HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
		InputStream inputStream = urlConn.getInputStream();
		return inputStream;
	}

	/**
	 * 将一个InputStream里面的数据写入到SD卡中
	 */
	private static File write2SDFromInput(String directory, String fileName,
										  InputStream input) {
		File file = null;
		String SDPATH = Environment.getExternalStorageDirectory().toString();
		FileOutputStream output = null;
		File dir = new File(SDPATH + directory);
		if (!dir.exists()) {
			dir.mkdir();
		}
		try {
			file = new File(dir + File.separator + fileName);
			file.createNewFile();
			output = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			while ((input.read(buffer)) != -1) {
				output.write(buffer);
			}
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}

	/**
	 * 根url据获得图片的byte[];
	 *
	 * @param urlStr
	 * @return
	 */
	public static byte[] getImageByteArray(String urlStr) {
		byte[] data = null;
		try {
			// 特定网址构建一个url
			URL url = new URL(urlStr);
			// 打开url连接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");// 提交方式
			conn.setConnectTimeout(6000);// 连接超时时间
			InputStream inputstream = conn.getInputStream();// 获得输入流
			data = readInputStream(inputstream);// 通过输入流获得图片二进制数据
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * 根据输入流获得byte
	 *
	 * @param input
	 * @return
	 */
	public static byte[] readInputStream(InputStream input) {
		ByteArrayOutputStream byteArrOut = new ByteArrayOutputStream();
		// 定义缓冲区
		byte[] buffer = new byte[1024];
		int read = 0;
		try {
			while ((read = input.read(buffer)) != -1)
				byteArrOut.write(buffer, 0, read);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return byteArrOut.toByteArray();
	}

	/**
	 *
	 * @param src
	 *            请求URL
	 * @param paramters
	 *            请求参数
	 * @return POST请求结果
	 * @throws IOException
	 */
	public static String post(String src, Map<String, String> paramters)
			throws IOException {
		URL url = new URL(src);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");

		if (paramters != null && paramters.size() > 0) {
			StringBuffer param = new StringBuffer();
			Set<Entry<String, String>> entries = paramters.entrySet();
			for (Iterator<Entry<String, String>> iterator = entries.iterator(); iterator
					.hasNext();) {
				Entry<String, String> entry = iterator.next();
				param.append(entry.getKey().trim()).append("=")
						.append(entry.getValue().trim()).append("&");
			}
			String content = param.substring(0, param.length() - 1);
			System.out.println("content --> " + content);
			connection.getOutputStream().write(param.toString().getBytes());
		}

		connection.getOutputStream().flush();
		connection.getOutputStream().close();
		// 返回请求码　200为请求成功
		int code = connection.getResponseCode();
		System.out.println("code   " + code);
		InputStream is = connection.getInputStream();
		int ch = -1;
		StringBuffer result = new StringBuffer();
		while ((ch = is.read()) != -1) {
			result.append((char) ch);
		}
		return result.toString();
	}


	private static HttpClient customerHttpClient;




}