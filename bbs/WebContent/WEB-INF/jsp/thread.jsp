<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.apache.commons.text.StringEscapeUtils" import="model.*,java.util.*" import="java.util.List" import="model.Thread"%>
<%List<Thread> list = (List<Thread>)request.getAttribute("list"); %>
<%String message_error = (String)request.getAttribute("message_error"); %>
<%String message_done = (String)request.getAttribute("message_done"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>〇〇掲示板</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/bbs_style.css">
	<script type="text/javascript" src="main.js"></script>
</head>
<%-- <%session.setMaxInactiveInterval(10); %> --%>
<body>
	<p class="page_title">〇〇掲示板</p>
	<p>ユーザー名：<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("user_name")) %></p>
	<form method='POST' action='InsertThreadServlet'>
		<input type="hidden" name="token" value="<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("token"))  %>">
		<table>
			<tr>
				<th>スレッド名</th>
				<td><input type="text" name="thread_name"></td>
				<td class="button_in_table"><button type="submit">スレッド作成</button></td>
			</tr>
		</table>
	</form>
	<br>
    <%if (message_error != "" && message_error != null) { %>
    	<p class="error"><%=StringEscapeUtils.escapeHtml4(message_error) %></p>
    <%} %>
    <%if (message_done != "" && message_done != null) { %>
    	<p class="done"><%=StringEscapeUtils.escapeHtml4(message_done) %></p>
    <%} %>
	<%if(list !=null && list.size()>0){ %>
		<table>
			<tr>
				<th>投稿者</th>
				<th>スレッド名</th>
			</tr>
			<%for(Thread thread:list) { %>
			<tr>
				<td><%=thread.getUserName() %></td>
				<td>
					<a href="CommentServlet?thread_id=<%=thread.getThreadId() %>"><%=thread.getThreadName() %></a>
				</td>
				<td class="button_in_table">
					<%if (thread.getUserId() == (int)session.getAttribute("user_id")) { %>
	                	<form action="DeleteThreadServlet" method="POST">
	                    	<input type="hidden" name="token" value="<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("token")) %>">
	                        <input type="hidden" name="thread_id" value="<%=thread.getThreadId() %>">
	                        <button type="submit" onclick="return confirm_test()">スレッド削除</button>
	                	</form>
	                <%} %>
                </td>
			</tr>
			<%} %>
		</table>
	<%} %>
	<br>
    <button type="submit">
        <a href="LogoutServlet">ログアウトする</a>
    </button>
</body>
</html>