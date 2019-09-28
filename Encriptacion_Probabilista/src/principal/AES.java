package principal;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/** 
 * This class is the one that contains the methods to encrypt and decrypt information.<br>
 * This one is in charge of generate the secret keys AES uses in the process.
 */
public class AES {

  private static SecretKeySpec secretKey;
  private static byte[] key;

    /**
   * This method is the one used by the algorithm of decryption <br>
   * and encryption to make the string given by the user into a <br>
   * byte key.
   * @param myKey - String
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
   * This function is the one in charge of encrypting the information<br>
   * given by the user. It uses "setKey" to make the string given, <br> 
   * into a real key.
   * @param strToEncrypt - String
   * @param secret - String
   * @return Null if secret was incorrect. <br>
   * The encypted data if the function ran in the correct way. 
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
   * This function is the one in charge of decrypting the information<br>
   * given by the user. It uses "setKey" to make the string given, <br> 
   * into a real key.
   * @param strToDecrypt - String
   * @param secret - String
   * @return Null if secret was incorrect. <br>
   * The decrypted data if the function ran in the correct way. 
   * 
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
