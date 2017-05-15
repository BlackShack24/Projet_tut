package ClassExtraites;

import java.util.Iterator;
import java.util.Set;

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
		int compteur = 0;
		Set cles = x.keySet();
		Iterator it = cles.iterator();

		if(x.noAttributes() >= 5) {
			while(it.hasNext()) {
				if(y.containsKey(it)) {
					compteur++;
					sum += Math.pow(Math.abs(x.get(it) - y.get(it)), this.power);
				}
			}
		}
		else return 10000.0;
		if(compteur < 5) return 10000.0;
		System.out.println("Compteur : "+compteur);
		return Math.pow(sum, 1.0D / this.power);
	}
}
