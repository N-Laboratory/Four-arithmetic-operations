<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>結果画面</title>
</head>
<body>
	<h1>計算結果</h1>
	<h3>
		<!-- セッションに格納されている数式と計算結果を表示 -->
		<s:property value="%{#session.num}" />
		の計算結果は
		<s:property value="%{#session.resultNum}" />
		です。
	</h3>
	<br>
	<!-- セッションをクリアしてindex.jspに遷移する。 -->
	<a href="<s:url action='SessionClearAction'></s:url>">戻る</a>
</body>
</html>