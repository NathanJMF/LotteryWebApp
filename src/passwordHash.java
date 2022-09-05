import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class passwordHash {
    /**
     * Takes in the string password and hashes it using the SHA algorithm and returns the new hashed password string.
     * @param password The given user password.
     * @return Returns the hashed password.
     */
    protected static String doHashing (String password){
        // Hashes the password using SHA
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            messageDigest.update(password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : resultByteArray){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
