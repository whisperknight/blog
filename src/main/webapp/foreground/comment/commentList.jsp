<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h4>
	<b>评论区</b>
</h4>
<div class="row" style="margin-bottom: 20px">
	<div class="col-md-1">
		<div class="round_icon">
			<a
				href="${pageContext.request.contextPath}/user/${empty currentUser?'login': 'center' }">
				<img
				src="/blog-data/user/${empty currentUser?'default.jpg': currentUser.imageName }"
				alt="${currentUser.nickName }">
			</a>
		</div>
	</div>
	<div class="col-md-10">
		<textarea id="textArea_currentUser" class="form-control" rows="3"
			placeholder="请自觉遵守互联网相关的政策法规，严禁发布色情、暴力、反动的言论。" style="resize: none;"></textarea>
		<p id="textArea_currentUser_helpBlock" class="text-danger"
			style="display: none;">
			<span>请至少输入2个字符！</span>
		</p>
	</div>
	<div class="col-md-1" style="padding: 0px">
		<button onclick="saveComment()" type="button" class="btn btn-primary"
			style="padding: 16px 12px">
			发表<br>评论
		</button>
	</div>
</div>
<hr />
<c:forEach items="${commentList }" var="comment">
	<div class="row">
		<div class="col-md-1">
			<div class="round_icon">
				<img src="/blog-data/user/${comment.user.imageName }"
					alt="${comment.user.nickName }">
			</div>
		</div>
		<div class="col-md-11">
			<div>
				<p>
					<b style="margin-right: 10px; color: #FB7A9C">${comment.user.statusStr }</b><b><a
						href="#">${comment.user.nickName }</a></b>
				</p>
				<p>${comment.content }</p>
				<p class="help-block">
					<span style="margin-right: 10px">${comment.createTimeStr }</span><a
						href="javascript:;" onclick="showTextArea(${comment.id})">回复</a>
				</p>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//异步加载内部评论区域
		function loadInnerComment_${comment.id}(url) {
			if (url == null)
				$.post("../comment/inner/list", {
					commentId : ${comment.id}
				}, function(data) {
					$('#innerCommentDiv_'+${comment.id }).html(data);
				});
			else if(url =='doNothing')
				return;
			else
				$.post("../comment/inner/list", {
					commentId : ${comment.id},
					page:url
				}, function(data) {
					$('#innerCommentDiv_'+${comment.id }).html(data);
				});
		}
	
		$(function(){
			loadInnerComment_${comment.id}();
		});
	</script>
	<div id="innerCommentDiv_${comment.id }"></div>
	<div class="row" style="margin-bottom: 20px; display: none"
		id="textAreaDiv_${comment.id }">
		<div class="col-md-1">
			<div class="round_icon">
				<a
					href="${pageContext.request.contextPath}/user/${empty currentUser?'login': 'center' }">
					<img
					src="/blog-data/user/${empty currentUser?'default.jpg': currentUser.imageName }"
					alt="${currentUser.nickName }">
				</a>
			</div>
		</div>
		<div class="col-md-10">
			<textarea class="form-control" rows="3"
				placeholder="请自觉遵守互联网相关的政策法规，严禁发布色情、暴力、反动的言论。"
				id="textArea_${comment.id }" style="resize: none;"></textarea>
			<p id="textArea_${comment.id }_helpBlock" class="text-danger"
				style="display: none;">
				<span>请至少输入2个字符！</span>
			</p>
		</div>
		<div class="col-md-1" style="padding: 0px">
			<button
				onclick="saveComment(${comment.id },${not empty currentUser?currentUser.id : 0})"
				type="button" class="btn btn-primary" style="padding: 16px 12px">
				发表<br>评论
			</button>
		</div>
	</div>
	<hr>
</c:forEach>
<c:if test="${not empty pagination}">
	<div class="row text-center">
		<ul class="pagination">
			<c:forEach items="${pagination.items }" var="item">
				<li
					class="${item.disabled?'disabled':'' } ${item.active?'active':''}"><a
					href="javascript:void(0)"
					onclick="loadComment('${item.disabled || item.active ? 'doNothing' : item.url }')"><span>${item.number }</span></a></li>
			</c:forEach>
		</ul>
	</div>
</c:if>

<script type="text/javascript">
	var mark_replyToUserId = null;//记录回复用户的ID
	var toSubmit_commentId = null;//记录当前显示的评论框

	function showTextArea(commentId, replyToUserId, replyToUserNickName){
		if(toSubmit_commentId != commentId){
			if(toSubmit_commentId !=null){
				$('#textArea_' + toSubmit_commentId).val('');
				$('#textAreaDiv_' + toSubmit_commentId).hide();
			}
			$('#textAreaDiv_' + commentId).show();
			toSubmit_commentId = commentId;
		}
		
		if(replyToUserId == null){
			$('#textArea_' + commentId).attr('placeholder','请自觉遵守互联网相关的政策法规，严禁发布色情、暴力、反动的言论。');
			mark_replyToUserId = null;			
		}
		else{
			$('#textArea_' + commentId).attr('placeholder','回复 @'+replyToUserNickName+'：');
			mark_replyToUserId=replyToUserId;
		}
	}
	
	function validate(commentId){
		if(commentId == null){
			if($.trim($('#textArea_currentUser').val()).length < 2){
				$('#textArea_currentUser_helpBlock').show();
				return false;
			}else
				return true;
		}
		else{
			if($.trim($('#textArea_' + commentId).val()).length < 2){
				$('#textArea_'+commentId+'_helpBlock').show();
				return false;
			}else
				return true;
		}
	}
	
	function saveComment(commentId, replyToUserId){
		var blogId = ${blogId};//从modelAndView取值
		if(blogId == null || blogId == 0)
			return alert("保存失败！");
		if(${empty currentUser?1:0})
			location.href = "../user/login";
		else if(!validate(commentId))
			return;
		else if(commentId == null){
			$.post('../comment/save',{
				'blog.id':blogId,
				'content':$('#textArea_currentUser').val()
			}, function(data) {
				$('#commentDiv').html(data);
			});
		}else if(mark_replyToUserId == null){
			$.post('../comment/inner/save',{
				'comment.id':commentId,
				'content':$('#textArea_' + commentId).val()
			}, function(data) {
				if(toSubmit_commentId !=null)
					$('#textArea_' + toSubmit_commentId).val('');
				$('#innerCommentDiv_'+commentId).html(data);
			});
		}else{
			$.post('../comment/inner/save',{
				'comment.id':commentId,
				'content':$('#textArea_' + commentId).val(),
				'replyToUser.id':mark_replyToUserId
			}, function(data) {
				if(toSubmit_commentId !=null)
					$('#textArea_' + toSubmit_commentId).val('');
				$('#innerCommentDiv_'+commentId).html(data);
			});
		}
	}
</script>

