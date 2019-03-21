package com.mycompany.controller.admin;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.entity.Blog;
import com.mycompany.lucene.BlogIndex;
import com.mycompany.service.BlogService;
import com.mycompany.util.StringUtil;

@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {

	@Resource
	BlogService blogService;

	BlogIndex blogIndex = new BlogIndex();

	/**
	 * 请求JSP：博文列表
	 * 
	 * @return
	 */
	@RequestMapping("/blogList")
	public String blogList() {
		return "admin/tags/blogList";
	}
	
	/**
	 * 请求JSP：编辑博文
	 * 
	 * @return
	 */
	@RequestMapping("/editBlog")
	public String addBlog() {
		return "admin/tags/editBlog";
	}

	/**
	 * ajax获取：博客列表分页
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam("page") Integer page,
			@RequestParam("rows") Integer rows, Blog s_blog) {
		var map = new HashMap<String, Object>();
		if (s_blog != null && StringUtil.isNotEmpty(s_blog.getTitle()))
			map.put("title", s_blog.getTitle());
		map.put("start", (page - 1) * rows);
		map.put("size", rows);
		List<Blog> blogList = blogService.getBlogList(map);
		Long total = blogService.getBlogCount(map);
		map.clear();
		map.put("rows", blogList);
		map.put("total", total);
		return map;
	}
	
	/**
	 * ajax获取博文
	 * 
	 * @return
	 */
	@RequestMapping("/info")
	@ResponseBody
	public Blog blogInfo(@RequestParam("id") Integer id) {
		Blog blog = blogService.getBlogById(id);
		return blog;
	}

	/**
	 * 执行：UEditor的文件上传
	 * 
	 * @param upfile
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Map<String, String> upload(@RequestParam("upfile") MultipartFile upfile)
			throws Exception {
		String originalFileName = upfile.getOriginalFilename();
		String newFileName = UUID.randomUUID().toString() + upfile.getOriginalFilename()
				.substring(upfile.getOriginalFilename().lastIndexOf("."));
		upfile.transferTo(new File("D:/Project-Data/blog-data/blog/" + newFileName));

		// 返回结果信息(UEditor需要)
		Map<String, String> map = new HashMap<String, String>();
		map.put("state", "SUCCESS");// 是否上传成功
		map.put("title", newFileName);// 现在文件名称
		map.put("original", originalFileName);// 文件原名称
		map.put("type", originalFileName.substring(upfile.getOriginalFilename().lastIndexOf(".")));// 文件类型:
																									// .+后缀名
		map.put("url", "/blog-data/blog/" + newFileName);// 回显访问路径
		map.put("size", upfile.getSize() + "");// 文件大小（字节数）
		return map;
	}
	
	/**
	 * 执行：保存博文
	 * 
	 * @param blog
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Boolean> save(Blog blog, Integer[] tagId) throws Exception {
		var map = new HashMap<String, Boolean>();
		if (blog != null && StringUtil.isNotEmpty(blog.getTitle()) && StringUtil.isNotEmpty(blog.getContent())
				&& StringUtil.isNotEmpty(blog.getSummary())) {
			if(blog.getId()==null) {
				blogService.add(blog);// 使用了useGeneratedKeys会自动返回插入的ID到对象
				blogIndex.addIndex(blog);
				if (tagId != null && tagId.length > 0)
					blogService.addTagForBlog(blog.getId(), Arrays.asList(tagId));
				map.put("addSuccess", true);
			}else{
				blogService.update(blog);
				blog.setReleaseDate(blogService.getBlogById(blog.getId()).getReleaseDate());//BlogIndex的更新需要原来的发布时间
				blogIndex.updateIndex(blog);
				blogService.deleteTagOfBlogByBlogId(blog.getId());
				if (tagId != null && tagId.length > 0)
					blogService.addTagForBlog(blog.getId(), Arrays.asList(tagId));
				map.put("updateSuccess", true);
			}
		} else
			map.put("success", false);
		return map;
	}
	
	/**
	 * 执行：删除博文
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestParam("strIds") String strIds) throws Exception {
		var map = new HashMap<String, Object>();
		if (strIds != null) {
			String[] blogIds = strIds.split(",");
			for(String blogId: blogIds) {
				blogService.delete(Integer.parseInt(blogId));//删除博客，博客服务层已经完成了对标签关系、评论、内部评论的删除
				blogIndex.deleteIndex(blogId);//删除博客的Lucene索引
			}
			map.put("success", true);
			return map;
		}
		return map;
	}
}
