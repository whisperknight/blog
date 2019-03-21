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

import com.mycompany.entity.Tag;
import com.mycompany.service.TagService;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/admin/tag")
public class TagAdminController {

	@Resource
	TagService tagService;

	/**
	 * 请求JSP：标签列表
	 * 
	 * @return
	 */
	@RequestMapping("/tagList")
	public String tagList() {
		return "admin/tags/tagList";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, Tag s_tag) {
		var map = new HashMap<String, Object>();
		if (s_tag != null && StringUtil.isNotEmpty(s_tag.getName()))
			map.put("name", s_tag.getName());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);
		List<Tag> tagList = tagService.getTagList(map);
		Long total = tagService.getTagCount(map);
		map.clear();
		map.put("rows", tagList);
		map.put("total", total);
		return map;
	}

	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(Tag tag, HttpSession session) {
		var map = new HashMap<String, Object>();
		if (tag != null && StringUtil.isNotEmpty(tag.getName())) {

			if (tag.getId() == null) {
				tagService.add(tag);// 使用了useGeneratedKeys会自动返回插入的ID到对象
				map.put("tagId", tag.getId());// 将刚插入的标签ID返回给页面
			} else
				tagService.update(tag);

			flushTagListInApplication(session);
			map.put("success", true);
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
				tagService.delete(Integer.parseInt(tagId));
			}
			flushTagListInApplication(session);
			map.put("success", true);
		} else
			map.put("success", false);
		return map;
	}

	/**
	 * 刷新application里的tagList
	 * 
	 * @param request
	 */
	private void flushTagListInApplication(HttpSession session) {
		List<Tag> tagList = tagService.getAllTags();
		session.getServletContext().setAttribute("tagList", tagList);
	}
}
