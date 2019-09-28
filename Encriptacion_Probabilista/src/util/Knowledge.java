package util;

/**
 * Knowledge is the result of the need to control how the <br>
 * percentajes behave and how the algorithm learns from <br>
 * previous runs.
 * It mantains an array of doubles in which every one of them <br>
 * represents a letter of the alphabet. Each of the percentajes<br>
 * fluctuates between each run of the algorithm in order to <br>
 * impove the accuracy of itself.
 * 
 * @author Anthony Artavia / Esteban Jiménez
 * @version 1.0
 *
 */
public class Knowledge {
  private double[] probabilidades = new double[26]; // At the moment the length is 26, because it's
                                                    // not the 'b'.

    /**
   * Makes the same probability for every letter represented in the array.
   */
  public Knowledge() {
    for (int index = 0; index < probabilidades.length; index++) {
      probabilidades[index] = 1.0;
    }
  }

    /**
   * This is used by other classes/methods to get the actual array of probabilities by letter.
   * @return probabilidades - Double[] Array<br>
   * 	Array that represents the probability per letter.
   */
  public double[] getProbabilidades() {
    return probabilidades;
  }

    /**
   * Set the probabilities of the actual array based on another of the same type. <br>
   * This makes possible the learning process. By using an old probability in the <br>
   * actual run of the algorithm.
   * @param probabilidades - Double[] Array
   */
  public void setProbabilidades(double[] probabilidades) {
    this.probabilidades = probabilidades;
  }

    /**
   * This method is used to introduce new probabilities in the spaces where they<br>
   * are needed. It uses the actual array and the one that is introduced as a <br>
   * parameter.
   * @param probabilidaesNuevas - Double[] Array
   */
  public void aprender(double[] probabilidaesNuevas) {
    for (int index = 0; index < probabilidaesNuevas.length; index++) {
      if (probabilidaesNuevas[index] < 0.5) {
        probabilidades[index] = probabilidaesNuevas[index];
      }
    }
  }
}
