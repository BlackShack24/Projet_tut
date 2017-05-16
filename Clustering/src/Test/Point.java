package Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    	return 0;
    }
    
    //Creates random point
    protected static Point createRandomPoint(int min, int max) {
    	Random r = new Random();
    	int size = 0 + (940-0) * r.nextInt();
    	// on remplis une HashMap avec des valeurs aléatoires
    	HashMap<Integer, Double> m = new HashMap<>();
    	for(int i=0; i< size; i++){
    		int u = 0 + (940 - 0) * r.nextInt();
    		double note = min + (max - min) * r.nextDouble();
    		m.put(u, note);
    	}
    	return new Point(m);
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
