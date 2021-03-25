package com.zdata.yundong;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdata.yundong.dao.HotWordsMapper;
import com.zdata.yundong.dao.YoungWordMapper;
import com.zdata.yundong.service.GroupRiseService;
import com.zdata.yundong.service.HotGoodsService;
import com.zdata.yundong.service.HotWordsService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
	
	@Autowired
	private HotGoodsService hotGoodsService;
	
	@Autowired
	private HotWordsMapper hotWordsMapper;
	
	@Autowired
	private YoungWordMapper youngWordMapper;
	
	@Autowired
	private GroupRiseService groupRiseService;
	
	@Autowired
	private HotWordsService hotWordsService;
	
	@Test
	public void test() throws Exception{
		hotGoodsService.updateRowNum("2020-10-06");
		groupRiseService.updateRise("2020-10-06");
		hotGoodsService.updateRise("2020-10-06");
		hotWordsService.updateRise("2020-10-06");
/*		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		hotGoodsService.updateRowNum(DateUtil.formatDate(calendar.getTime(), "yyyy-MM-dd"));
*/		
		
		//hotGoodsService.updateRowNum("2020-09-23");
		/*groupRiseService.updateRise("2020-09-23");
		hotGoodsService.updateRise("2020-09-23");
		hotWordsService.updateRise("2020-09-23");*/
	/*	String api="http://api.dfvips.com/simnet?type=text&text=懒人眼影&compare=懒人眼影盘";
		  Document doc=null;
		  Connection connection=Jsoup.connect(api);
		  try {
		   doc = connection.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:74.0) Gecko/20100101 Firefox/74.0").timeout(50000).ignoreContentType(true).timeout(50000).ignoreContentType(true).get();
		  } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
		  }
		  String json=doc.body().text();
		  System.out.println(json);*/
		
		//List<HotWords> list = hotWordsMapper.findYoungByCatId(18715);
		/*for (HotWords hotWords : list) {
			//获取关键词的增长数据
			YoungWord record = new YoungWord();
			record.setState(0);
			record.setCatId(18715);
			record.setWord(hotWords.getWord());
			youngWordMapper.insert(record);
		}*/
		//hotWordsService.findYoung(18671);
		//hotWordsService.findYoung(18715);
		//hotWordsService.findYoung(18720);
	}
	
	
}
