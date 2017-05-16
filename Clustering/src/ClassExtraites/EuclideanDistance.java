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
		int compteur = 0;  // Compteur de similarité entre Instance
		// Declaration des iterators des instances
		Set cles = x.keySet();
		Iterator it = cles.iterator();
		Set cles2 = y.keySet();
		Iterator it2 = cles2.iterator();
		double meanX, meanY;
		int sumX=0, sumY=0;
		
		// Si l'instance x ou y a moins de 5 attributs alors on retourne une valeur aberrante 
		if(x.noAttributes()<5 || y.noAttributes()<5) return 10000.0;

		// Récuperation des valeurs des instances pour en faire la moyenne
		while(it.hasNext()) {
			Object ob = it.next();
			if(y.containsKey(ob)) compteur++;
			sumX += x.get(ob);
		}
		meanX = sumX / x.noAttributes();
		while(it2.hasNext()) sumY += y.get(it2.next());
		meanY = sumY / y.noAttributes();
		
		// Calcul de la distance euclidienne
		double sum = (meanY - meanX) * (meanY - meanX);
		
		// Si il y a moins de 5 similarités entre les instances on renvoi une valeur aberrante
		if(compteur < 5) return 10000.0;
		return Math.sqrt(sum);
	}
}
