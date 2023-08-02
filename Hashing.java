/**
 * This class extends from the SHA512 class and it returns a hashed string in the cryptHash method.
 * @author Loc Nguyen
 */
public class Hashing extends SHA512 {
    /**
     * This method takes in a string and return a hashed hex string from the parent class.
     * @param s The string to be hashed.
     * @return The subtring of the hashed string.
     */
    public static String cryptHash(String s) {
        String digest = hashSHA512(s);
        return digest.substring(0,128);
    }
}
