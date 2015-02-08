package org.tbe.watermark.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.tbe.watermark.service.IWatermarkService;

@Controller
public class UploadController {

	@Autowired
	private IWatermarkService service;

	@Autowired
	private ApplicationContext ctx;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "upload";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public String uploadFiles(@RequestParam("files[]") ArrayList<MultipartFile> files, HttpServletResponse response) throws IOException {
		if (files == null || files.size() == 0) {
			return "upload";
		}
		Resource template = ctx.getResource("classpath:logo.png");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(bos);
		for (MultipartFile file : files) {
			service.addWatermarkToImage(zos, file.getInputStream(), file.getName(), template.getInputStream());
		}
		zos.flush();
		zos.close();
		response.setHeader("Content-Disposition","attachment;filename=pictures.zip");
		IOUtils.copy(new ByteArrayInputStream(bos.toByteArray()), response.getOutputStream());

		response.flushBuffer();
		return null;
	}
}
