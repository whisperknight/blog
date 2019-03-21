package com.mycompany.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.controller.InitController;

@Controller
@RequestMapping("/admin")
public class MainAdminController {

	@Resource
	InitController initController;

	/**
	 * 刷新application缓存
	 * 
	 * @throws Exception
	 */
	@RequestMapping("refresh")
	@ResponseBody
	public Map<String, Object> refreshApplication() throws Exception {
		initController.init();
		var map = new HashMap<String, Object>();
		map.put("success", true);
		return map;
	}

	/**
	 * 请求后台主页
	 * @return
	 */
	@RequestMapping("/main")
	public ModelAndView main() {
		var mav = new ModelAndView("admin/main");
		mav.addObject("mainPage", "/admin/home.jsp");
		return mav;
	}
}
