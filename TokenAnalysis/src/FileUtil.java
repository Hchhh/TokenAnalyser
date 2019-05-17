import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
	public static boolean readFile(StringBuffer buffer, String fileSrc){
		try {
			FileReader fileReader = new FileReader(fileSrc);
			BufferedReader br = new BufferedReader(fileReader);
			String temp = null ;
			while((temp = br.readLine())!= null) {
				buffer.append(temp);
			}
			return true;
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static boolean writeFile(String args) {
		try {
			File file = new File("./src/output.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(args);
			bw.close();
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
	public static boolean clearFile() {
		try {
			File file = new File("./src/output.txt");
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
			return true;
		}catch(IOException e) {
			e.printStackTrace();
			return true;
		}
	}
	
}
