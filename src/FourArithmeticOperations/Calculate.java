package FourArithmeticOperations;

import java.util.ArrayList;
import java.util.Stack;

import org.apache.commons.lang3.math.NumberUtils;

import com.ibm.icu.math.BigDecimal;
/*
逆ポーランド記法の数式の計算 
参考URL : http://lang.sist.chukyo-u.ac.jp/classes/C/2016/StackPorland.pdf
手続き： 
1. 数を読み込んだらスタックに積む 
2. 演算子(＋－＊／ これらを＠で表す）を読み込んだら、スタックから二つの数をpopし（取り出した順にa,bとする
	b ＠ aを計算してスタックに積む 
3. 読み込むものがなければ スタックをpopして終了
*/

public class Calculate {

	private String num;
	private Stack<Double> stack = new Stack<>();
	private StringBuilder sb = new StringBuilder();
	ArrayList<String> list = new ArrayList<>();

	public Calculate(String num) {
		this.num = num;
	}
	public String calculate() {
		ReversePolishNotation rev = new ReversePolishNotation(num);
		list = rev.convertReversePolishNotation();	//数式を逆ポーランド記法に直し、リストで受け取る
		System.out.println("逆ポーランド"+list);
		int countOperator = 0;	//演算子の数をカウント
		int countNum = 0;		//数値の数をカウント
		for(String str : list) {
			if(str.equals("+") || str.equals("-") || str.equals("*") ||str.equals("/"))	countOperator++;
			if(NumberUtils.isNumber(str))	countNum++;
		}
		//数値と演算子が１つしかない、演算子が0で数値が１以上ある場合
		if ((countOperator == 1 && countNum == 1) || (countOperator == 0 && countNum > 0)) {
			return "エラー： 数式が正しくありません";
		}
		for(int i = 0; i < list.size(); i++) {
			String next = list.get(i);
			if(NumberUtils.isNumber(next)) {
				//符号も桁数にカウントするので負数の場合は数字は７桁まで
				if(next.length() > 8)	return "エラー： 9桁以上の数値を入力することができません";
				stack.push(Double.valueOf(next));
			}else {
				//各数値がdouble型なので精度を高めるためにBigDecimalを使用
				BigDecimal value1 = BigDecimal.valueOf(stack.pop());
				BigDecimal value2 = BigDecimal.valueOf(stack.pop());
				if (next.equals("+")) {
					BigDecimal add = value2.add(value1);
					if(add.doubleValue() >= 100000000 || add.doubleValue()  <= -10000000) {
						return "エラー： 計算結果が9桁以上となる加算はできません";
					}
					stack.push(add.doubleValue());
				}
				if (next.equals("-")) {
					BigDecimal sub = value2.subtract(value1);
					if(sub.doubleValue() >= 100000000 || sub.doubleValue() <= -10000000) {
						return "エラー： 計算結果が9桁以上となる減算はできません";
					}
					stack.push(sub.doubleValue());
				}
				if (next.equals("*")) {
					BigDecimal mul = value2.multiply(value1);
					if(mul.doubleValue() >= 100000000 || mul.doubleValue() <= -10000000) {
						return "エラー： 計算結果が9桁以上となる乗算はできません";
					}
					stack.push(mul.doubleValue());
				}
				//除算の場合は小数点以下４桁を四捨五入し、３桁までに直す
				if (next.equals("/")) {
					BigDecimal div = value2.divide(value1, 3, BigDecimal.ROUND_HALF_UP);
					if (value1.doubleValue() == 0) 	return "エラー： 0除算を行ったためエラーが発生しました";
					if(div.doubleValue() >= 100000000 || div.doubleValue() <= -1000000) {
						return "エラー： 計算結果が9桁以上となる除算はできません";
					}
					stack.push(div.doubleValue());
				}
			}
		}
		System.out.println(stack);
		while(!stack.empty()) {
			sb.append(stack.pop());
		}
		num = sb.toString();
		String regex = "[+-]?(0|[1-9]+\\d*.0)";
		//134.0のように少数が出ない数値の場合は.0を除去
		if(num.matches(regex))	num = num.replace(".0", "");
		for (int i= 0; i<num.length();i++) {
			char check = num.charAt(i);
			if(check == 'E') num = "エラー： 計算結果が９桁以上の四則演算は行えません。";
		}
		return num;
	}

}
