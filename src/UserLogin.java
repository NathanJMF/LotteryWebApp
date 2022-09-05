import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;


@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

    private Connection conn;
    private Statement stmt;

    /**
     * If the given user credentials given match a record in the database they are logged in.
     * @param request request given by index.jsp
     * @param response response given by index.jsp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        String DB_URL = "jdbc:mysql://db:3306/lottery";
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            HttpSession session = request.getSession();
            // Goes through all session attributes and deletes all attributes except for loginNum.
            // loginNum is kept to keep track of number of logins through the session.
            Enumeration<String> attributes = session.getAttributeNames();
            while (attributes.hasMoreElements()) {
                String attribute = (String) attributes.nextElement();
                if (!attribute.equals("LoginNum")){
                    session.removeAttribute(attribute);
                }
            }
            // If the user has gone to error.jsp and decided to end the session on login every attribute is deleted.
            //T his is to start a blank session.
            if (request.getParameter("endSession")!=null){
                attributes = session.getAttributeNames();
                while (attributes.hasMoreElements()) {
                    String attribute = (String) attributes.nextElement();
                    session.removeAttribute(attribute);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            }
            session.setAttribute("userNumExist",false);
            // User login number checking
            UserLoginNum.checkLoginNumExist(session);
            boolean checker = UserLoginNum.checkLoginNumVal(session);
            UserLoginNum.checkLoginNumRedirect(checker, request, response);
            // This hashes the given password.
            if (username!=null && password!=null){
                password = passwordHash.doHashing(password);
            }
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String SQL = "SELECT * FROM userAccounts WHERE Username='" + username + "' AND Pwd='" + password + "'";
            ResultSet x = stmt.executeQuery(SQL);
            if(x.next()) {
                session.setAttribute("firstname", x.getString("Firstname"));
                session.setAttribute("lastname", x.getString("Lastname"));
                session.setAttribute("username", username);
                session.setAttribute("email", x.getString("Email"));
                session.setAttribute("password", password);
                session.setAttribute("isAdmin", x.getString("Isadmin"));
                session.setAttribute("userNumExist",false);
                // Checks to see if the logged in user is an admin
                boolean adminVal = AdminCheck.IsAdminCheck(session);
                if(adminVal){
                    // query database and get results
                    ResultSet rs = stmt.executeQuery("SELECT * FROM userAccounts");
                    // This creates and populates the admin table
                    String content = AdminCheck.BuildAdminTable(rs);
                    // close connection
                    conn.close();
                    // display admin_home.jsp page with given content above if successful
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/admin_home.jsp");
                    request.setAttribute("data", content);
                    dispatcher.forward(request, response);
                }
                else{
                    // This creates winVal.txt and writes the wining numbers to it.
                    // It then reads those wining numbers and sets session attribute winVal that holds the wining values.
                    getWinNum.getWinningsNumbers(session);
                }
                // On successful login user is sent to the account.jsp page.
                RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
                request.setAttribute("message", "Login success");
                dispatcher.forward(request, response);
            }
            else {
                // If at any point login fails user is sent to the error.jsp page.
                RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
                request.setAttribute("message", "Login unsuccessful");
                dispatcher.forward(request, response);
            }
        } catch (Exception se) {
            se.printStackTrace();
            // display error.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", "Database Error, Please try again");
            dispatcher.forward(request, response);
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
