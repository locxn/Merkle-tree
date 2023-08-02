import java.security.*;
import java.nio.charset.*;
/**
 * This class is used to hash two strings together to create a unique hash code.
 * @author Loc Nguyen
 */
//This code is already made for you, it is extended by Hashing to generate the SHA-512 hash of a String. No explicit call to any method here is required.
public class SHA512 {
    /**
     * This method takes in a string message and convert it into a hex value string.
     * @param message The digest to be hashed.
     * @return The hashed string.
     */
    protected static String hashSHA512(String message) {
        String sha512ValueHexa = "";
        try {
            MessageDigest digest512 = MessageDigest.getInstance("SHA-512");
            sha512ValueHexa = byteToHex(digest512.digest(message.getBytes(StandardCharsets.UTF_8)));
        }
        catch(NoSuchAlgorithmException exp) {
            exp.getMessage();
        }
        return sha512ValueHexa;
    }

    /**
     * This method takes in the converted bytes to generated a new hashed hex string.
     * @param digest list of bytes.
     * @return Hashed hex string.
     */
    public static String byteToHex(byte[] digest) {
        StringBuilder vector = new StringBuilder();
        for (byte c : digest) {
            vector.append(String.format("%02X", c));
        }
        String output = vector.toString();
        return output;
    }
}
