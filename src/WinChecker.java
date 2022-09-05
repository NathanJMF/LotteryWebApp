import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/WinChecker")
public class WinChecker extends HttpServlet {
    /**
     * Converts the string array containing the user numbers and converts it into a comma seperated string.
     * This string is then compared against the winVal string.
     * If they match then the user has won.
     * If they dont then the user has lost.
     * the win or lose message is created and passed as the message when redirecting to the account.jsp page.
     * Also cleans up the user file after checking.
     * @param request given request
     * @param response given response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String[] userNumbers = (String[]) session.getAttribute("draws");
        if(userNumbers==null){userNumbers = new String[]{};};
        String convertedOutput = "";
        // Converts string array containing user numbers into string.
        for (int x = 0; x< userNumbers.length; x++){
            if (x<userNumbers.length-1){
                convertedOutput = convertedOutput+userNumbers[x]+",";
            }
            else{
                convertedOutput = convertedOutput+userNumbers[x];
            }
        }
        String winMessage;
        // Checks to see if the user has won and applies the appropriate message.
        if (convertedOutput.equals(session.getAttribute("winVal"))){
            winMessage = "Congratulations, you have won!";
        }
        else{
            winMessage="Sorry, you have not won";
        }
        // Cleans up user file and redirects to account.jsp
        WinCheckCleaner.cleanUp(session);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("message", winMessage);
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
