package dataOnFocusKmeans;
/* 
 * KMeans.java ; Cluster.java ; Point.java
 *
 * Solution implemented by DataOnFocus
 * www.dataonfocus.com
 * 2015
 *
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dataOnFocusKmeans.Cluster;
import dataOnFocusKmeans.Point;

public class KMeans {

	//Number of Clusters. This metric should be related to the number of points
    private int NUM_CLUSTERS = 6;    
//    //Number of Points
//    private int NUM_POINTS = 15;
    //Min and Max X and Y
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_COORDINATE = 5;
    
    private List points;
    private List clusters;
    private HashMap<Integer, HashMap<Integer, Double>> data;
    private int maxIterations = 0;
    
    public KMeans() {
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();    	
    }
    
    public KMeans(HashMap<Integer, HashMap<Integer, Double>> m){
    	data = m;
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();
    }
    
    
    public KMeans(HashMap<Integer, HashMap<Integer, Double>> m, int it){
    	data = m;
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();
    	this.maxIterations = it;
    }
    
    //Initializes the process
    public void init() {
    	//Create Points
    	Set cle = data.keySet();
    	Iterator it = cle.iterator();
    	while(it.hasNext()){
    		Object ob = it.next();
    		points.add(new Point(data.get(ob), (int)ob));
    	}
    	//Create Clusters
    	//Set Random Centroids
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = Point.createRandomPoint(MIN_COORDINATE,MAX_COORDINATE);
    		cluster.setCentroid(centroid);
    		clusters.add(cluster);
    	}
    	
    	//Print Initial state
    	plotClusters();
    }

	private void plotClusters() {
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster c = (Cluster) clusters.get(i);
    		c.plotClusterCourt();
    	}
    }
    
	//The process to calculate the K Means, with iterating method.
    public void calculate() {
        boolean finish = false;
        int iteration = 0;
        
        // Add in new data, one at a time, recalculating centroids with each new one. 
        while(!finish && iteration < maxIterations) {
        	//Clear cluster state
        	clearClusters();
        	
        	List lastCentroids = getCentroids();
        	
        	//Assign points to the closer cluster
        	assignCluster();
            
            //Calculate new centroids.
        	calculateCentroids();
        	
        	iteration++;
        	
        	List currentCentroids = getCentroids();
        	
        	//Calculates total distance between new and old Centroids
        	double distance = 0;
        	for(int i = 0; i < lastCentroids.size(); i++) {
        		distance += Point.distance((Point) lastCentroids.get(i), (Point) currentCentroids.get(i));
        	}
        	System.out.println("#################");
        	System.out.println("Iteration: " + iteration);
        	System.out.println("Centroid distances: " + distance);
        	plotClusters();
        	        	
        	if(distance == 0) {
        		finish = true;
        	}
        }
    }
    
    private void clearClusters() {
    	for(int i = 0 ; i < clusters.size() ; i++) {
    		((Cluster) clusters.get(i)).clear();
    	}
    }
    
    private List getCentroids() {
    	List centroids = new ArrayList(NUM_CLUSTERS);
    	for(int i = 0 ; i < clusters.size() ; i++) {
    		Point aux = ((Cluster) clusters.get(i)).getCentroid();
    		Point point = new Point(aux.getCoord());
    		centroids.add(point);
    	}
    	return centroids;
    }
    
    private void assignCluster() {
        double max = Double.MAX_VALUE;
        double min = max; 
        int cluster = 0;                 
        double distance = 0.0; 
        
        for(int j = 0 ; j< points.size() ; j++) {
        	min = max;
            for(int i = 0; i < NUM_CLUSTERS; i++) {
            	Cluster c = (Cluster) clusters.get(i);
                distance = Point.distance((Point) points.get(j), c.getCentroid());
                if(distance < min){
                    min = distance;
                    cluster = i;
                }
            }
            ((Point) points.get(j)).setCluster(cluster);
            ((Cluster) clusters.get(cluster)).addPoint((Point) points.get(j));
        }
    }
    
    private void calculateCentroids() {
    	for(int i=0; i<clusters.size(); i++){
    		// pour chaque cluster
    		List list = ((Cluster) clusters.get(i)).getPoints(); // on récupère les points du cluster
    		int nbr_points = list.size(); // on recupere la taille de la liste
    		
    		// nouvelle Hashmap qui contiendra les coord du prochain centroid
    		HashMap<Integer, Double> nouvMap = new HashMap();
    		// recuperation du centroid actuel
    		Point centroid = ((Cluster) clusters.get(i)).getCentroid();
    		System.out.println("Centroid coord size :" + centroid.getCoord().size());
    		for(int j=0; j<centroid.getCoord().size(); j++){
    			// on regarde pour chaque point si il possede une note pour l utilisateur "j"
    			for(int k=0; k<list.size(); k++){
    				Point temp = (Point) list.get(k);
    				// si le point possède une note pour cet user
    				if(temp.getCoord().containsKey(j+1)){
    					// si il y a une valeurs dans la nouvelleMap
    					if(nouvMap.containsKey(j+1)){
    						// on recupere l ancienne valeur
    						double d = nouvMap.get(j+1);
    						d += temp.getCoord().get(j+1);
    						nouvMap.put(j+1, d);
    					}else{
    						// on ajoute la valeur à la HashMap
    						double d = temp.getCoord().get(j+1);
    						nouvMap.put(j+1, d);
    					}
    				}else{
    					nouvMap.put(j+1, centroid.getCoord().get(j+1));
    				}
    			}
    		}
    		// une fois la nouvelle HashMap remplie
    		// on va diviser chaque valeurs de la HashMap par le nombre de points dans le cluster
    		for(int j=0;j<nouvMap.size()-1;j++){
    			
    			double temp = nouvMap.get(j+1);
    			temp /= nbr_points;
    			nouvMap.put(j+1, temp);
    		}
    		// on change les coord du centroid
    		centroid.setCoord(nouvMap);
    		((Cluster) clusters.get(i)).setCentroid(centroid);
    	}
    }
    
    public void listFilm(){
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster c = (Cluster) clusters.get(i);
    		System.out.println("---------------------------------------------------------------------------------------------");
    		System.out.println(c.recupererFilm());
    	}
    }
}