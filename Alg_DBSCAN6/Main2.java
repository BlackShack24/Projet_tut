package Alg_DBSCAN6;

import java.io.File;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.DensityBasedSpatialClustering;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfAveragePairwiseSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfCentroidSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;

public class Main2 { 

	public static void main(String[] args) throws Exception { 
		
		/* Load dataset */ 
		File f1 = new File("C:\\Users\\Clément\\Desktop\\ml-latest-small\\moviesbis.csv");
		Dataset data = FileHandler.loadDataset(f1, ","); 
		
		System.out.println("Density Based Spatial Clustering"); 

		for (int i = 0; i < data.size(); i++) System.out.println(data.get(i)); 
		
		Clusterer cl = new DensityBasedSpatialClustering(); 

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 

		for (int i = 0; i < clusters.length; i++) { 
			FileHandler.exportDataset(clusters[i], new File("output" + i + ".txt")); 
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
