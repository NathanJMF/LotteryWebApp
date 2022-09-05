import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {
    /**
     * Reads the hex string from the user file, converts it into a byte array, and then decrypts that byte array to give the original user numbers.
     * @param request given request from account.jsp
     * @param response given response from account.jsp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String[] decryptedUserNumberArray = new String[0];
        String encryptedNumbers = "";
        String fileName;
        String password = (String) session.getAttribute("password");
        // Gets file name.
        if (password.length() > 20){
            fileName = password.substring(0,20)+".txt";
        }
        else{
            fileName = password+".txt";
        }
        try{
            // Finds the user file and reads the encrypted numbers into encryptedNumbers.
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()){
                encryptedNumbers = encryptedNumbers + myReader.nextLine();
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Converts the hex string into a byte array.
        byte[] encryptedItem = new byte[0];
        try {
            encryptedItem = WriteTXT.hexToByteArray(encryptedNumbers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Takes the encrypted byte array containing the user numbers and decrypts them into decryptedUserNuberArray.
        try {
            String decryptedNumbers = NumberEncryptDecrypt.numDecrypt(encryptedItem, request);
            decryptedUserNumberArray = StringToArray.getNumArray(decryptedNumbers);
            
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        // Redirects back to account.jsp.
        session.setAttribute("userGetNumExist",true);
        session.setAttribute("draws",decryptedUserNumberArray);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("draws",decryptedUserNumberArray);
        request.setAttribute("message", "");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
