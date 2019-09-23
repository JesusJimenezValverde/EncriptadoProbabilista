package Principal;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class AES {
 
    private static SecretKeySpec secretKey;
    private static byte[] key;
 
    
    public static void setKey(String myKey){
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
 
    public static String encrypt(String strToEncrypt, String secret)
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
 
    public static String decrypt(String strToDecrypt, String secret)
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
            //System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
   // ---------------------------------------- new ---------------------------------------- \\ 
    
    public static double generarProb() { // Genera un random 
    	return Math.random();	 
    }
    
    public static void copiarArrays(double[]origen, double[]destino) { // Copia los valores del origen en el destino
    	for(int elemento = 0; elemento<origen.length; elemento++) {
    		destino[elemento] = origen[elemento];
    	}
    }
    
    
    public static void generarPorcentajes(double[]array) { // Genera porcentajes solo para los elementos > 0.5
    	for(int elemento = 0; elemento < array.length; elemento++) {
    		if(array[elemento]>(double)0.5) {
    			array[elemento] = generarProb();
    		}
    	}
    }
    
    public static void generarInicio(double[]array) { // Genera porcentajes para todos los elementos del array
    	for(int elemento = 0; elemento<array.length; elemento++) {
			array[elemento] = generarProb();
    	}
    }
    
    public static int verificarPorcentajes(double[]array) { // Saca el campo con mayor porcentaje
    	int maxValue = 0;
    	for(int letraM = 1; letraM < array.length; letraM++) {
    		if(array[letraM]>array[maxValue]) {
    			maxValue = letraM;
    		}
    	}
    	return maxValue;
    }
    
    public static boolean pruebas(String strToDecrypt, String key, String[] letras, double[] porcentajes) {

    	for(int letra = 0; letra < letras.length ; letra++) {
    		if(porcentajes[letra] > 0.5) {	
	    		for(int numero = 0; numero < 10; numero++) {
	    			String actual = key;
	    			actual = key.replaceFirst("_", letras[letra]);
	    			actual = actual.replace("_", Integer.toString(numero));
	    			String nuevo = decrypt(strToDecrypt, actual);
	    			if(nuevo != null) {
	    				return true; //Se encuentra la respuesta
	    			}
	    		}
    		}
    	}
    	return false; //No se encontro la respuesta
    }
    
    public static String pruebas2(String strToDecrypt, String pKey, String[] letras, int cantidadPruebas) {
    	
    	double[] pActual = new double[letras.length];
    	double[] pAnterior = new double[letras.length];
    	generarInicio(pActual);
    	copiarArrays(pActual,pAnterior);
    	
    	int PruebaAct = 0;
    	while(PruebaAct<cantidadPruebas) {
    		
    		if(pruebas(strToDecrypt, pKey, letras, pActual) == true) {
    			System.out.println("Logradooooooooooop");
    			copiarArrays(pActual, pAnterior);
    			generarPorcentajes(pActual);
    			
    		}else{
    			//Cuando da negativo se toma el estado anterior y se generan los porcentajes denuevo
    			if(PruebaAct == 0) {//Excepto en la primera iteracion... en esta se generan los dos arreglos denuevo
    				System.out.println("Entro en el que hace todos denuevo");
    				generarInicio(pActual);
    			}else {
    				copiarArrays(pAnterior, pActual);
    				generarPorcentajes(pActual);
    			}
    		}
    		PruebaAct++;
    	}

    	int maxValue = verificarPorcentajes(pAnterior);
    	return letras[maxValue];
    }
}

//Tomado de https://howtodoinjava.com/security/java-aes-encryption-example/
