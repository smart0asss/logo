package org.tbe.watermark.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WatermarkService implements IWatermarkService {

	private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(WatermarkService.class);

	@Override
	public void addWatermarkToImage(ZipOutputStream zip, InputStream imageFile, String name, InputStream logoFile) {
		try {
			BufferedImage image = ImageIO.read(imageFile);
			BufferedImage overlay = ImageIO.read(logoFile);

			Image scaledLogo = overlay.getScaledInstance(image.getWidth(), -1, Image.SCALE_SMOOTH);

			// create the new image, canvas size is the max. of both image sizes
			BufferedImage combined = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

			// paint both images, preserving the alpha channels
			Graphics g = combined.getGraphics();
			g.drawImage(image, 0, 0, null);
			g.drawImage(scaledLogo, 10, (int) (image.getHeight() / 2.4), null);

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(combined, "PNG", bos);
			bos.flush();
			
			ByteArrayOutputStream jpgOut = new ByteArrayOutputStream();
			ByteArrayInputStream pngIn = new ByteArrayInputStream(bos.toByteArray());
			
			convertFormat(pngIn,jpgOut,"JPEG");

			ZipEntry ze = new ZipEntry(System.currentTimeMillis() + ".jpg");
			zip.putNextEntry(ze);
			zip.write(bos.toByteArray());
			zip.closeEntry();

			jpgOut.close();
			pngIn.close();
			bos.close();
			g.dispose();
		} catch (IOException e) {
			LOGGER.error("Error in processing : ",e);
		}

	}
	
	  private void convertFormat(ByteArrayInputStream inputStream,
	            ByteArrayOutputStream outputStream, String formatName) throws IOException {
	        BufferedImage inputImage = ImageIO.read(inputStream);
	         
	        ImageIO.write(inputImage, formatName, outputStream);
	         
	    }

}
