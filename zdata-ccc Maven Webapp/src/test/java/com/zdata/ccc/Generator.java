package com.zdata.ccc;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;


public class Generator {

	@Test
	public void test() throws Exception {
		//执行过程中的警告信息
		List<String> warnings = new ArrayList<String>();
		//生成的代码重复时，覆盖原代码
		boolean overwrite = false;
		//读取配置文件
		InputStream is = Generator.class.getResourceAsStream("/generator/generatorConfig.xml");
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(is);
		is.close();
		
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		//创建代码生成器
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback,warnings);
		//执行生成代码
		myBatisGenerator.generate(null);
		//输出警告信息
		for(String warning : warnings) {
			System.out.println(warning);
		}
	}

}
