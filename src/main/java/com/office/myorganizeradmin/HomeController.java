package com.office.myorganizeradmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
public class HomeController {

	@GetMapping({"","/"})
	public String home() {
		log.info("home()");
		
		String nextPage = "index";
		
		return nextPage;
	}
		
}
