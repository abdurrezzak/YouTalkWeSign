package com.youtalkwesign.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VideoController {

	@GetMapping("{color}/sign-videos/{word}")
	protected void getVideo(@PathVariable String color, @PathVariable String word,
			HttpServletResponse response) throws IOException {
		File file = new File("D:/" + color + "/sign-videos/" + word + ".mp4"); // TODO: D:/sign-videos/ or /home/sign-videos/
		response.setHeader("Content-Type", "video/mp4");
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
		Files.copy(file.toPath(), response.getOutputStream());
	}
}