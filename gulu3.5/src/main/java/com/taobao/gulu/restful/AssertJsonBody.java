package com.taobao.gulu.restful;

import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.taobao.gulu.tools.ComparisonFailureHandle;
import com.taobao.gulu.tools.FailedHandle;

/**
 * <p>Title: AssertJsonBody.java</p>
 * <p>Description: verify response(expect body is JsonObject) info</p>
 * @author: gongyuan.cz
 * @email:  gongyuan.cz@taobao.com
 * @blog:   100continue.iteye.com
 */
public class AssertJsonBody extends AssertBehavior {

	private static Logger logger = Logger.getLogger(AssertJsonBody.class);
			
	public static void assertBody(Response actualResponse, String expectJsonStr) throws Exception {
		JSONObject expectJson = new JSONObject(expectJsonStr); 
		assertBody(actualResponse, expectJson);
	}

	public static void assertBody(Response actualResponse, JSONObject expectJson) throws Exception {
		try {
			for (Iterator<?> iter = expectJson.keys(); iter.hasNext();) {
				String key = (String) iter.next();

				if (!(expectJson.get(key).toString().equals(new JSONObject(
						actualResponse.getResponseBodyAsString()).get(key).toString()))){
					logger.info("verify response body FAILED");
					throw new ComparisonFailureHandle("verify response body", expectJson.toString(), actualResponse.getResponseBodyAsString());
				}
			}
		} catch (JSONException e) {
			logger.info(e);
			throw new FailedHandle(e.getMessage());
		}
		logger.info("verify response body SUCCESS");
	}
	
	public static void assertResponse(Response actualResponse, Response expectResponse, String...assertArgs ) throws Exception{
		for(int i = 0; i < assertArgs.length; i++){
			if(assertArgs[i].equals("StatusCode")){
				assertStatusCode(actualResponse, expectResponse.getStatusCode());
			}
			if(assertArgs[i].equals("StatusLine")){
				assertStatusLine(actualResponse, expectResponse.getStatusLine());
			}
			if(assertArgs[i].equals("Headers")){
				assertHeaders(actualResponse, expectResponse.getHeaders());
			}
			if(assertArgs[i].equals("Body")){
				assertBody(actualResponse, expectResponse.getResponseBodyAsString());
			}
		}
	}


	public static String getJSONValue(JSONObject expectJson, String key) throws Exception {
		return expectJson.getString(key);
	}
	

	public static String getJSONValue(String expectJsonStr, String key) throws Exception {
		JSONObject expectJson = new JSONObject(expectJsonStr); 
		return expectJson.getString(key);
	}

	
}
