package org.tbe.watermark.service;

import java.io.InputStream;
import java.util.zip.ZipOutputStream;

public interface IWatermarkService {

	void addWatermarkToImage(ZipOutputStream zip, InputStream imageFile, String name, InputStream logoFile);
}
