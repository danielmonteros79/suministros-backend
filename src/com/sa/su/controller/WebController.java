package com.sa.su.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = { "/" })
public class WebController {
	Logger log = Logger.getLogger(WebController.class);

	@RequestMapping
	public String index() {
		return "index.html";
	}

}