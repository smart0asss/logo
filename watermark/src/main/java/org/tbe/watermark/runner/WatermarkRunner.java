package org.tbe.watermark.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.tbe.watermark.config.WatermarkSettings;
import org.tbe.watermark.service.WatermarkService;

public class WatermarkRunner implements CommandLineRunner{

	@Autowired
	private WatermarkService service;
	
	@Autowired
	private WatermarkSettings settings;
	
	@Override
	public void run(String... arg0) throws Exception {
	//	File inputDir = settings.getInputDir();
	}

}
