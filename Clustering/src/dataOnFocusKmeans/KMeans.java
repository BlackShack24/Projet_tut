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

import javax.swing.plaf.synth.SynthSeparatorUI;

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

	public void calculerInterCluster(){
		// on calcule la distance intercluster :
		// on cree une matrice carree dans laquelle il y aura la distance entre deux cluster
		// [i][j] i = lignes j = col
		double[][] dist = new double[NUM_CLUSTERS][NUM_CLUSTERS];
		for(int i=0;i<dist.length;i++){
			for(int j=0;j<dist.length;j++){
				// on compare le centroid i au centroid j :
				Cluster c1 = (Cluster)clusters.get(i);
				Cluster c2 = (Cluster)clusters.get(j);
				dist[i][j] = Point.distance(c1.centroid, c2.centroid);
			}
		}
		// on calcule la distance intra cluster :
		// pour chaques cluster
		double []distcumulPtCtroide =new double[NUM_CLUSTERS];
		for(int a=0;a<clusters.size();a++){
			double distance =0;
			// on recupere le cluster
			Cluster c = (Cluster) clusters.get(a);
			// on recupere le centroide
			Point centroide = c.centroid;
			// on recupere la liste des points de ce cluster
			List points = c.getPoints();
			// pour chaque point on calcule la distance entre le point et le centroide
			for(int b=0;b<points.size();b++){
				distance += Point.distance((Point)points.get(b), centroide);
			}
			distcumulPtCtroide[a] = distance;
		}

		System.out.println("Tableau de distance entre les centroides des clusters : ");
		for(int i=0;i<dist.length;i++){
			System.out.println("-------------------------------------------------");
			String print = "";
			for(int j=0; j<dist.length;j++){
				if(j==0){
					print += "| ";
				}
				print += " | " +dist[i][j] +" | ";
			}
			System.out.println(print);
		}

	}

	public void calculerIntraCluster(){
		// creation du tableau qui va posseder la distance moyenne intracluster pour chaque cluster
		double[] dist = new double[NUM_CLUSTERS];
		// on va calculer pour chaque cluster la distance intracluster
		for(int i=0;i<NUM_CLUSTERS;i++){
			double distance = 0;
			// on recupere le clusters 
			Cluster c = (Cluster) clusters.get(i);
			// la liste des points des cluster
			List points = c.getPoints();
			for(int j=0;j<points.size();j++){
				// on ajoute la distance entre le point j et le cluster
				distance += Point.distance((Point)points.get(j),c.getCentroid());		
			}
			// on ajoute cette distance au tableau
			dist[i]=distance;
		}

		System.out.println("Tableau de distance intra cluster");
		String print = "";
		for(int a=0;a<dist.length;a++){
			print += " | "+dist[a]+" | "; 
		}
		System.out.println(print);
	}
}