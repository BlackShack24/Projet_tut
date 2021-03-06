package Test;
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

import Test.Cluster;
import Test.Point;

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
    
    public KMeans(HashMap<Integer, HashMap<Integer, Double>> m, int it, int nbclus){
    	data = m;
    	this.points = new ArrayList();
    	this.clusters = new ArrayList();
    	this.maxIterations = it;
    	this.NUM_CLUSTERS = nbclus;
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
    	ArrayList<Point> centroids = Point.createRandomCentroid(NUM_CLUSTERS, points);
    	for (int i = 0; i < NUM_CLUSTERS; i++) {
    		Cluster cluster = new Cluster(i);
    		Point centroid = new Point(centroids.get(i));
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
    		List list = ((Cluster) clusters.get(i)).getPoints(); // on r�cup�re les points du cluster
    		int nbr_points = list.size(); // on recupere la taille de la liste
    		
    		// nouvelle Hashmap qui contiendra les coord du prochain centroid
    		HashMap<Integer, Double> nouvMap = new HashMap();
    		// recuperation du centroid actuel
    		Point centroid = ((Cluster) clusters.get(i)).getCentroid();
    		// on parcours la liste des points
    		for(int j=0; j<list.size();j++){
    			// pour chaque point on regarde si on a pas ajoute le point a l arraylist
    			Point p = (Point) list.get(j);
    			// on va parcourir les notes de ce point
    			Set clePoint = p.getCoord().keySet();
    			Iterator itPoint = clePoint.iterator();
    			while(itPoint.hasNext()){
    				// on recupere l utilisateur
    				int user = (int) itPoint.next();
    				// on regarde si la nouvMap a l user
    				if(nouvMap.containsKey(user)){
    					// on recupere la note de nouvmap pour l user on y ajoute celle du point et on remet ca dans nouvmap
    					double temp = nouvMap.get(user);
    					temp += p.getCoord().get(user);
    					nouvMap.put(user, temp);
    				}else{
    					nouvMap.put(user, p.getCoord().get(user));
    				}
    			}
    		}
    		// ensuite on divise chaque somme par le nombre de points pour faire une moyenne
        	Set cleCentr = nouvMap.keySet();
        	Iterator itCentr = cleCentr.iterator();
        	while(itCentr.hasNext()){
        		int userTemp = (int) itCentr.next();
        		double temp = nouvMap.get(userTemp);
        		temp/= nbr_points;
        		nouvMap.put(userTemp, temp);
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
