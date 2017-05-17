package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Point {

	private HashMap<Integer, Double> coord;
	private int cluster_number = 0;

	public Point(HashMap<Integer, Double> m)
	{
		this.setCoord(m);
	}


	public HashMap<Integer, Double> getCoord() {
		return coord;
	}


	public void setCoord(HashMap<Integer, Double> coord) {
		this.coord = coord;
	}


	public void setCluster(int n) {
		this.cluster_number = n;
	}

	public int getCluster() {
		return this.cluster_number;
	}

	//Calculates the distance between two points.
	protected static double distance(Point p, Point centroid) {
		// on doit calculer la distance entre les deux points
		// on fait la moyenne des coordonnes pour chaques point

		// on calcule la distance uniquement sur les truc en commun
		// on garde les centroids set comme ils sont
		// on remplis les centroids sur les 940 dimention avec des notes aleatoires

		int compteur = 0;
		Set cles = centroid.getCoord().keySet();
		Iterator it = cles.iterator();
		Set cles2 = p.getCoord().keySet();
		Iterator it2 = cles2.iterator();

		double meanX, meanY;
		int sumX=0, sumY=0;
		// Si l'instance x ou y a moins de 5 attributs alors on retourne une valeur aberrante 
		if(cles.size()<5 || cles2.size()<5) return 10000.0;
		// Récuperation des valeurs des instances pour en faire la moyenne
		while(it.hasNext()) {
			Object ob = it.next();
			if(p.getCoord().containsKey(ob)) compteur++;
			sumX += centroid.getCoord().get(ob);
		}
		meanX = sumX / cles.size();
		while(it2.hasNext()) sumY += p.getCoord().get(it2.next());
		meanY = sumY / cles2.size();

		// Calcul de la distance euclidienne
		double sum = (meanY - meanX) * (meanY - meanX);

		// Si il y a moins de 5 similarités entre les instances on renvoi une valeur aberrante
		if(compteur < 5) return 10000.0;
		return Math.sqrt(sum);
	}


	//Creates random point
	protected static Point createRandomPoint(int min, int max) {
		int size = 940;
		// on remplis une HashMap avec des valeurs aléatoires
		// on choisis de donner une valeur pour chaque utilisateur au centroid
		// cela permet  de faciliter les comparaisons
		HashMap<Integer, Double> m = new HashMap<>();
		for(int i=0; i< size; i++){
			double note = min + (max - min) * Math.random();
			m.put(i+1, note);

		}
		Point retour = new Point(m);
		return retour;
	}

	protected static List createRandomPoints(int min, int max, int number) {
		List points = new ArrayList(number);
		for(int i = 0; i < number; i++) {
			points.add(createRandomPoint(min,max));
		}
		return points;
	}

	public String toString() {
		return this.coord.toString();
	}
}
