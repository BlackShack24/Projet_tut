package DBScan;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import ClassExtraites.DensityBasedSpatialClustering;
import commun.Donnees;
import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfAveragePairwiseSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfCentroidSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;


public class DBScan {

	public static void main(String[] args) throws Exception { 

		Donnees d = new Donnees();
		
		Dataset data = d.extraireDonnees();
		 long debut = System.currentTimeMillis();
		
		//Epsilon = 0.6, minpoints = 6 and a normalized version of the euclidean distance
		Clusterer cl = new DensityBasedSpatialClustering();

		System.out.println("Méthode utilisée : DBSCAN"); 

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 

		
		System.out.println("Temps 1 : "+((DensityBasedSpatialClustering) cl).getDebut()+" Temps 2 : "+((DensityBasedSpatialClustering) cl).getDebut1()+ " Temps 3 : "+((DensityBasedSpatialClustering) cl).getDebut2());
		
		// Nombre d'iterations
		System.out.println("Nombre iterations : "+((DensityBasedSpatialClustering) cl).getCompteurIteration());
		
		// Temps d'execution du clustering
		System.out.println("Temps clustering : "+(System.currentTimeMillis()-debut)+" millisecondes");

		for (int i = 0; i < clusters.length; i++) System.out.println("Cluster "+(i+1)+" : "+clusters[i].size());
		
		/* Print the number of clusters found */ 
		System.out.println("Number of clusters: " + clusters.length);

		/* Create object for the evaluation of the clusters */ 
		ClusterEvaluation eval;
		/* Measuring the quality of the clusters (multiple measures) */ 
		eval = new SumOfSquaredErrors(); // Somme des carrées des résidus
		System.out.println("Score according to SumOfSquaredErrors: " + eval.score(clusters)); 
		eval = new SumOfCentroidSimilarities(); 
		System.out.println("Score according to SumOfCentroidSimilarities: " + eval.score(clusters)); 
		eval = new SumOfAveragePairwiseSimilarities(); 
		System.out.println("Score according to SumOfAveragePairwiseSimilarities: " + eval.score(clusters)); 
	}
}
