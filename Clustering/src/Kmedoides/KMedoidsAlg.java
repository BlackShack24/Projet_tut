package Kmedoides;

import java.io.File;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import ClassExtraites.EuclideanDistance;
import ClassExtraites.KMedoids;
import ClassExtraites.WB;

import commun.Donnees;

public class KMedoidsAlg {

	public static void main(String[] args) throws Exception {

		Donnees d = new Donnees();
		Dataset data = d.extraireDonnees();
		long debut = System.currentTimeMillis();
		
		Clusterer cl = new KMedoids(6, 60, new EuclideanDistance());
		
		System.out.println("Méthode utilisée : KMedoids"); 

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 
		
		// Nombre d'iterations
		System.out.println("Nombre iterations : "+((KMedoids) cl).getCount());
		
		// Temps d'execution du clustering
		System.out.println("Temps clustering : "+(System.currentTimeMillis()-debut)+" millisecondes");

		for (int i = 0; i < clusters.length; i++) System.out.println("Cluster "+(i+1)+" : "+clusters[i].size());
		
		/* Print the number of clusters found */ 
		System.out.println("Number of clusters: " + clusters.length); 

		for (int i = 0; i < clusters.length; i++) { 
			FileHandler.exportDataset(clusters[i], new File("C:\\Users\\Clément\\Documents\\workspace\\M1_SC\\Projet_tut\\Output\\Kmedoids-output2" + i + ".txt")); 
		}
		
		
		/* Create object for the evaluation of the clusters */ 
		ClusterEvaluation eval;
//		/* Measuring the quality of the clusters (multiple measures) */ 
//		eval = new SumOfSquaredErrors(); 
//		System.out.println("Score according to SumOfSquaredErrors: " + eval.score(clusters)); 
//		eval = new SumOfCentroidSimilarities(); 
//		System.out.println("Score according to SumOfCentroidSimilarities: " + eval.score(clusters)); 
//		eval = new SumOfAveragePairwiseSimilarities(); 
//		System.out.println("Score according to SumOfAveragePairwiseSimilarities: " + eval.score(clusters));
		eval = new WB(new EuclideanDistance());
		eval.score(clusters);
		System.out.println("Score according to SumOfDistanceIntraCluster: " + ((WB) eval).getDw());
		System.out.println("Score according to SumOfDistanceInterCluster: " + ((WB) eval).getDb());
	}
}
