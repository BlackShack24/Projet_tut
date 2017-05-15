package ClassExtraites;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.AbstractDistance;

public class NormDistance
extends AbstractDistance
{
	private static final long serialVersionUID = 3431231902618783080L;
	private double power;

	public NormDistance()
	{
		this(2.0D);
	}

	public NormDistance(double power)
	{
		this.power = power;
	}

	public double measure(Instance x, Instance y)
	{
		assert (x.noAttributes() == y.noAttributes());
		double sum = 0.0D;
		System.out.println("Test");
		if(x.noAttributes() >= 5) {
			for (int i = 0; i < x.noAttributes(); i++) {
				sum += Math.pow(Math.abs(y.value(i) - x.value(i)), this.power);
				System.out.println("NormDistance if l33");
			}
		}else{
			System.out.println("NormDistance ligne 35");
		}
		return Math.pow(sum, 1.0D / this.power);
	}

}
