package principal;

import java.io.IOException;

/**
 * Class to run the application
 */
public class Principal {

  public static void main(String[] args) throws IOException {
    App app = new App();

    String[] letrasconB = new String[] {"a", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", 
        "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    String toDecrypt = "xZwM7BWIpSjYyGFr9rhpEa+cYVtACW7yQKmyN6OYSCv0ZEg9jWbc6lKzzCxRSSIvOvlimQZBMZOYnOwiA9yy3YU8zk4abFSItoW6Wj0ufQ0=";
    String secretKey = "29dh120_dk1_3";

    for (int i = 0; i < 100; i++) {
      String resultado = app.Aprendizaje(toDecrypt, secretKey, letrasconB);
      System.out.println(resultado);
      app.imprimirProbabilidades(letrasconB);
    }

    System.out.println();
    System.out.println();
    System.out.println("Cantidad de logro: " + app.logro);
    System.out.println("Cant de menorMitad iteraciones: " + app.isMenorMitad);
    
    app.logro = app.isMenorMitad = 0;

    for (int i = 0; i < 100; i++) {
      String resultado = app.prueba(toDecrypt, secretKey, letrasconB);
      System.out.println(resultado);
    }

    System.out.println("Cantidad de logro: " + app.logro);
    System.out.println("Cant de menorMitad iteraciones: " + app.isMenorMitad);

  }

}
