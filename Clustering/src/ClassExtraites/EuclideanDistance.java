package ClassExtraites;

import java.util.Iterator;
import java.util.Set;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.NormDistance;

public class EuclideanDistance
extends NormDistance
{
	private static final long serialVersionUID = 6672471509639068507L;

	public double calculateDistance(Instance x, Instance y) {
		int compteur = 0;  
		Set cles = x.keySet();
		Iterator it = cles.iterator();

		if (x.noAttributes() != x.noAttributes()) {
			throw new RuntimeException("Both instances should contain the same number of values.");
		}
		double sum = 0.0D;

		if(x.noAttributes()<5) {
			while(it.hasNext()) {
				if ((!Double.isNaN(y.get(it))) && (!Double.isNaN(x.get(it)))) {
					if(y.containsKey(it)) {
						compteur++;
						sum += (y.get(it) - x.get(it)) * (y.get(it) - x.get(it));
					}
				}
			}
		} else return 10000.0;
		if(compteur < 5) return 10000.0;

		return Math.sqrt(sum);
	}
}
