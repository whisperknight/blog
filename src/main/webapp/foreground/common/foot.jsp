<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="b-foot">
	<div class="b-foot-link">
		<div class="row text-center">
			<c:forEach items="${linkList }" var="link">
				<div class="col-md-3">
					<p style="margin: 10px auto;">
						<a href="${link.url }" target="_blank">${link.name }</a>
					</p>
				</div>
			</c:forEach>
		</div>
	</div>
	<div class="b-foot-contain">
		<p>Copyright © 骑士博客网 | 川ICP备1XXXXXX1号 | 川公网安备1XXXXXXXXXXXX1</p>
	</div>
</div>
