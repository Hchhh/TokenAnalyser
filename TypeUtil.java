import java.lang.reflect.Field;

public class TypeUtil {
	
	// 关键字数组
	private final String keyWords[] = {"boolean", "byte","char","double", "else","String",
			"final",  "float", "for","if","int", 
			"long","new", "private", "protected","public", 
			"return", "short", "static", "this", "void","while" };
	
	// 运算符数组
	private final char operators[] = { '+', '-', '*', '/', '=', '>', '<', '&', '|',
			'!' }; 
	
	// 界符数组
	private final char separators[] = { ',', ';', '{', '}', '(', ')', '[', ']', '_',
			':', '.', '"','\\'}; 
	
	
	public boolean isLetter(char ch) {
		return Character.isLetter(ch);
	}

	
	public boolean isDigit(char ch) {
		return Character.isDigit(ch);
	}

	public boolean isUndLine(char ch) {
		if(ch == '_')
			return true;
		else
			return false;
	}
	
	public boolean isDot(char ch) {
		if(ch == '.') {
			return true;
		}
		else 
			return false;
	}
	
	public boolean isKeyWord(String s) {
		for (int i = 0; i < keyWords.length; i++) {
			if (keyWords[i].equals(s))
				return true;
		}
		return false;
	}


	public boolean isOperator(char ch) {
		for (int i = 0; i < operators.length; i++) {
			if (ch == operators[i])
				return true;
		}
		return false;
	}

	public boolean isSeparators(char ch) {
		for (int i = 0; i < separators.length; i++) {
			if (ch == separators[i])
				return true;
		}
		return false;
	}

	public int getType(String args) {
		int type = -1;
		Field[] fields = KeyTypes.class.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(args)) {
				try {
					type = (Integer) field.get(new KeyTypes());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return type;
	}
}
