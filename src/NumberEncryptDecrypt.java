import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class NumberEncryptDecrypt {
    /**
     * Takes the user numbers and encrypts them.
     * @param UserNumbers The given user numbers to encrypt and store.
     * @param request The given request from account.jsp
     * @return returns a byte array containing the encrypted user numbers.
     */
    public static byte[] numEncrypt(String UserNumbers, HttpServletRequest request){
        SecureRandom securerandom = new SecureRandom();
        byte[] encryptedUserNumbers = null;
        try{
            //Create key
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, securerandom);
            SecretKey key = keyGenerator.generateKey();
            HttpSession session = request.getSession();
            session.setAttribute("key", key);
            //Initialise a vector with an arbitrary value
            byte[] initializationVector = new byte[16];
            securerandom.nextBytes(initializationVector);
            session.setAttribute("vector", initializationVector);
            //Takes plaintext and converts it into cipher text using the key
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);
            cipher.init(Cipher.ENCRYPT_MODE,key,ivParameterSpec);
            encryptedUserNumbers = cipher.doFinal(UserNumbers.getBytes(StandardCharsets.UTF_8));
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encryptedUserNumbers;
    }

    /**
     * Takes the user numbers and decrypts them to return a plain text string holding them.
     * @param encryptedUserNumbers The encrypted byte array holding the user's numbers.
     * @param request request given by account.jsp
     * @return returns the string holding the plain text user numbers after decryption.
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String numDecrypt(byte[] encryptedUserNumbers, HttpServletRequest request) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        HttpSession session = request.getSession();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        IvParameterSpec ivParameterSpec = new IvParameterSpec((byte[]) session.getAttribute("vector"));
        cipher.init(Cipher.DECRYPT_MODE, (SecretKey) session.getAttribute("key"), ivParameterSpec);
        byte[] result = cipher.doFinal(encryptedUserNumbers);
        return new String(result, StandardCharsets.UTF_8);
    }
}
