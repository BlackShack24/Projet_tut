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
	private int idFilm;

	public Point(HashMap<Integer, Double> m)
	{
		this.setCoord(m);
	}

	public Point(HashMap<Integer, Double> m, int numfilm)
	{
		this.setCoord(m);
		this.idFilm = numfilm;
	}

	public Point(Point p){
		this.coord = p.coord;
		this.idFilm = p.idFilm;
		this.cluster_number = p.cluster_number;
	}

	public int getIdFilm() {
		return idFilm;
	}

	public void setIdFilm(int idFilm) {
		this.idFilm = idFilm;
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
		double retour = 0;
		// lorsque l'on compare deux film on va uniquement comparer sur les distances en commun
		ArrayList<Double> coordcom1 = new ArrayList<Double>();
		ArrayList<Double> coordcom2 = new ArrayList<Double>();
		Set cles = p.getCoord().keySet();
		Iterator it = cles.iterator();
		Set cles2 = centroid.getCoord().keySet();
		Iterator it2 = cles2.iterator();
		
		// on parcours les coord du Point pour r�cuperer celles en commun avec le centroid
		while(it.hasNext()){
			Object ob = it.next();
			if(centroid.getCoord().containsKey(ob)){
				// si l item est en commum on ajoute dans les list de coordonnes communes
				coordcom1.add(centroid.getCoord().get(ob));
				coordcom2.add(p.getCoord().get(ob));
			}
		}
		// ensuite on fait la somme du carr� des diff�rence
		for(int i=0; i<coordcom1.size(); i++){
			retour += Math.pow(coordcom1.get(i) - coordcom2.get(i),2 );
		}
		
		// et on retourne la racine de cette somme
		return Math.sqrt(retour);
	}


	//Creates random point
	protected static Point createRandomPoint(int min, int max) {
		int size = 940;
		// on remplis une HashMap avec des valeurs al�atoires
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
	
	protected static ArrayList<Point> createRandomCentroid(int nombreCentroid, List points){
		// les futurs centroids
		ArrayList<Point> centroids = new ArrayList();
		// pour chaque cnetroids
		for(int i=0; i<nombreCentroid;i++){
			// on genere un nombre aleratoire parmis la taille de la liste de nos points
			double rand = points.size() * Math.random();
			// on recupere le point correspondant
			Point p = (Point)points.get((int)rand);
			// si il n a pas deja ete choisis comme centroids et si il a au moins 100 coordonnes on le prend sinon on re genere un nombre aleatoire
			while(centroids.contains(p)|| p.getCoord().size()<100){ //
				rand = points.size() * Math.random();
				p = (Point)points.get((int)rand);
			}
			// on ajoute le point choisis 
			centroids.add((Point) points.get((int)rand));
		}

		return centroids;
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
