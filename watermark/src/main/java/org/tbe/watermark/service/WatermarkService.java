package org.tbe.watermark.service;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public class WatermarkService implements IWatermarkService {

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

			ZipEntry ze = new ZipEntry(System.currentTimeMillis() + ".png");
			zip.putNextEntry(ze);
			zip.write(bos.toByteArray());
			zip.closeEntry();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String args[]) throws IOException{
		File f = new File("/Users/timo/.ssh");
		System.out.println(f.listFiles());
		zipFile(f.listFiles(), new File("output.zip"));
	}
	  public static void zipFile(final File[] files, final File targetZipFile) throws IOException {
		    try {
		      ByteArrayOutputStream   fos = new ByteArrayOutputStream();
		      ZipOutputStream zos = new ZipOutputStream(fos);
		      byte[] buffer = new byte[4096];
		      for (int i = 0; i < files.length; i++) {
		        File currentFile = files[i];
		        if (!currentFile.isDirectory()) {
		          ZipEntry entry = new ZipEntry(currentFile.getName());
		          FileInputStream fis = new FileInputStream(currentFile);
		          zos.putNextEntry(entry);
		          int read = 0;
		          while ((read = fis.read(buffer)) != -1) {
		            zos.write(buffer, 0, read);
		          }
		          zos.closeEntry();
		          fis.close();
		        }
		      }
		      zos.close();
		      FileOutputStream fw = new FileOutputStream(targetZipFile);
		      fw.write(fos.toByteArray());
		      fw.flush();
		      fw.close();
		    } catch (FileNotFoundException e) {
		      System.out.println("File not found : " + e);
		    }
		  }

}
