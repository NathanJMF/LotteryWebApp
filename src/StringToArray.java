import java.util.Arrays;

public class StringToArray {
    /**
     * Takes the decrypted user numbers and turns it into a string array.
     * @param decryptedUserNumbers The decrypted user number string.
     * @return returns the string array holding the user numbers.
     */
    public static String[] getNumArray(String decryptedUserNumbers){
        String[] userNumbers = decryptedUserNumbers.split(",");
        return userNumbers;
    }
}
