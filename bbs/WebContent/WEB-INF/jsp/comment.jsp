<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="org.apache.commons.text.StringEscapeUtils" import="model.*,java.util.*" import="java.util.List" %>
<%List<Comment> list = (List<Comment>)request.getAttribute("list"); %>
<%String message_error = (String)request.getAttribute("message_error"); %>
<%String message_done = (String)request.getAttribute("message_done"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><%=request.getAttribute("thread_name") %></title>
        <link rel="stylesheet" href="bbs_style.css">
       	<script type="text/javascript" src="main.js"></script>
    </head>
    <body>
        <p class="page_title"><%=StringEscapeUtils.escapeHtml4((String)request.getAttribute("thread_name")) %></p>
	    <%if (message_error != "" && message_error != null) { %>
	    	<p class="error"><%=StringEscapeUtils.escapeHtml4(message_error) %></p>
	    <%} %>
	    <%if (message_done != "" && message_done != null) { %>
	    	<p class="done"><%=StringEscapeUtils.escapeHtml4(message_done) %></p>
	    <%} %>
        <table>
            <thead>
                <tr>
                    <td>ユーザー名</td>
                    <td>コメント</td>
                    <td>作成日時</td>
                    <td>更新日時</td>
                </tr>
            </thead>
            <tbody>
                <%for(Comment comment:list) { %>
                    <tr>
                        <td>
                            <%=comment.getUserName() %>
                        </td>
                        <td>
                            <%=comment.getContent() %>
                        </td>
                        <td>
                            <%=comment.getCreatedAt() %>
                        </td>
                        <td>
                            <%=comment.getUpdatedAt() %>
                        </td>
                        <%if (comment.getUserId() == (int)session.getAttribute("user_id")) { %>
                            <td class="button_in_table">
                            	<form action="UpdateCommentServlet" method="GET">
                                	<input type="hidden" name="comment_id" value="<%=comment.getCommentId() %>">
                                	<input type="hidden" name="thread_id" value="<%=comment.getThreadId() %>">
                                	<button type="submit">コメント編集</button>
                            	</form>
                            </td>
                            <td class="button_in_table">
	                            <form action="DeleteCommentServlet" method="POST">
	                                <input type="hidden" name="token" value="<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("token")) %>">
	                                <input type="hidden" name="comment_id" value="<%=comment.getCommentId() %>">
	                               	<input type="hidden" name="thread_id" value="<%=comment.getThreadId() %>">
	                                <button type= "submit" onclick="return confirm_test()">コメント削除</button>
	                            </form>
                            </td>
                        <%} %>
                    </tr>
                <%} %>
            </tbody>
        </table>
        <br><br>
        <form action="InsertCommentServlet" method='POST'>
            <input type="hidden" name="token" value="<%=StringEscapeUtils.escapeHtml4((String)session.getAttribute("token")) %>">
            <input type="hidden" name="thread_id" value="<%=request.getAttribute("thread_id") %>">
            <table>
                <tr>
                    <th>コメント</th>
                    <td><textarea name="content"></textarea></td>
                    <td class="button_in_table">
                        <button type= "submit">書き込む</button>
                    </td>
                </tr>
            </table>
        </form>
        <br>
        <button type="submit">
            <a href="ThreadServlet">ホームへ戻る</a>
        </button>
        <br><br>
        <button type="submit">
            <a href="LogoutServlet">ログアウトする</a>
        </button>
    </body>
</html>