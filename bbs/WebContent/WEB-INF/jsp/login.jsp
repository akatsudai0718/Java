<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String message_error = (String)request.getAttribute("message_error"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>ログイン画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">ログイン画面</p>
        <%if (message_error != null) { %>
        	<p class="error"><%=message_error %></p>
        <%} %>
        <form action="LoginServlet" method="POST">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力</p>
            <p><input type="password" class="field" id="password" name="password"></p>
            <p><button type="submit">ログインする</button></p>
        </form>
        <button type="submit">
        	<a href="EntryServlet">新規登録をする</a>
    	</button>
    </body>
</html>