import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@WebFilter("/UserLogin")
public class FieldFilterLogIn implements Filter {
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
        String userName = request.getParameter("username");
        String password = request.getParameter("password");


        try{
            // Checks to see if the given credentials contain and illegal strings.
            if(!(Arrays.stream(banned).anyMatch(userName::contains) || Arrays.stream(banned).anyMatch(password::contains))){
                chain.doFilter(req, resp);

            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Login unsuccessful filter");
                dispatcher.forward(req, resp);
            }
        }
        catch (Exception e) {
            // When ending the current session null values are passed so an exception has to be made.
            if(userName == null || password == null){
                chain.doFilter(req, resp);

            }
            else{
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Login unsuccessful filter");
                dispatcher.forward(req, resp);
            }
            e.printStackTrace();
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}