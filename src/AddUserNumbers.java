import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;



@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {
    /**
     * This gets the given user numbers from request params, encrypts them and then creates a user txt file to store them in.
     * @param request request given by account.jsp
     * @param response response given by account.jsp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String number1 = request.getParameter("number1");
        String number2 = request.getParameter("number2");
        String number3 = request.getParameter("number3");
        String number4 = request.getParameter("number4");
        String number5 = request.getParameter("number5");
        String number6 = request.getParameter("number6");
        String UserNumbers = (number1+","+number2+","+number3+","+number4+","+number5+","+number6);
        // Creates a byte array of the encrypted user numbers.
        byte[] encryptedUserNumbers = NumberEncryptDecrypt.numEncrypt(UserNumbers, request);
        HttpSession session = request.getSession();
        session.setAttribute("userNumExist",true);
        // Creates the user txt file holding the encrypted user numbers.
        WriteTXT.CreateFile(encryptedUserNumbers, (String) session.getAttribute("password"));
        // Redirects back to the account.jsp page.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
        request.setAttribute("message", "");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }
}
