import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserLoginNum {
    /**
     * Checks if the login number session counter exists.
     * If it doesn't exist yet it is created and initialised to 1.
     * If it does exist then it is iterated.
     * @param session given session
     */
    public static void checkLoginNumExist(HttpSession session){
        //Checks if session attribute LoginNum already exists. If it does not then it creates it and sets it to 1
        if (session.getAttribute("LoginNum")==null){
            String stringToInput = "";
            int required = 1;
            stringToInput = convertIntToString(required);
            session.setAttribute("LoginNum", stringToInput);
        }
        else{
            int givenValue;
            String givenString;
            givenString = (String) session.getAttribute("LoginNum");
            givenValue = convertStringToInt(givenString);
            givenValue = givenValue+1;
            givenString = convertIntToString(givenValue);
            session.setAttribute("LoginNum", givenString);
        }
    }

    /**
     * Checks to see if the current session is in the allowed number of login attempts.
     * @param session given session.
     * @return returns whether or not the current session is in or out of the acceptable range.
     */
    public static boolean checkLoginNumVal(HttpSession session){
        int currentVal;
        currentVal = convertStringToInt((String) session.getAttribute("LoginNum"));
        if(currentVal>3){
            return false;
        }
        return true;
    }

    /**
     * Converts a string to int without losing information.
     * @param givenString String holding the information to be converted.
     * @return Returns the converted string.
     */
    public static int convertStringToInt(String givenString){
        int convertedInt;
        convertedInt = Integer.parseInt(givenString);
        return convertedInt;
    }

    /**
     * Converts and int  to string without losing information.
     * @param givenInt Int holding the information to be converted.
     * @return Returns the converted int.
     */
    public static String convertIntToString(int givenInt){
        String convertedString;
        convertedString = String.valueOf(givenInt);
        return convertedString;
    }

    /**
     * Checks to see if the user should be redirected to error.jsp for if the user login num is out of range.
     * @param checker The boolean value determining whether the user should be redirected.
     * @param request given request
     * @param response given response
     * @throws ServletException
     * @throws IOException
     */
    public static void checkLoginNumRedirect(boolean checker, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!checker){
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Number of logins exceeds 3!");
            dispatcher.forward(request, response);
        }
    }
}
