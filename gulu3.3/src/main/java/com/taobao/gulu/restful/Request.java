package com.taobao.gulu.restful;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Request.java
 * </p>
 * <p>
 * Description: contain request info
 * </p>
 * 
 * @author: gongyuan.cz
 * @email: gongyuan.cz@taobao.com
 * @blog: 100continue.iteye.com
 */
public class Request {
	private Logger logger = Logger.getLogger(Request.class);

	private String url = "";
	private Header[] headers = null;
	private MultipartBodyEntity multipartBody = null;
	private BodyEntity body = null;
	private NameValuePair[] pairsBody = null;
	private int connectTimeOut = 30000;
	private int soTimeOut = 120000;
	public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
	public static final String DEFAULT_CHARSET = "ISO-8859-1";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Header[] getHeaders() {
		return headers;
	}

	public void setHeaders(Header[] headers) {
		this.headers = headers;
	}

	/**
	 * set request headers
	 * 
	 * @param headersStr
	 *            pattern: "a:b$c:d$cookie:tt=xx;cc=pp" means headers: a:b c:d
	 *            cookie:tt=xx;cc=pp
	 * 
	 */
	public void setHeaders(String headersStr) {

		String keyValuePairArray[] = headersStr.split("\\$");
		Header[] headers = new Header[keyValuePairArray.length];
		for (int i = 0; i < keyValuePairArray.length; i++) {
			String keyValuePair[] = keyValuePairArray[i].split(":");
			headers[i] = new Header(keyValuePair[0], keyValuePair[1].trim());
		}
		this.headers = headers;
	}

	public BodyEntity getBody() {
		return body;
	}

	public void setBody(BodyEntity body) {
		this.body = body;
	}

	public MultipartBodyEntity getMultipartBody() {
		return multipartBody;
	}

	public void setMultipartBody(MultipartBodyEntity multipartBody) {
		this.multipartBody = multipartBody;
	}

	public NameValuePair[] getPairsBody() {
		return pairsBody;
	}

	public void setPairsBody(NameValuePair... pairs) {
		this.pairsBody = pairs;
	}

	/**
	 * set pairs
	 * 
	 * @param pairsStr
	 *            pattern: "a=b$c=d$e=f" means headers: a:b c:d
	 *            cookie:tt=xx;cc=pp
	 * 
	 */
	public void setPairsBody(String pairsStr) {
		String nameValuePairArray[] = pairsStr.split("\\$");
		NameValuePair[] pairs = new NameValuePair[nameValuePairArray.length];
		for (int i = 0; i < nameValuePairArray.length; i++) {
			String keyValuePair[] = nameValuePairArray[i].split("=");
			pairs[i] = new NameValuePair(keyValuePair[0], keyValuePair[1]);
		}
		this.pairsBody = pairs;
	}

	public int getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	/**
	 * <p>
	 * Title: showRequest
	 * </p>
	 * <p>
	 * Description: show request info
	 * </p>
	 * 
	 * @throws Exception
	 */
	public void showRequest() throws Exception {
		// String headerName;
		logger.info("Request Info: ---------------> ");
		logger.info("Request URL: " + getUrl());
		// Set<String> keySet = getHeaders().keySet();
		// Iterator<String> it = keySet.iterator();
		// while (it.hasNext()) {
		// headerName = (String) it.next();
		// logger.info(headerName + ":"
		// + (String) getHeaders().get(headerName));
		// }
		if (this.headers != null)
			for (Header header : this.headers) {
				logger.info(header.getName() + ":" + header.getValue());
			}
		
		if (multipartBody != null) {
			BodyEntity[] multipartbodyEntity = multipartBody.getBodyEntity();
			for (int i = 0; i < multipartbodyEntity.length; i++) {
				BodyEntity requestEntityBody = multipartbodyEntity[i];
				logger.info("Content-Type:"
						+ (requestEntityBody.getContentType() == null ? DEFAULT_CONTENT_TYPE
								: requestEntityBody.getContentType())
						+ "; charset="
						+ (requestEntityBody.getCharset() == null ? DEFAULT_CHARSET
								: requestEntityBody.getCharset()));

				logger.info("Request Body:"
						+ new String(requestEntityBody.getContent()));
			}
			logger.info("End of Request Info ------------->");
		} else if (body != null) {
			logger.info("Content-Type:"
					+ (body.getContentType() == null ? DEFAULT_CONTENT_TYPE
							: body.getContentType())
					+ "; charset="
					+ (body.getCharset() == null ? DEFAULT_CHARSET : body
							.getCharset()));

			logger.info("Request Body:" + new String(body.getContent()));
			logger.info("End of Request Info ------------->");
		} else if (pairsBody != null) {
			logger.info("Content-Type:" + DEFAULT_CONTENT_TYPE + "; charset="
					+ DEFAULT_CHARSET);

			for (int i = 0; i < pairsBody.length; i++) {
				logger.info("Request Body Name Value Pair:"
						+ pairsBody[i].getName() + " = "
						+ pairsBody[i].getValue());
			}
		} else {
			logger.info("do not content any body! ");
		}
	}
}
