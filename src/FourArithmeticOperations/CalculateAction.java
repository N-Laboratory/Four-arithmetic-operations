package FourArithmeticOperations;

import java.util.Map;
import java.util.Set;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;


/*
 *  (問題３）
 *	通常の数式（8桁までの整数値の四則演算のみ、括弧も使えるようにする）を入
 *	力し計算結果を出力するプログラムをC++あるいはJavaにより作成せよ。一般利
 *	用者が入力する可能性のある数式を出来る限り幅広く処理できるように仕様を明
 *	確に定義した上で、数式エラー時には分かりやすいエラーメッセージを出力でき
 *	るようにすること。
 */
public class CalculateAction extends ActionSupport implements SessionAware{
	private String num;
	private Map<String, Object> session;
	
	public String execute() {

		session.clear();
		String result = ERROR;

		//入力のチェック
		InputChecker inputChecker = new InputChecker();
		Set<String> messageList = inputChecker.checkPattern(num);
		if (!messageList.isEmpty()) {
			session.put("errorMessageList", messageList);
			return result;
		}
		//四則演算を行う
		Calculate cal = new Calculate(num);
		String resultNum = cal.calculate();
		if (resultNum.startsWith("エ")) {
			session.put("errorMessageList", resultNum);
			return result;
		}
		session.put("num", num);	//入力された数式をセッションに格納
		session.put("resultNum", resultNum);	//計算結果をセッションに格納
		result = SUCCESS;
		return result;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
