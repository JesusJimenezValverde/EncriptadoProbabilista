package util;

public class Knowledge {
  private double[] probabilidades = new double[26]; // At the moment the length is 26, because it's
                                                    // not the 'b'.

  
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

  public void aprender(double[] probabilidaesNuevas) {
    for (int index = 0; index < probabilidaesNuevas.length; index++) {
      if (probabilidaesNuevas[index] < 0.5) {
        probabilidades[index] = probabilidaesNuevas[index];
      }
    }
  }
}
