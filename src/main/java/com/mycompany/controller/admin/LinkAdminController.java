package com.mycompany.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.entity.Link;
import com.mycompany.service.LinkService;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/admin/link")
public class LinkAdminController {

	@Resource
	LinkService linkService;

	/**
	 * 请求JSP：标签列表
	 * 
	 * @return
	 */
	@RequestMapping("/linkList")
	public String linkList() {
		return "admin/tags/linkList";
	}

	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, Link s_link) {
		var map = new HashMap<String, Object>();
		if (s_link != null && StringUtil.isNotEmpty(s_link.getName()))
			map.put("name", s_link.getName());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);
		List<Link> linkList = linkService.getLinkList(map);
		Long total = linkService.getLinkCount(map);
		map.clear();
		map.put("rows", linkList);
		map.put("total", total);
		return map;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(Link link, HttpSession session) {
		var map = new HashMap<String, Object>();
		if (link != null) {

			if (link.getId() == null && StringUtil.isNotEmpty(link.getName())
					&& StringUtil.isNotEmpty(link.getUrl())) {
				linkService.add(link);
				flushLinkListInApplication(session);
				map.put("success", true);
			} else if (link.getId() != null) {
				linkService.update(link);
				flushLinkListInApplication(session);
				map.put("success", true);
			} else
				map.put("success", false);

		} else
			map.put("success", false);
		return map;
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("strIds") String strIds, HttpSession session) {
		var map = new HashMap<String, Object>();
		if (strIds != null) {
			String[] tagIds = strIds.split(",");
			for (String tagId : tagIds) {
				linkService.delete(Integer.parseInt(tagId));
			}
			flushLinkListInApplication(session);
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}

	/**
	 * 刷新application里的linkList
	 * 
	 * @param request
	 */
	private void flushLinkListInApplication(HttpSession session) {
		List<Link> linkList = linkService.getAllLinks();
		session.getServletContext().setAttribute("linkList", linkList);
	}
}
