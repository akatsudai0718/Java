<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String message_error = (String)request.getAttribute("message_error"); %>
<%String message_done = (String)request.getAttribute("message_done"); %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>新規登録画面</title>
        <link rel="stylesheet" href="bbs_style.css">
    </head>
    <body>
        <p class="page_title">新規登録画面</p>
        <%if (message_error != "" && message_error != null) { %>
        	<p class="error"><%=message_error %></p>
        <%} %>
        <%if (message_done != "" && message_done != null) { %>
        	<p class="done"><%=message_done %></p>
        <%} %>
        <form action="EntryServlet" method="POST">
            <p>ユーザー名を入力</p>
            <input type="text" name="user_name">
            <p>メールアドレスを入力</p>
            <input type="text" name="mail">
            <p>パスワードを入力（半角英数字8文字以上24文字以下）</p>
            <p><input type="password" class="field" id="password" name="password"></p>
            <p>パスワードをもう一度入力</p>
            <p><input type="password" class="field" id="password" name="password2"></p>
            <button type="submit">ユーザーを登録する</button>
        </form>
        <br>
        <button type="submit">
            <a href="LoginServlet">ログイン画面に戻る</a>
        </button>
    </body>
</html>