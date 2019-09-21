package Principal;

public class principal {

	public static void main(String[] args) {
		String[] letrasconB = new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z",};
		 //String[] letras = new String[]{"a","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z",};
		// TODO Auto-generated method stub
		String toDecrypt = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
		String secretKey = "29dh120_dk1_3";
		
		String resultado = AES.pruebas(toDecrypt, secretKey, letrasconB);
		System.out.println(resultado);
	}

}
