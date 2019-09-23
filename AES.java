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
    
    public static double generarProb() {
    	return Math.random();	 
    }
    
    public static void copiarArrays(double[]origen, double[]destino) {
    	for(int elemento = 0; elemento<origen.length; elemento++) {
    		destino[elemento] = origen[elemento];
    	}
    }
    
    public static String pruebas2(String strToDecrypt, String pKey, String[] letras, int cantidadPruebas) {
    	
    	double[] pActual = new double[letras.length];
    	
    	for(int pLetra = 0; pLetra<letras.length; pLetra++) {
    			pActual[pLetra] = generarProb();
    		}
    	
    	double[] pAnterior = new double[letras.length];
    	copiarArrays(pActual,pAnterior);
    	
    	int PruebaAct = 0;
    	while(PruebaAct<cantidadPruebas) {
    		if(pruebas(strToDecrypt, pKey, letras, pActual) == true) {
    			System.out.println("Logradooooooooooop");
    			//Cuando da positivo se generan denuevo los porcentajes, excepto los que sean menores a 0.5
    			copiarArrays(pActual, pAnterior);
    			for(int nuevosP = 0; nuevosP<pActual.length; nuevosP++) {
    				if(pActual[nuevosP] > 0.5) {
    					pActual[nuevosP] = generarProb();
    				}
    			}
    		}else{
    			//Cuando da negativo se toma el estado anterior y se generan los porcentajes denuevo
    			if(PruebaAct == 0) {//Excepto en la primera iteracion... en esta se generan los dos arreglos denuevo
    				System.out.println("Entro en el que hace todos denuevo");
    		    	for(int pLetra = 0; pLetra<letras.length; pLetra++) {
    	    			pActual[pLetra] = generarProb();
    	    		}    				
    			}else {
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
		    				System.out.println("-----------------------------------------------Anterior-------------------------------------------");
		    	        	for(int letraM = 0; letraM < pActual.length; letraM++) {
		    	        		if(pAnterior[letraM]>0.5) {
		    	        			System.out.println("Buena -- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pAnterior[letraM]);
		    	        		}else {
		    	        			System.out.println("-- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pAnterior[letraM]);
		    	        		}
		    	    		}
		    				System.out.println("-----------------------------------------------Actual-------------------------------------------");
		    	        	for(int letraM = 0; letraM < pActual.length; letraM++) {
		    	        		if(pAnterior[letraM]>0.5) {
		    	        			System.out.println("Buena -- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pActual[letraM]);
		    	        		}else {
		    	        			System.out.println("-- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pActual[letraM]);
		    	        		}
		    	    		}
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
    	        	copiarArrays(pAnterior, pActual);
        			for(int nuevosP = 0; nuevosP<pActual.length; nuevosP++) {
        				if(pActual[nuevosP] > 0.5) {
        					System.out.println("Estoy generando un nuevo porcentaje para "+"'"+letras[nuevosP]+"'");
        					pActual[nuevosP] = generarProb();
        				}
        			}	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
    				System.out.println("-----------------------------------------------Actuald-------------------------------------------");
    	        	for(int letraM = 0; letraM < pActual.length; letraM++) {
    	        		if(pActual[letraM]>0.5) {
    	        			System.out.println("Buena -- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pActual[letraM]);
    	        		}else {
    	        			System.out.println("-- Letra -- "+ letras[letraM]+" -- porcentaje -- "+pActual[letraM]);
    	        		}
    	        	}
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
//-------------------------------------------------------------------------------------------------------------------------------------------------------------//	
    			}
    		}
    		PruebaAct++;
    	}
    	int maxValue=0;

    	for(int letraM = 1; letraM < pActual.length; letraM++) {
    		if(pAnterior[letraM]>pAnterior[maxValue]) {
    			System.out.println(letras[maxValue]);
    			maxValue = letraM;
    		}
    	}
    	return letras[maxValue];
    }
    
    
    public static boolean pruebas(String strToDecrypt, String key, String[] letras, double[] porcentajes) {

    	for(int letra = 0; letra < letras.length ; letra++) {
    		if(porcentajes[letra] > 0.5) {	
	    		for(int numero = 0; numero < 10; numero++) {
	    			String actual = key;
	    			actual = key.replaceFirst("_", letras[letra]);
	    			//System.out.println("Llave actual: "+actual);
	    			actual = actual.replace("_", Integer.toString(numero));
	    			//System.out.println("Llave actual: "+actual);
	    			String nuevo = decrypt(strToDecrypt, actual);
	    			if(nuevo != null) {
	    				//System.out.println("Logrado");
	    				return true; //Se encuentra la respuesta
	    			}
	    		}
    		}
    	}
    	return false; //No se encontro la respuesta
    }

//Tomado de https://howtodoinjava.com/security/java-aes-encryption-example/
