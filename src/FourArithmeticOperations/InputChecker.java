package FourArithmeticOperations;

import java.util.HashSet;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/*
 * 	このメソッドは入力値に対して、正規表現で判定する文字種が含まれていれば、からのリストを返し、
 * 	正規表現で判定する文字種が含まれていなければ、エラーメッセージをリストに格納して返す。
 * 	
 *
 *  ページの遷移先の決定は、リストがからであればSUCCESSを返し、リストの中身にエラーメッセージが格納されていれば、
 *  ERRORを返す条件判定を作成する。
 */

public class InputChecker {

	//checkPatternメソッドで指定された文字種で入力値をチェック
	public Set<String> checkPattern(String target){
		//エラーメッセージを格納するためのリスト
		Set<String> messageList = new HashSet<String>();
		StringBuilder sb = new StringBuilder();
		int countParentheses = 0;	//括弧の数をカウント
		int countOperator = 0;		//演算子の数をカウント
		String errorMessage = "エラー： 数式が正しくありません";

		//StringUtils.isBlankは文字列がnullでもヌルポがでない
		if(StringUtils.isBlank(target)){
			messageList.add("数式を入力してください");
			return messageList;
		}
		Pattern patternCheck = Pattern.compile("[0-9*+-/() ]+$");		//正規表現を用いてチェックを行う
		Matcher matcher = patternCheck.matcher(target);

		//matches()は全件一致 find()は部分一致
		if(!(matcher.matches())) {
			messageList.add("エラー： 数式で用いる数字・括弧・演算子・スペースは全て半角で入力してください。");
		}else {
			int count = 0;	//空白を除いた文字列の順番を格納
			for(int i = 0; i < target.length(); i++) {
				if (!Character.isWhitespace(target.charAt(i))) {
					char next = target.charAt(i); 	//文字を１文字づつ取り出し
					boolean isNextOperator = (next == '+' || next == '-' || next == '*' || next == '/');
					if (next == '(')	countParentheses++;
					if (next == ')')	countParentheses--;
					if(isNextOperator)	countOperator++;
					if (count > 0) {
						char previous = sb.toString().charAt(count-1);
						boolean isPreviousOperator = (previous == '+' || previous == '-' || previous == '*' || previous == '/');
						//演算子の連続入力、開き括弧の次に演算子、閉じ括弧の前に演算子、開きと閉じ括弧の間を省略、閉じ括弧の数が開き括弧より多い場合
						if((isPreviousOperator && isNextOperator)
								|| (previous == '(' && (next == '+' || next == '*' || next == '/' ))
								|| (isPreviousOperator && next == ')')
								|| (previous == '(' && next == ')')
								|| countParentheses < 0){
							messageList.add(errorMessage);
							return messageList;
						}
					}else {
						//最初の文字が演算子(-を除く)または閉じ括弧だった場合
						if ((next == '+' || next == '*' ||next == '/' ) || next == ')') {
							messageList.add(errorMessage);
							return messageList;
						}
					}
					//最後の文字が演算子または開き括弧、開き括弧の数が閉じ括弧より多い、演算子が一度も入力されない場合
					if(i == (target.length()-1)){
						if ((isNextOperator || next == '(') || countParentheses > 0 || countOperator == 0 ){
							messageList.add(errorMessage);
							return messageList;
						}
					}
					sb.append(next);	//正規表現にマッチし、空白の除かれた文字が１文字づつ入る
					count++;
				}
			}
		}
		return messageList;
	}

}
