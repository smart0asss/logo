package org.tbe.watermark.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="watermark")
public class WatermarkSettings {

	private String logoFile;
	private String inputDir;
	public String getLogoFile() {
		return logoFile;
	}
	public void setLogoFile(String logoFile) {
		this.logoFile = logoFile;
	}
	public String getInputDir() {
		return inputDir;
	}
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}
	@Override
	public String toString() {
		return "WatermarkSettings [logoFile=" + logoFile + ", inputDir=" + inputDir + "]";
	}

	
}
