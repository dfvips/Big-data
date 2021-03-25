package com.zdata.yundong;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;


public class UrlTest {

	@Test
	public void test() throws Exception{
		String cookie = "api_uid=rBUU+l7yB8KxSnJSPt0KAg==; _nano_fp=XpdbX5UxX0gbnpT8nT_f4Q9mSqY0KUKQm~1ykXhg; csrfToken=WKACKm6z_v_MGMdlDgwtrj0J; _pati=GpAAGvnRRSq7hTtS4UlmsMLYT02Tcoc6; finger-FKGJ=d511ec4a24d044ccb8bb262a7f7c2312; finger-cookie=21757e6e9efda04fc175ba400b345892; mms_b84d1837=120,3397,3398; DDJB_PASS_ID=35b966e591a0f33f7f1f681d99c097c3; mms_b84d1838=120,3397,3398; finger-FKGJ_0.1.2=9c88ddd7a16949e6ba8f43ee4b541c96; evercookie_etag=8bb3f44e5224dd8cf1b26a1c7095932e; evercookie_cache=8bb3f44e5224dd8cf1b26a1c7095932e; finger-cookie_0.1.2=8bb3f44e5224dd8cf1b26a1c7095932e; PASS_ID=1-DsAI6dI7J/yNdDYKBwa7hxxLaok6I+yApKMtnHalWcK6VMmOn1kWS/D9LeUoQ8hQZBcvF7vB2D4H+z9MHi5nLg_622820629_18499188; JSESSIONID=331339C9120B74F74B5B7CC36CCC4BA8; x-visit-time=1599294334772";
		HashMap<String, String> cookieMap = convertCookie(cookie);
		String requestBody = "{beginDate:\"2020-09-04\",catId:18486,crawlerInfo:\"0aoAfxndgjGyg9TIAMXcSt7h_CAvsIoXtOIYKFruvozMQzCBsumeHtlfiUUMd-EqHAUljJx2Rsh4SSMpRJfzR0Cww4tAOp5ymAIxTT98TE0LvnJCt3v9TmtodiEocGI0y8g2UqcRt3iWNBZk1upcSm5F4vRtDrN2oNOmyPSymKMFFvvY-qGlcHD6zDkv2EWzUHy5e2zaVwDygxXHKT9Ob9S0fCAB70JBw3ZUOrQDfHBelbJnQTKKdNOGoqMPWdNpEuoZ91Ni4D9PyTfL0f4_gbKTUMBWg3PpoP_YPx3tkWqoPg0OgHMSJENY8900pcUR7bgZMm7qDJdCF-A_W0H_VRD7ghruSvOEKfpI9MWQmz0LaL5wuHtWHzp1s_zbo_-JineNBKyrAEgZ8GGrdcADKO5RtGS-SKDv9rY7eH3fM7oUczHtRNYoTUkbzqueHMkZJmyj_wyc3Gy3UQc7SDvmhAAw6sYz7jxaij3jBVyIRbWDkPcWRqhRLnmzbeXmBFfIhSzK15O9AJVyOROqcQJZT5BmaSnxRIyrKYZYzfquhEGmX-p6T\",dateRange:1,endDate:\"2020-09-04\",keywordRankType:1,mallId:622820629}";
		Document doc = Jsoup.connect("https://mms.pinduoduo.com/pascal/api/unit/keyword/analysis/queryKeywordRank")
				.cookies(cookieMap)
				.timeout(50000)
				.header("content-type", "application/json;charset=UTF-8")
				.ignoreContentType(true)
				.header("accept", "application/json")
				.header("accept-encoding", "gzip, deflate, br")
				.header("accept-language", "zh-CN,zh;q=0.9")
				.referrer("https://mms.pinduoduo.com/exp/tools/dataAnalysis")
				.requestBody(requestBody)
				.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.89 Safari/537.36").post();
		
		//Elements element = doc.getElementsByClass("container");
		String title = doc.title();
		System.out.println(title);
	}
	
	private HashMap<String, String> convertCookie(String cookie) {
		HashMap<String, String> cookiesMap = new HashMap<String, String>();
		String[] items = cookie.trim().split(";");
		for (String item:items) {
			cookiesMap.put(item.split("=")[0], item.split("=")[1]);
		}
		return cookiesMap;
	}
	
	
}
