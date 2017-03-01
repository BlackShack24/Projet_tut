package Kmedoides;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Kmeans.Point;
import Kmeans.Cluster;

public class KMedoids {
	
	//Nombre de cluster
    private int NUM_CLUSTERS = 3;    
    //Nombre de points
    private int NUM_POINTS = 15;
    //Valeurs de test pour les coordonnes des points
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 10;
    
    // liste des points a traiter
    private List points;
    // liste des clusters
    private List clusters;

    public KMedoids(){
    	// on peut aussi faire un constructeur dans lequel on passe en paramètre
    	// le nombre de cluster que l'on veut
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();
    }
    
    public void init(){
    	// creation des points 
    	points = Point.createRandomPoints(MIN_COORDINATE,MAX_COORDINATE,NUM_POINTS);
    	
    	// on choisi aléatoirement les premiers médoides parmis le points
    	ArrayList<Point> medoides = new ArrayList();
    	
    	for(int i=0; i<NUM_CLUSTERS; i++){
        	int num = (int) (0 + (Math.random() * (NUM_POINTS - 0)));
        	
        	while(medoides.contains((Point) points.get(num))){
        		num = (int) (0 + (Math.random() * (NUM_POINTS - 0)));
        	}
        	System.out.println(num);
        	medoides.add((Point) points.get(num));
    	}
    	// affichage des medoides
    	for(int i=0; i<medoides.size();i++){
    		System.out.println(medoides.get(i).toString());
    	}
    	// on assigne les medoides a chaques cluster
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = medoides.get(i);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	// affichage des Cluster :
    	this.plotClusters();
    }
    
    
	private void plotClusters() {
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster c = (Cluster) clusters.get(i);
    		c.plotCluster();
    	}
    }
	
	// on calcule les clusters en boucle
	private void calculate(){
		
	}
}
