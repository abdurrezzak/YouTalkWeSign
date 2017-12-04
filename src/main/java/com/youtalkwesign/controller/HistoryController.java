package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;

@Controller
public class HistoryController {

	@RequestMapping(value = "/history", method = RequestMethod.GET)
	public String getHistoryVideos(ModelMap model) {
		List<Video> historyVideos = new ArrayList<Video>();

		Video videoOne = new Video();
		videoOne.setId("GCdwKhTtNNw");
		videoOne.setTitle("The Neighbourhood - Sweather Weather");
		videoOne.setThumbnailImageUrl("https://i.ytimg.com/vi/GCdwKhTtNNw/maxresdefault.jpg");	
		
		historyVideos.add(videoOne);
		
		model.addAttribute("historyVideos", historyVideos);
		
		return "history-fragment :: result";		
	}
}
