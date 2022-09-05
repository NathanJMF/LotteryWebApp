import javax.servlet.http.HttpSession;
import java.io.File;

public class WinCheckCleaner {
    /**
     * Deletes the user file when method is called.
     * @param session given session.
     */
    public static void cleanUp(HttpSession session){
        session.removeAttribute("userNumExist");
        session.removeAttribute("userGetNumExist");
        String fileName;
        String password = (String) session.getAttribute("password");
        if (password.length() > 20){
            fileName = password.substring(0,20)+".txt";
        }
        else{
            fileName = password+".txt";
        }
        File myObj = new File(fileName);
        myObj.delete();
    }
}
