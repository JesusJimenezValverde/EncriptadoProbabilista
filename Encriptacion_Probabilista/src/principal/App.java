package principal;

import util.Knowledge;

/**
 * Class of the application that work with the AES and use class Knowledge to learn from another tests.
 */
public class App {
  private Knowledge misProbabilidades;
  private AES codex;
  public int logro;
  public int isMenorMitad;

  public App() {
    misProbabilidades = new Knowledge();
    codex = new AES();
    logro = isMenorMitad = 0;
  }

  public Knowledge getMisProbabilidades() {
    return misProbabilidades;
  }

  public void setMisProbabilidades(Knowledge misProbabilidades) {
    this.misProbabilidades = misProbabilidades;
  }

  public AES getCodex() {
    return codex;
  }

  public void setCodex(AES codex) {
    this.codex = codex;
  }


  /**
   * Function to learn about what letters are necessary.
   * 
   * @param strToDecrypt is the encrypted String to decode.
   * @param key is the key that decode the strToDecrypt.
   * @param letras these are the letters to work with.
   * @return a String that can be null if the correct key wasn't found and another String if the correct key was found.
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
   * Function that prove the application with the present knowledge of the probabilities.
   * For better functionality you could use this function after you used aprendizaje().
   * 
   * @param strToDecrypt is the encrypted String to decode.
   * @param key these are the letters to work with.
   * @param letras these are the letters to work with.
   * @return a String that can be null if the correct key wasn't found and another String if the correct key was found.
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
   * This function generate a random double
   * 
   * @return a double, that is a probability between 0.0 and 1.0
   */
  public static double generarProbabilidad() {
    double num1 = (double) (Math.random() * 10) + 1;
    double num2 = (double) (Math.random() * 10) + num1;

    return num1 / num2;
  }


  public void imprimirProbabilidades(String[] letras) {
    System.out.println("Probabilidades Actuales");
    for(int index=0; index < getMisProbabilidades().getProbabilidades().length; index++) {
      System.out.println(letras[index]+": "+getMisProbabilidades().getProbabilidades()[index]);
    }
  }
}
