package com.zw.baoFu.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.security.KeyStoreException;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;

import com.zw.baoFu.http.HttpSendModel;
import com.zw.baoFu.http.SimpleHttpClient;
import com.zw.baoFu.http.SimpleHttpResponse;

/**
 * 项目名称：baofoo-fopay-sdk-java
 * 类名称：表单参数
 * 类描述：
 * 创建人：陈少杰
 * 创建时间：2014-10-22 下午2:58:22
 * 修改人：陈少杰
 * 修改时间：2014-10-22 下午2:58:22
 * @version
 */
public class HttpsUtil {

	/**
	 * @param keyStore
	 *            java密钥
	 * @param password
	 *            密钥对应密码
	 * @param trustStore
	 *            受信任的java密钥
	 * @param uri
	 *            请求uri
	 * @param sendCharSet
	 *            发送请求编码
	 * @param getCharSet
	 *            接收请求编码
	 * @param params
	 *            参数
	 * @return
	 * @throws Exception
	 */

	@SuppressWarnings("deprecation")
	public static SimpleHttpResponse doRequest(KeyStore keyStore,
			String password, KeyStore trustStore, HttpSendModel httpSendModel,
			String getCharSet) throws Exception {
		SimpleHttpClient simpleHttpclient = new SimpleHttpClient();
		HttpClient httpclient = simpleHttpclient.getHttpclient();

		SSLSocketFactory socketFactory;
		try {
			socketFactory = new SSLSocketFactory(keyStore, password, trustStore);
		} catch (Exception e) {
			throw new Exception("https请求注册密钥失败", e);
		}
		// 不校验域名
		socketFactory
				.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		int port;
		try {
			port = new URI(httpSendModel.getUrl()).getPort();
			if (port == -1) {
				port = 443;
			}
		} catch (URISyntaxException ue) {
			throw new Exception("https请求转化Uri并获取短裤失败", ue);
		}

		Scheme sch = new Scheme("https", port, socketFactory);
		httpclient.getConnectionManager().getSchemeRegistry().register(sch);

		try {
			return HttpUtil.doRequest(simpleHttpclient, httpSendModel,
					getCharSet);
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
	}

	/**
	 * 通过密钥文件路径及密码取得对应的KeyStore
	 * 
	 * @param filePath
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	public static KeyStore getKeyStore(String filePath, String password) throws Exception {
		return getKeyStore(filePath, password, KeyStore.getDefaultType());
	}

	/**
	 * 通过密钥文件路径及密码取得对应的KeyStore
	 * 
	 * @param filePath
	 * @param password
	 * @param type
	 *            密钥类型
	 * @return
	 * @throws Exception 
	 */
	public static KeyStore getKeyStore(String filePath, String password,
			String type) throws Exception {
		KeyStore keyStore;
		try {
			keyStore = KeyStore.getInstance(type);
		} catch (KeyStoreException ke) {
			throw new Exception("读取Key失败", ke);
		}

		FileInputStream instream = null;
		try {
			instream = new FileInputStream(new File(filePath));
			keyStore.load(instream, password.toCharArray());
		} catch (Exception e) {
			throw new Exception("读取Key失败", e);
		} finally {
			if (instream != null) {
				try {
					instream.close();
				} catch (Exception e) {
				}
			}
		}

		return keyStore;
	}

}
