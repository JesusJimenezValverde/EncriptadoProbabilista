package principal;

import util.Knowledge;

/**
 * This class contains the main application that solves the problem <br>
 * of having a secret key for AES encryption with a letter and a <br>
 * number missing.
 * @author Anthony Artavia / Esteban Jiménez
 *
 */
public class App {
  private Knowledge misProbabilidades;
  private AES codex;
  public int logro;
  public int isMenorMitad;

    /**
   * This is the constructor of the class. 
   * It contains an instance of the class knowledge and a object AES<br>
   * that corresponds to the secret key of the 
   */
  public App() {
    misProbabilidades = new Knowledge();
    codex = new AES();
    logro = isMenorMitad = 0;
  }

    /**
   * This method is used to know the actual state of the probabilities per letter.
   * @return Knowledge
   */
  public Knowledge getMisProbabilidades() {
    return misProbabilidades;
  }

    /**
   * This method is used to set a bunch of new probabilities to the actual object.
   * @param misProbabilidades - knowledge
   */
  public void setMisProbabilidades(Knowledge misProbabilidades) {
    this.misProbabilidades = misProbabilidades;
  }

    /**
   * It is used to get the codex that contains the secret key.
   * @return codex - AES
   */
  public AES getCodex() {
    return codex;
  }

    /**
   * Once generated the object AES with the secret key, it is passed by parameter<br>
   * to this class where is set as codex.
   * @param codex - AES
   */
  public void setCodex(AES codex) {
    this.codex = codex;
  }

    /**
   * This is the main method when we speak about the learning process. It takes the actual<br>
   * set of odds and make new ones based on the probability of a letter to become more/less<br>
   * important on the process. 
   * @param strToDecrypt - String
   * @param key - String
   * @param letras - String[] Array
   * @return String corresponding to the result of the operation.
   */
  public String Aprendizaje(String strToDecrypt, String key, String[] letras) {
    double miProb;
    int it = 0;
    double[] probabilidadesActuales = new double[26];

    for (int letra = 0; letra < letras.length; letra++) {
      it++;

      miProb = generarProbabilidad();
      probabilidadesActuales[letra] = miProb;

      if (probabilidadesActuales[letra] < 0.5)
        continue;

      for (int numero = 0; numero < 10; numero++) {
        it++;
        String actual = key;
        actual = key.replaceFirst("_", letras[letra]);
        // System.out.println("Llave actual: "+actual);
        actual = actual.replace("_", Integer.toString(numero));
        // System.out.println("Llave actual: "+actual);
        String nuevo = getCodex().decrypt(strToDecrypt, actual);

        if (nuevo != null) {
          getMisProbabilidades().aprender(probabilidadesActuales);
          System.out.println("Iteraciones: " + it);
          if (it <= 130)
            isMenorMitad++;
          logro++;
          return nuevo;
        }
      }
    }
    return null;
  }

    /**
   * This algorithm/method is used once the learning process is concluded<br>
   * It gets the actual set of probabilities and check the ones that are <br>
   * better. It makes a test with this better ones and determine if the <br>
   * learning process was correct or not, based on the quantity of iterations<br>
   * that took it to get to the answer.
   * @param strToDecrypt - String
   * @param key - String
   * @param letras - String[] Array
   * @return String that notifies if the process went all god or something happened.
   */
  public String prueba(String strToDecrypt, String key, String[] letras) {
    int it = 0;

    for (int letra = 0; letra < letras.length; letra++) {
      it++;

      if (misProbabilidades.getProbabilidades()[letra] < 0.5)
        continue;

      for (int numero = 0; numero < 10; numero++) {
        it++;
        String actual = key;
        actual = key.replaceFirst("_", letras[letra]);
        actual = actual.replace("_", Integer.toString(numero));
        String nuevo = getCodex().decrypt(strToDecrypt, actual);

        if (nuevo != null) {
          System.out.println("Iteraciones: " + it);   //Imprime las iteraciones que le llevo.
          if (it <= 130)
            isMenorMitad++;
          logro++;
          return nuevo;
        }
      }
    }
    return null;
  }

    /**
   * This method is used to give probabilities to the main algorithm.
   * @return Double
   */
  public static double generarProbabilidad() {
    double num1 = (double) (Math.random() * 10) + 1;
    double num2 = (double) (Math.random() * 10) + num1;

    return num1 / num2;
  }


  /**
   * It prints the actual probability for each letter in the alphabet.
   * @param letras - String[] Array
   */
  public void imprimirProbabilidades(String[] letras) {
    System.out.println("Probabilidades Actuales");
    for(int index=0; index < getMisProbabilidades().getProbabilidades().length; index++) {
      System.out.println(letras[index]+": "+getMisProbabilidades().getProbabilidades()[index]);
    }
  }
}
