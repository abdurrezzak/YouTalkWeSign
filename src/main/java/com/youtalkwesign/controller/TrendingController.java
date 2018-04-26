package com.youtalkwesign.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.youtalkwesign.model.Video;

@Controller
public class TrendingController {
	
	@RequestMapping(value = "/trending", method = RequestMethod.GET)
	public String getHeartedVideos(ModelMap model) {
		List<Video> trendingVideos = new ArrayList<Video>();

		Video videoOne = new Video();
		videoOne.setId("jyAXQAJXzxs");
		videoOne.setTitle("Game of Thrones: Cast Commentary on Jon, Daenerys, and Jorah Meeting (HBO)");
		videoOne.setThumbnailImageUrl("https://i.ytimg.com/vi/jyAXQAJXzxs/maxresdefault.jpg");
		
		Video videoTwo = new Video();
		videoTwo.setId("KM4Xe6Dlp0Y");
		videoTwo.setTitle("Looks aren't everything. Believe me, I'm a model. | Cameron Russell");
		videoTwo.setThumbnailImageUrl("https://i.hizliresim.com/oOJGvb.jpg");	
		
		Video videoThree = new Video();
		videoThree.setId("Sv5QitqbxJw");
		videoThree.setTitle("The biggest risks facing cities -- and some solutions | Robert Muggah");
		videoThree.setThumbnailImageUrl("https://i.ytimg.com/vi/Sv5QitqbxJw/maxresdefault.jpg");
		
		Video videoFour = new Video();
		videoFour.setId("yx9dRL1BCCQ");
		videoFour.setTitle("Game of Thrones: The Frozen Lake (HBO)");
		videoFour.setThumbnailImageUrl("https://i.ytimg.com/vi/yx9dRL1BCCQ/maxresdefault.jpg");
	
		trendingVideos.add(videoOne);
		trendingVideos.add(videoTwo);
		trendingVideos.add(videoThree);
		trendingVideos.add(videoFour);
		model.addAttribute("trendingVideos", trendingVideos);
		
		return "trending-fragment :: result";
	}
}
