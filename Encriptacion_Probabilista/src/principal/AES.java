package principal;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *Class to encrypt and decode strings using a unique key.
 */
public class AES {

  private static SecretKeySpec secretKey;
  private static byte[] key;

  /**
   * This method set the secretKey in certain form, so after the key will be used to decode and encrypt strings.
   * 
   * @param myKey is the string that will set up to convert in a secretKey.
   */
  public static void setKey(String myKey) {
    MessageDigest sha = null;
    try {
      key = myKey.getBytes("UTF-8");
      sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16);
      secretKey = new SecretKeySpec(key, "AES");

    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * This method encrypt a String with a specific key.
   * 
   * @param strToEncrypt is the String that will be encrypted.
   * @param secret is the key that encode the string and this can decode just by this key.
   * @return the codified String of the strToEncryt.
   */
  public String encrypt(String strToEncrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
      
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
      
    } catch (Exception e) {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  /**
   * This method decode a String with a specific key.
   * 
   * @param strToDecrypt is the String that will be decode.
   * @param secret is the key that decode the string.
   * @return the decoded String of the strToDecrypt.
   */
  public String decrypt(String strToDecrypt, String secret) {
    try {
      setKey(secret);
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(Cipher.DECRYPT_MODE, secretKey);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    } catch (Exception e) {
      //System.out.println("Error while decrypting: " + e.toString());  //to avoid a lot of text in the console it is commented
    }
    return null;
  }
}
