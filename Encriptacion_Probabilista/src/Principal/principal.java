package Principal;

import java.io.IOException;

public class principal {

	public static void main(String[] args) throws IOException {	
		String[] letrasconB = new String[]{"a","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z"};
		
		String toDecrypt = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
		String secretKey = "29dh120_dk1_3";
		
		AES aes = new AES();
		
		for(int i=0; i<100; i++) {
			String resultado = aes.Aprendisaje(toDecrypt, secretKey, letrasconB);
			System.out.println(resultado);
		}
		
		System.out.println();
		System.out.println();
		System.out.println("Cantidad de logro: "+aes.logro);
		System.out.println("Cant de menorMitad iteraciones: "+aes.isMenorMitad);
		
		for(int i=0; i<letrasconB.length; i++) {
			System.out.println(letrasconB[i]+": " + aes.getProbabilidates()[i]);
		}
		
		System.out.println();
		System.out.println();
		AES aes2 = new AES();
		for(int i=0; i<100; i++) {
			String resultado = aes2.prueba(toDecrypt, secretKey, letrasconB, aes.getProbabilidates());
			System.out.println(resultado);
		}

		System.out.println("Cantidad de logro: "+aes2.logro);
		System.out.println("Cant de menorMitad iteraciones: "+aes2.isMenorMitad);
	}

}
