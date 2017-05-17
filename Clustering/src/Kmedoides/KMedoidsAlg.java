package Kmedoides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import ClassExtraites.EuclideanDistance;
import ClassExtraites.KMedoids;
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

public class KMedoidsAlg {

	public static void main(String[] args) throws Exception {

		Donnees d = new Donnees();
		Dataset data = d.extraireDonnees();
		long debut = System.currentTimeMillis();
		
		Clusterer cl = new KMedoids(4, 10, new EuclideanDistance());
		
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

//		/* Create object for the evaluation of the clusters */ 
//		ClusterEvaluation eval;
//		/* Measuring the quality of the clusters (multiple measures) */ 
//		eval = new SumOfSquaredErrors(); 
//		System.out.println("Score according to SumOfSquaredErrors: " + eval.score(clusters)); 
//		eval = new SumOfCentroidSimilarities(); 
//		System.out.println("Score according to SumOfCentroidSimilarities: " + eval.score(clusters)); 
//		eval = new SumOfAveragePairwiseSimilarities(); 
//		System.out.println("Score according to SumOfAveragePairwiseSimilarities: " + eval.score(clusters)); 
	}
}
