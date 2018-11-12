package FourArithmeticOperations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class ReversePolishNotation {

	private String num;
	private Stack<Character> stack = new Stack<>();
	private StringBuilder sb = new StringBuilder();
	private ArrayList<String> list = new ArrayList<>();
	private HashMap<Character, Integer> map = new HashMap<>();

	public ReversePolishNotation(String num) {
		this.num = num;
	}
	public ArrayList<String> convertReversePolishNotation() {
		map.put('+', 1);
		map.put('-', 1);
		map.put('*', 2);
		map.put('/', 2);
		//空白を削除
		for(int i = 0; i<num.length(); i++) {
			if (!Character.isWhitespace(num.charAt(i))) {
				sb.append(num.charAt(i));
			}
		}
		num = sb.toString();
		sb.delete(0, sb.length());
		//逆ポーランド記法に変換
		for(int i = 0; i < num.length(); i++) {
			int j = 0;
			char next = num.charAt(i);
			char previous = i>0 ? num.charAt(i-1) : next;
			if(Character.isDigit(next)) {
				// *が省略されていた場合にスタックに*を挿入
				if (i != 0 && previous == ')')	stack.push('*');
				// (-[数字]の場合は-を演算子ではなく負数を表すと解釈する
				if(i > 1 && (num.charAt(i-2) == '(' && previous == '-')) {
					sb.append('-');
					stack.pop();	//先に-が演算子として判定され、スタックにプッシュされているのでここで取り除く
				}
				//数式の先頭が負数の場合
				if(i == 1 && previous == '-') {
					sb.append('-');
					stack.pop();
				}
				//数字が出現すると演算子や括弧が出るまでループする
				for(j = i; j < num.length(); j++) {
					if(Character.isDigit(num.charAt(j))) {
						sb.append(num.charAt(j));
					}else {
						break;
					}
				}
				i = --j;	//取り出した文字数分カウンタ変数が進むので全体のループのカウンタ変数を更新している
				list.add(sb.toString());
				sb.delete(0, sb.length());
			}else if(next == ')'){
				while(stack.peek() != '(') {
					list.add(String.valueOf(stack.pop()));
				}
				stack.pop();
			}else if(next == '(') {
				//)(の間に＊が省略されている場合は＊をスタックに挿入
				if (i != 0 && (previous== ')' || Character.isDigit(previous))){
					stack.push('*');
				}
				stack.push(next);
			}else {
				while(!stack.empty()) {
					if (stack.peek() != '(') {
						//スタックの最上段の演算子よりも、数式から取り出した演算子の優先順位が高い場合
						if(map.get(stack.peek()) < map.get(next)) {
							break;
						}else {
							list.add(String.valueOf(stack.pop()));
						}
					}else {
						break;
					}
				}
				stack.push(next);
			}
			System.out.println("StingBuilder  " + list.toString());
			System.out.println(stack);
		}
		while(!stack.empty()) {
			list.add(String.valueOf(stack.pop()));
		}
		return list;
	}
}
