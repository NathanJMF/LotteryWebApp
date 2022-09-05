import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;

@WebServlet("/CreateAccount")
public class CreateAccount extends HttpServlet {

    private Connection conn;
    private PreparedStatement stmt;

    /**
     * Gets the given credentials and creates a user record in the database using them.
     * It then logs them into that account.
     * @param request request given by index.jsp
     * @param response response given by index.jsp
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // MySql database connection info
        String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        String USER = "user";
        String PASS = "password";
        String DB_URL = "jdbc:mysql://db:3306/lottery";
        // get parameter data that was submitted in HTML form (use form attributes 'name')
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String isAdmin = request.getParameter("isAdmin");
        try{
            // Hashes user password.
            password = passwordHash.doHashing(password);
            HttpSession session = request.getSession();
            session.setAttribute("firstname", firstname);
            session.setAttribute("lastname", lastname);
            session.setAttribute("username", username);
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            session.setAttribute("isAdmin", isAdmin);
            session.setAttribute("userNumExist",false);
            // User login number checking
            UserLoginNum.checkLoginNumExist(session);
            boolean checker = UserLoginNum.checkLoginNumVal(session);
            UserLoginNum.checkLoginNumRedirect(checker, request, response);
            // create database connection and statement
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            // Create sql query
            String query = "INSERT INTO userAccounts (Firstname, Lastname, Email, Phone, Username, Pwd, Isadmin)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            // set values into SQL query statement
            stmt = conn.prepareStatement(query);
            stmt.setString(1,firstname);
            stmt.setString(2,lastname);
            stmt.setString(3,email);
            stmt.setString(4,phone);
            stmt.setString(5,username);
            stmt.setString(6,password);
            stmt.setString(7,isAdmin);
            // execute query and close connection
            stmt.execute();
            // Checks to see if the created user is an admin
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
            conn.close();
            // display account.jsp page with given message if successful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/account.jsp");
            request.setAttribute("message", firstname+", you have successfully created an account");
            dispatcher.forward(request, response);

        } catch(Exception se){
            se.printStackTrace();
            // display error.jsp page with given message if unsuccessful
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            request.setAttribute("message", firstname+", this username/password combination already exists. Please try again");
            dispatcher.forward(request, response);
        }
        finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }
            catch(SQLException se2){}
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }


    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
