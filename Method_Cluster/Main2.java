package Method_Cluster;

import java.io.File;
import java.util.Scanner;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.DensityBasedSpatialClustering;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.clustering.OPTICS;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfAveragePairwiseSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfCentroidSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

public class Main2 { 

	public static void main(String[] args) throws Exception { 
		
		Scanner scan = new Scanner(System.in);
		int input;
		String methode = "";
		
		/* Load dataset */ 
		File f1 = new File("C:\\Users\\Clément\\Desktop\\ml-latest-small\\ratingbis.csv");
		Dataset data = FileHandler.loadDataset(f1, ","); 
		
		System.out.println("Choisir méthode K-means[1]-Kmedoids[2]-DBSCAN[3]-OPTICS[4]");
		input = scan.nextInt();
		
		Clusterer cl;
		if(input==1) {
			cl = new KMeans();
			methode = "Kmeans";
		}
		else if(input==2) {
			cl = new KMedoids();
			methode = "Kmedoids";
		}
		else if(input==3)	{
			cl = new DensityBasedSpatialClustering();
			methode = "DBSCAN";
		}
		else {
			cl = new OPTICS();
			methode = "OPTICS";
		}
		
		System.out.println("Méthode utilisée : "+methode); 

		for (int i = 0; i < data.size(); i++) System.out.println(data.get(i)); 
		
		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 

		for (int i = 0; i < clusters.length; i++) { 
			FileHandler.exportDataset(clusters[i], new File("C:\\Users\\Clément\\Documents\\workspace\\M1_SC\\Projet_tut\\Output\\"+methode+"output" + i + ".txt")); 
		} 
		/* Print the number of clusters found */ 
		System.out.println("Number of clusters: " + clusters.length); 
		
		/* Create object for the evaluation of the clusters */ 
		ClusterEvaluation eval;
		/* Measuring the quality of the clusters (multiple measures) */ 
		eval = new SumOfSquaredErrors(); 
		System.out.println("Score according to SumOfSquaredErrors: " + eval.score(clusters)); 
		eval = new SumOfCentroidSimilarities(); 
		System.out.println("Score according to SumOfCentroidSimilarities: " + eval.score(clusters)); 
		eval = new SumOfAveragePairwiseSimilarities(); 
		System.out.println("Score according to SumOfAveragePairwiseSimilarities: " + eval.score(clusters)); 
	} 
}
