<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>計算画面</title>
</head>
<body>
	<h1>四則演算の計算機</h1>
	<s:form action="CalculateAction">
		<dl>
			<dt>使い方</dt>
			<dd>
				数式を入力し、四則演算を行います。<br><br>
				入力は半角のみで、入力可能な文字種は以下の4つです。<br>
				1. 数字　0~9　負数を使用するには()で括る。例 (-9)　但し、数式の先頭で負数を用いる場合は括弧で括らなくても良い。 <br>
				2. 括弧　( )<br>
				3. 演算子　+　-　*　/<br>
				4. 半角スペース　数式を見やすくするため任意の数で入力可能です。<br><br>
				各数値は８桁まで入力可能です。負数の場合は「-」も桁数に含む。<br>
			   「=」を入力する必要はありません。<br><br>
			    計算結果は整数部分が８桁、小数点以下３桁まで表示します。整数部分が９桁を超える場合は表示できません。<br>
				計算機の入力例1　-12321/76+(-34981/129)4+(35/2)*2-99*9/3+(-32*21)/(-33) <br>
				計算機の入力例2　10*10/3*(-10)/2*100/13/(-2)*4+3(12/6)*2-9000+(2)9/10-29*3<br>
			</dd>
		</dl><br>
		<h3>入力フィールド</h3>
		<!-- セッションにエラーメッセージが格納されていた場合は表示する -->
		<s:if test="!#session.errorMessageList.isEmpty()">
			<s:iterator value="#session.errorMessageList">
				<span style="color: #ff0000;"><s:property /></span>
			</s:iterator>
		</s:if><br>
		<s:textfield name="num" placeholder="数式を入力してください。" size="100" />
		<s:submit value="計算する" />
	</s:form>
</body>
</html>
