<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.apache.commons.text.StringEscapeUtils" import="model.*,java.util.*" import="java.util.List" %>
<%Comment comment_info = (Comment)request.getAttribute("comment_info"); %>
<%String message_error = (String)request.getAttribute("message_error"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>コメント編集画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">コメント編集画面</p>
	    <%if (message_error != "" && message_error != null) { %>
	    	<p class="error"><%=StringEscapeUtils.escapeHtml4(message_error) %></p>
	    <%} %>
        <form action="UpdateCommentServlet" method="POST">
            <input type="hidden" name="token" value="<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("token")) %>">
            <input type="hidden" name="comment_id" value="<%=comment_info.getCommentId() %>">
            <input type="hidden" name="thread_id" value="<%=comment_info.getThreadId() %>">
            <input type="hidden" name="content_old" value="<%=comment_info.getContent() %>">
            <textarea name="content"><%=comment_info.getContent() %></textarea>
            <button type="submit">コメント編集</button>
        </form>
        <button type="submit">
            <a href="CommentServlet?thread_id=<%=comment_info.getThreadId() %>">コメント欄に戻る</a>
        </button>
    </body>
</html>