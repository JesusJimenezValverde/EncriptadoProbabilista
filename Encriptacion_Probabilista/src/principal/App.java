package principal;

import util.Knowledge;

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
