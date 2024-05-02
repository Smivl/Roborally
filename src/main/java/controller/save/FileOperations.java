package controller.save;
import com.google.gson.Gson;
import java.io.*;

public class FileOperations {
    Gson gson = new Gson();
    BufferedWriter buff_writ = null;
    BufferedReader buff_read = null;
    public void strToFile(String file_name, String file_content, boolean append) {
        try {
            this.buff_writ = new BufferedWriter(new FileWriter(file_name, append));
            this.buff_writ.write(file_content);
            this.buff_writ.close();
        } catch (IOException ex) {
            System.out.println("an IO exceoption ocured during saving");
        }
    }
    public String readToStr(String file_name){
        try {
            this.buff_read = new BufferedReader(new FileReader(new File(file_name)));
            String file_content = null;
            file_content = this.buff_read.readLine();
            this.buff_read.close();
            return file_content;
        } catch (IOException ex){}
        return null;
    }
    public String object_to_str(Object c){
        return this.gson.toJson(c);
    }



}
