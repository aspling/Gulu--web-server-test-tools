package com.taobao.gulu.http;

import org.apache.log4j.Logger;
import com.taobao.gulu.tools.ComparisonFailureHandle;

/**
 * <p>
 * Title: AssertStringBody.java
 * </p>
 * <p>
 * Description: verify response(expect body is StringText) info
 * </p>
 * 
 * @author: gongyuan.cz
 * @email: gongyuan.cz@taobao.com
 * @blog: 100continue.iteye.com
 */
public class AssertStringBody extends AssertBehavior {
	private static Logger logger = Logger.getLogger(AssertStringBody.class);

	public static void assertBody(Response actualResponse, String expectBody)
			throws Exception {
		if (actualResponse.getResponseBodyAsString().contains(expectBody)) {
			logger.info("verify response body SUCCESS");
		} else {
			logger.info("verify response body FAILED");
			throw new ComparisonFailureHandle("verify response body",
					expectBody, actualResponse.getResponseBodyAsString());
		}
	}

	public static void assertBodyEQ(Response actualResponse, String expectBody)
			throws Exception {
		if (actualResponse.getResponseBodyAsString().equals(expectBody)) {
			logger.info("verify response body SUCCESS");
		} else {
			logger.info("verify response body FAILED");
			throw new ComparisonFailureHandle("verify response body",
					expectBody, actualResponse.getResponseBodyAsString());
		}
	}

	/**
	 * 
	 * <p>Title: assertResponse</p>
	 * <p>Description: </p>
	 * @param actualResponse
	 * @param expectResponse
	 * @param assertArgs means define which part to assert, it have "StatusCode" , "StatusLine", "Headers", "Body"
	 * @throws Exception
	 */
	public static void assertResponse(Response actualResponse,
			Response expectResponse, String... assertArgs) throws Exception {
		for (int i = 0; i < assertArgs.length; i++) {
			if (assertArgs[i].equals("StatusCode")) {
				assertStatusCode(actualResponse, expectResponse.getStatusCode());
			}
			if (assertArgs[i].equals("StatusLine")) {
				assertStatusLine(actualResponse, expectResponse.getStatusLine());
			}
			if (assertArgs[i].equals("Headers")) {
				assertHeaders(actualResponse, expectResponse.getHeaders());
			}
			if (assertArgs[i].equals("Body")) {
				assertBody(actualResponse,
						expectResponse.getResponseBodyAsString());
			}
		}
	}

}
