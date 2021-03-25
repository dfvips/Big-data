package com.zdata.ccc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource(locations={"classpath:dubbo/dubbo-config.xml"})
public class DubboConfig {
	
}
