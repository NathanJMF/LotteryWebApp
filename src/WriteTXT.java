import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;



public class WriteTXT {
    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

    /**
     * Takes the byte array holding the encrypted user numbers and stores it in a txt file.
     * The txt file has the name of the first 20 characters of the hashed password.
     * @param EncryptedUserNumbers byte array containing the encrypted user numbers.
     * @param password string holding the hashed password.
     */
    public static void CreateFile(byte[] EncryptedUserNumbers, String password) {
        String fileName;
        try{
            if (password.length() > 20){
                fileName = password.substring(0,20)+".txt";
            }
            else{
                fileName = password+".txt";
            }

            File myObj = new File(fileName);
            if (myObj.createNewFile()){
                WriteToFile(EncryptedUserNumbers, fileName);
            }
            else{
                WriteToFile(EncryptedUserNumbers, fileName);
            }
            }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Gets the name of the user file and writes the encrypted numbers to it.
     * @param EncryptedUserNumbers byte array containing the encrypted user numbers.
     * @param filename name of the user file to write to.
     */
    public static void WriteToFile(byte[] EncryptedUserNumbers, String filename){
        try {
            FileWriter myWriter = new FileWriter(filename);
            String encryptedUserNumbersHex = byteToHex(EncryptedUserNumbers);
            myWriter.write(encryptedUserNumbersHex);
            myWriter.close();

        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts given byte array into hex.
     * This is done to prevent the loss of information when converting a byte array into a string.
     * @param bytes the given byte array to be converted.
     * @return returns a hex string holding the encrypted numbers converted from a byte array.
     */
    public static String byteToHex(byte[] bytes){
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        String result = new String(hexChars, StandardCharsets.UTF_8);

        if (result.length() % 2 == 1){
            try {
                StringBuilder byt = new StringBuilder();
                for(byte b : bytes){
                    byt.append(b);
                }
                throw new Exception("Hex value cannot have odd number of digits. An error has occurred. Input string " + byt);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Converts the encrypted hex string to a byte array for decryption.
     * @param input the given hex string to be converted into a byte array.
     * @return returns a byte array holding the encrypted user numbers.
     * @throws Exception
     */
    public static byte[] hexToByteArray(String input) throws Exception {
        if (input.length() == 0){
            throw new Exception("The hex cannot have no digits");
        }
        if (input.length() % 2 == 1){
            throw new Exception("The hex cannot have an odd number of digits. Digits given " + input.length());
        }

        int len = input.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(input.charAt(i), 16) << 4)
                    + Character.digit(input.charAt(i+1), 16));
        }
        return data;
    }


}
