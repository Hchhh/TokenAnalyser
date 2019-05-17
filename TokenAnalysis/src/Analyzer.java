
public class Analyzer extends TypeUtil {
	private StringBuffer buffer = new StringBuffer();
	private int i = 0;
	private char ch;// 存放读取的字符
	private String strToken;// 存放构成一个token的字符串

	public Analyzer() {

	}

	public Analyzer(String fileSrc) {
		FileUtil.readFile(buffer, fileSrc);
	}

	// 核心代码部分
	public void analyse() {
		strToken = "";
		FileUtil.clearFile();
		while (i < buffer.length()) {
			nextChar();
			nextNotSpace();

			// 传入首字符为字母
			if (isLetter(ch)) {
				while (isLetter(ch) || isDigit(ch) || isUndLine(ch)) {
					conToken();
					nextChar();
				}
				// 回调
				retract();
				if (isKeyWord(strToken)) {
					writeFile(strToken, strToken);
				} else {
					writeFile("id", strToken);
				}
				// 清空strToken
				strToken = "";
			} // 标识符关键字识别结束

			// 读入首字符为数字
			else if (isDigit(ch)) {
				while (isDigit(ch)) {
					conToken();
					nextChar();
				}
				if (isDot(ch)) {
					conToken();
					nextChar();
					// 小数点后加数字
					while (isDigit(ch)) {
						conToken();
						nextChar();
					}
				}
				if (!isLetter(ch) || !isOperator(ch) || !isUndLine(ch) || !isDot(ch)) {
					retract();
					writeFile("digit", strToken);
				} else
					writeFile("error", strToken);
				strToken = "";
			} // 数字识别结束

			else if (isOperator(ch)) {
				if (ch == '/') {
					nextChar();
					conToken();
					if (ch == '*') {// 为/*注释
						while (true) {
							conToken();
							nextChar();
							if (ch == '*') {// 为多行注释结束
								nextChar();
								if (ch == '/') {
									nextChar();
									writeFile("MultiComment",strToken);
									break;
								}
							}
						}
					}
					/*if (ch == '/') {
						while (ch != '\r') {
							nextChar();
						}
						writeFile("SingleComment",strToken);
					}*/
					retract_div();
				}

				switch (ch) {
				case '+':
					conToken();
					nextChar();
					if (ch == '=') {
						conToken();
						writeFile("eqp", strToken + "");
					} else {
						retract();
						writeFile("plus", ch + "");
					}
					strToken = "";
					break;

				case '-':
					conToken();
					nextChar();
					if (ch == '=') {
						conToken();
						writeFile("eqm", strToken + "");
					} else {
						retract();
						writeFile("minus", ch + "");
					}
					strToken = "";
					break;
				case '*':
					conToken();
					nextChar();
					if (ch == '=') {
						conToken();
						writeFile("eqc", strToken + "");
					} else {
						retract();
						writeFile("prod", ch + "");
					}
					strToken = "";
					break;
				case '/':
					conToken();
					nextChar();
					if(ch == '=') {
						conToken();
						writeFile("eqd", strToken+"");
					}
					else {
						retract();
						writeFile("div",ch+"");
						}
					strToken = "";
					break;
				case '>':
					writeFile("gt", ch + "");
					break;
				case '<':
					writeFile("st", ch + "");
					break;
				case '=':
					conToken();
					nextChar();
					if(ch == '=') {
						conToken();
						writeFile("EQQ", strToken+"");
					}
					else {
						retract();
						writeFile("EQ",'='+"");
						}
					strToken = "";
					break;
				case '&':
					conToken();
					nextChar();
					if(ch == '=') {
						conToken();
						writeFile("andAnd", strToken+"");
					}
					else {
						retract();
						writeFile("and",ch+"");
						}
					strToken = "";
					break;
				case '|':
					conToken();
					nextChar();
					if(ch == '=') {
						conToken();
						writeFile("orOr", strToken+"");
					}
					else {
						retract();
						writeFile("or",ch+"");
						}
					strToken = "";
					break;
				case '!':
					conToken();
					nextChar();
					if(ch == '=') {
						conToken();
						writeFile("notEQ", strToken+"");
					}
					else {
						retract();
						writeFile("not",ch+"");
						}
					strToken = "";
					break;
				default:
					break;
				}
			}//结束运算符识别
			
			//首字符为分隔符
			else if(isSeparators(ch)) {
				switch(ch) {
				case ',':
					writeFile("comma",ch+"");
					break;
				case '.':
					writeFile("dot",ch+"");
					break;
				case ';':
					writeFile("semicol",ch+"");
					break;
				case '{':
					writeFile("lBrace",ch+"");
					break;
				case '}':
					writeFile("rBrace",ch+"");
					break;
				case '(':
					writeFile("lParenthesis",ch+"");
					break;
				case ')':
					writeFile("rParenthesis",ch+"");
					break;
				case '_':
					writeFile("underLine",ch+"");
					break;
				case '[':
					writeFile("lBracket",ch+"");
					break;
				case ']':
					writeFile("rBracket",ch+"");
					break;
				case ':':
					writeFile("colon",ch+"");
					break;
				case '"':
					writeFile("dQuote",ch+"");
					break;
				case '\'' :
					writeFile("quote",ch+"");
					break;
				}
			}else
				writeFile("error",ch+"");
		}
	}

	public void nextChar() {
		ch = buffer.charAt(i);
		i++;
	}

	public void nextNotSpace() {
		while (Character.isWhitespace(ch)) {
			nextChar();
		}
	}

	public void conToken() {
		strToken += ch;
	}

	/** 将搜索指示器回调一个字符位置，将ch值为空白字 */
	public void retract() {
		i--;
		ch = ' ';
	}
	public void retract_div() {
		i--;
		ch = '/';
	}

	// agrs_1 被写入文件的字符种类名
	// args_2 读取的字符串
	public void writeFile(String name, String s) {
		int temp = getType(name.toUpperCase());
		System.out.println("(" + name + ", " + s + ")");
		name = "(" + temp + ", " + s + ")" + "\r\n";
		FileUtil.writeFile(name);
	}
}
