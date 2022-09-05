import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/CreateAccount")
public class FieldFilterCreate implements Filter {
    public void destroy() {
    }

    /**
     * Filter to check if the user has entered any illegal string in the fields.
     * @param req given req
     * @param resp given resp
     * @param chain given chain
     * @throws ServletException
     * @throws IOException
     */
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String[] banned = {"<", ">", "!", "{", "}", "insert", "into", "where", "script", "delete", "input"};
        HttpServletRequest request = (HttpServletRequest) req;
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String isAdmin = request.getParameter("isAdmin");
        // Check to see if any of the given data contains any illegal Strings.
        // If not it continues to CreateAccount.java.
        // Else user is redirected to error.jsp
        try{
            if(!(Arrays.stream(banned).anyMatch(userName::contains) || Arrays.stream(banned).anyMatch(password::contains)
                    || Arrays.stream(banned).anyMatch(phone::contains) || Arrays.stream(banned).anyMatch(email::contains)
                    || Arrays.stream(banned).anyMatch(lastname::contains) || Arrays.stream(banned).anyMatch(firstname::contains))){
                chain.doFilter(req, resp);
            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Account creation unsuccessful");
                dispatcher.forward(req, resp);
            }
        }
        catch (Exception e) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Account creation unsuccessful");
            dispatcher.forward(req, resp);
            e.printStackTrace();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}