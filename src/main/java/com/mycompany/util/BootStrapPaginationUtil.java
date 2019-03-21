package com.mycompany.util;

import java.util.ArrayList;
import java.util.List;

public class BootStrapPaginationUtil {

	private static BootStrapPaginationUtil bootStrapPaginationUtil = new BootStrapPaginationUtil();

	/**
	 * 已封装：直接获取bootstrap的分页组件
	 * 
	 * @param totalRecord 总记录
	 * @param pageSize    一页记录数
	 * @param currentPage 当前页
	 * @param preUrl      没有加分页参数的url，不要以?或&结尾，末尾会自动添加
	 * @return
	 */
	public static Pagination getPagination(Long totalRecord, Integer pageSize, Integer currentPage,
			String preUrl) {
		int totalPage = (int) (totalRecord % pageSize == 0 ? totalRecord / pageSize
				: (totalRecord / pageSize + 1));
		if (totalPage == 0)
			totalPage = 1;//保证至少有一页
		if (preUrl.charAt(preUrl.length() - 1) == '?' || preUrl.charAt(preUrl.length() - 1) == '&')
			preUrl += "page=";
		else
			preUrl += preUrl.lastIndexOf("?") >= 0 ? "&page=" : "?page=";
		return bootStrapPaginationUtil.new Pagination(totalPage, currentPage, preUrl);
	}

	/**
	 * bootstrap的内部分页样式，用item里的url表示page页数
	 * 
	 * @param totalRecord 总记录
	 * @param pageSize    一页记录数
	 * @param currentPage 当前页
	 * @return
	 */
	public static Pagination getPaginationWithSimpleStyle(Long totalRecord, Integer pageSize, Integer currentPage) {
		int totalPage = (int) (totalRecord % pageSize == 0 ? totalRecord / pageSize
				: (totalRecord / pageSize + 1));
		if (totalPage == 0)
			totalPage = 1;//保证至少有一页
			return bootStrapPaginationUtil.new Pagination(totalPage, currentPage);
	}

	public class Pagination {
		private List<Item> items = new ArrayList<>();

		private int maxPageView = 5;// 分页组件中最大显示总页数，改动此数值需要联合改动分页算法

		/**
		 * 获取bootstrap的分页，每一个item对应一个分页上的按钮
		 * 
		 * @param totalPage   总页数
		 * @param currentPage 当前页数
		 * @param preUrl      预置的url，例如 "product_list.action?typeId=3&pageBean.page="
		 */
		public Pagination(Integer totalPage, Integer currentPage, String preUrl) {
			items.add(new Item("首页", preUrl + 1, currentPage == 1));
			items.add(new Item("上一页", preUrl + (currentPage - 1), currentPage == 1));
			if (totalPage < maxPageView)
				for (int i = 1; i <= totalPage; i++)
					items.add(new Item(i + "", preUrl + i, false, currentPage == i));
			else if (currentPage <= 2)
				for (int i = 1; i <= maxPageView; i++)
					items.add(new Item(i + "", preUrl + i, false, currentPage == i));
			else if (totalPage - 1 <= currentPage && currentPage <= totalPage)
				for (int i = totalPage - maxPageView + 1; i <= totalPage; i++)
					items.add(new Item(i + "", preUrl + i, false, currentPage == i));
			else
				for (int i = currentPage - 2; i <= currentPage + 2; i++)
					items.add(new Item(i + "", preUrl + i, false, currentPage == i));
			items.add(new Item("下一页", preUrl + (currentPage + 1), currentPage == totalPage));
			items.add(new Item("尾页", preUrl + totalPage, currentPage == totalPage));
		}
		
		/**
		 * bootstrap的ajax分页，用item里的url表示page页数
		 * 
		 * @param totalPage   总页数
		 * @param currentPage 当前页数
		 */
		public Pagination(Integer totalPage, Integer currentPage) {
			items.add(new Item("共"+totalPage+"页"));
			items.add(new Item("上一页", "" + (currentPage - 1), currentPage == 1));
			if (totalPage < maxPageView)
				for (int i = 1; i <= totalPage; i++)
					items.add(new Item(i + "", "" + i, false, currentPage == i));
			else if (currentPage <= 2)
				for (int i = 1; i <= maxPageView; i++)
					items.add(new Item(i + "", "" + i, false, currentPage == i));
			else if (totalPage - 1 <= currentPage && currentPage <= totalPage)
				for (int i = totalPage - maxPageView + 1; i <= totalPage; i++)
					items.add(new Item(i + "", "" + i, false, currentPage == i));
			else
				for (int i = currentPage - 2; i <= currentPage + 2; i++)
					items.add(new Item(i + "", "" + i, false, currentPage == i));
			items.add(new Item("下一页", "" + (currentPage + 1), currentPage == totalPage));
		}

		public List<Item> getItems() {
			return items;
		}

		public void setItems(List<Item> items) {
			this.items = items;
		}

		public int getMaxPageView() {
			return maxPageView;
		}

		public void setMaxPageView(int maxPageView) {
			this.maxPageView = maxPageView;
		}

	}

	public class Item {
		private String number;// 页码
		private String url;// 链接
		private boolean disabled;
		private boolean active;

		public Item() {
			super();
		}
		
		public Item(String number) {
			super();
			this.number = number;
		}

		public Item(String number, String url) {
			super();
			this.number = number;
			this.url = url;
		}

		public Item(String number, String url, boolean disabled) {
			super();
			this.number = number;
			this.url = url;
			this.disabled = disabled;
		}

		public Item(String number, String url, boolean disabled, boolean active) {
			super();
			this.number = number;
			this.url = url;
			this.disabled = disabled;
			this.active = active;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public boolean isDisabled() {
			return disabled;
		}

		public void setDisabled(boolean disabled) {
			this.disabled = disabled;
		}

		public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}
	}
}
