package com.zdata.ccc;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zdata.ccc.dao.HotWordsMapper;
import com.zdata.ccc.dao.YoungWordMapper;
import com.zdata.ccc.model.HotWords;
import com.zdata.ccc.model.YoungWord;
import com.zdata.ccc.service.GroupRiseService;
import com.zdata.ccc.service.HotGoodsService;
import com.zdata.ccc.service.HotWordsService;
import com.zdata.ccc.util.DateUtil;


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
		hotGoodsService.updateRowNum("2020-10-01");
		groupRiseService.updateRise("2020-10-01");
		hotGoodsService.updateRise("2020-10-01");
		hotWordsService.updateRise("2020-10-01");
	}
	
	
}
