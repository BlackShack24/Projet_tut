package Kmeans;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import ClassExtraites.KMeans;

import commun.Donnees;

public class KMeansAlg {

	public static void main(String[] args) throws Exception {

		Donnees d = new Donnees();
		Dataset data = d.extraireDonneesTest();
		long debut = System.currentTimeMillis();

		Clusterer cl = new KMeans(4,10);

		System.out.println("Méthode utilisée : KMeans"); 

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 

		// Nombre d'iterations
		System.out.println("Nombre iterations : "+((KMeans) cl).getIterationCount());

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
