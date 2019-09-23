package Principal;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class AES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
    public int logro=0;
    public int isMenorMitad = 0;
    private double[] probabilidades = {1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
 
    
    public double[] getProbabilidates() {
    	return probabilidades;
    }
    
    public void setProbabilidates(double[] pProbabilidades) {
    	probabilidades = pProbabilidades;
    }
    
    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
            
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public String decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    /**
     * Function to prove the functionality
     * 
     * @param strToDecrypt is the encrypted String to decode
     * @param key is the key that decode the strToDecrypt
     * @param letras these are the 
     * @return
     */
    public String Aprendisaje(String strToDecrypt, String key, String[] letras) {
    	double miProb;
    	int it = 0;
    	double[] probabilidadesActuales = new double[26];
    	
    	for(int letra = 0; letra < letras.length ; letra++) {
    		it++;
    		
    		miProb = generarProbabilidad();
    		probabilidadesActuales[letra] = miProb;
    		
    		if(probabilidadesActuales[letra] < 0.5)
    			continue;		
    		
    		for(int numero = 0; numero < 10; numero++) {
    			it++;
    			String actual = key;
    			actual = key.replaceFirst("_", letras[letra]);
    			//System.out.println("Llave actual: "+actual);
    			actual = actual.replace("_", Integer.toString(numero));
    			//System.out.println("Llave actual: "+actual);
    			String nuevo = decrypt(strToDecrypt, actual);
    			
    			if(nuevo != null) {
    				aprender(probabilidadesActuales);
    				System.out.println("Iteraciones: " + it);
    				if(it <= 130)
    					isMenorMitad++;
    				logro++;
    				return nuevo;
    			}
    		}
    	}
    	return null;
    }
    
    
    public String prueba(String strToDecrypt, String key, String[] letras, double[] prop) {
    	int it = 0;
    	setProbabilidates(prop);
    	
    	for(int letra = 0; letra < letras.length ; letra++) {
    		it++;
    		
    		if(probabilidades[letra] < 0.5)
    			continue;		
    		
    		for(int numero = 0; numero < 10; numero++) {
    			it++;
    			String actual = key;
    			actual = key.replaceFirst("_", letras[letra]);
    			//System.out.println("Llave actual: "+actual);
    			actual = actual.replace("_", Integer.toString(numero));
    			//System.out.println("Llave actual: "+actual);
    			String nuevo = decrypt(strToDecrypt, actual);
    			
    			if(nuevo != null) {
    				System.out.println("Iteraciones: " + it);
    				if(it <= 130)
    					isMenorMitad++;
    				logro++;
    				return nuevo;
    			}
    		}
    	}
    	return null;
    }
    
    /**
     * This function generate a random double
     * 
     * @return a double, that is a probability between 0.0 and 1.0
     */
    public static double  generarProbabilidad() {
		double num1 = (double) (Math.random() * 10) + 1;
		double num2 = (double) (Math.random() * 10) + num1;
		
		return num1/num2;
	}
    
    /**
     * 
     * 
     * @param probabilidaesNuevas
     */
    public void aprender(double[] probabilidaesNuevas) {
    	for(int index=0; index<26; index++) {
    		if(probabilidaesNuevas[index] < 0.5) {
    			//System.out.println("Probabilidad pos:"+index+" = "+probabilidaesNuevas[index]);
    			probabilidades[index] = probabilidaesNuevas[index];
    		}
    	}
    }
}
