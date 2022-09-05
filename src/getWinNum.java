import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class getWinNum {
    /**
     * Reads the winVal.txt to retrieve the wining
     * @param session session to setAttribute winVal containing the wining values.
     */
    public static void getWinningsNumbers(HttpSession session){
        // Reads the winVal.txt to get the wining numbers to be compared to the user numbers.
        createWinValFile();
        try {
            File myObj = new File("winVal.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                session.setAttribute("winVal",data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the winVal.txt for holding the wining values.
     */
    public static void createWinValFile(){
        // Creates the winVal.txt that will hold the wining numbers.
        try {
            File myObj = new File("winVal.txt");
            myObj.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeWinValFile();
    }

    /**
     * Writes the wining values to the winVal.txt for storing.
     */
    public static void writeWinValFile(){
        // Writes the wining numbers to the winVal.txt
        try {
            FileWriter myWriter = new FileWriter("winVal.txt");
            myWriter.write("1,2,3,4,5,6");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
