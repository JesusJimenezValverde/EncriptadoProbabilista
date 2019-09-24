package util;

/**
 * Class to have the knowledge of the algorithm
 */
public class Knowledge {
  private double[] probabilidades = new double[26]; // At the moment the length is 26, because it's
                                                    // not the 'b'.

  /**
   * Constructor to create instances of Conocimiento
   */
  public Knowledge() {
    for (int index = 0; index < probabilidades.length; index++) {
      probabilidades[index] = 1.0;
    }
  }

  public double[] getProbabilidades() {
    return probabilidades;
  }

  public void setProbabilidades(double[] probabilidades) {
    this.probabilidades = probabilidades;
  }


  /**
   * Class to change the probabilities of the class, but just the probabilities that are higher than
   * 0.5
   * 
   * @param probabilidaesNuevas new probabilities
   */
  public void aprender(double[] probabilidaesNuevas) {
    for (int index = 0; index < probabilidaesNuevas.length; index++) {
      if (probabilidaesNuevas[index] < 0.5) {
        probabilidades[index] = probabilidaesNuevas[index];
      }
    }
  }
}
