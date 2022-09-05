import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminCheck {
    /**
     * Gets the isAdmin session attribute and returns if the user is an admin or not.
     * @param session session given.
     * @return Returns boolean indicating whether the user is an admin or not.
     */
    public static boolean IsAdminCheck(HttpSession session){
        String adminValue = (String) session.getAttribute("isAdmin");
        if (adminValue.equals("Yes")){
            return true;
        }
        return false;
    }

    /**
     * Constructs the html table containing the user data to be sent to admin_home.jsp
     * @param rs Result set containing the user records
     * @return Returns the html table holding the user data.
     * @throws SQLException
     */
    public static String BuildAdminTable(ResultSet rs) throws SQLException {
        // create HTML table text
        String content = "<table border='1' cellspacing='2' cellpadding='2' width='100%' align='left'>" +
                "<tr><th>First name</th><th>Last name</th><th>Email</th><th>Phone number</th><th>Username</th><th>Admin</th></tr>";

        // add HTML table data using data from database
        while (rs.next()) {
            content += "<tr><td>"+ rs.getString("Firstname") + "</td>" +
                    "<td>" + rs.getString("Lastname") + "</td>" +
                    "<td>" + rs.getString("Email") + "</td>" +
                    "<td>" + rs.getString("Phone") + "</td>" +
                    "<td>" + rs.getString("Username") + "</td>" +
                    "<td>" + rs.getString("Isadmin") + "</td></tr>";
        }
        // finish HTML table text
        content += "</table>";
        return content;
    }
}
