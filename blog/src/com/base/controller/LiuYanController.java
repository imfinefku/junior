package com.base.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.bean.LiuYan;
import com.base.service.LiuYanService;

/**
 *
 * @author xuduo
 */
@Controller
@RequestMapping("/liuYanController.do")
public class LiuYanController {

	private static Logger logger = Logger.getLogger(LiuYanController.class);
	@Autowired
	LiuYanService liuYanService;
}
