package ClassExtraites;

import net.sf.javaml.core.Instance;

public class EuclideanDistance
  extends NormDistance
{
  private static final long serialVersionUID = 6672471509639068507L;
  
  public double calculateDistance(Instance x, Instance y)
  {
    if (x.noAttributes() != x.noAttributes()) {
      throw new RuntimeException("Both instances should contain the same number of values.");
    }
    double sum = 0.0D;
    for (int i = 0; i < x.noAttributes(); i++) {
      if ((!Double.isNaN(y.value(i))) && (!Double.isNaN(x.value(i)))) {
        sum += (y.value(i) - x.value(i)) * (y.value(i) - x.value(i));
      }
    }
    return Math.sqrt(sum);
  }
}
