package HierarchiqueAscendante;

import java.io.File;

import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfAveragePairwiseSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfCentroidSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.data.FileHandler;
import weka.clusterers.HierarchicalClusterer;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class HierarchiqueAscendante {

	public static void main(String[] args) throws Exception { 

		/* Load dataset */ 
		DataSource source = new DataSource("Donnees\\ratings.csv");
		Instances instances = source.getDataSet();

		HierarchicalClusterer HC = new HierarchicalClusterer();
		HC.buildClusterer(instances);

		System.out.println("Méthode utilisée : "+HC.globalInfo()); 

		/* The actual clustering of the data */
//		Dataset[] clusters = HC.cluster(data); 		
//		for (int i = 0; i < HC.numberOfClusters(); i++) System.out.println("Cluster "+(i+1)+" : "+HC.;


		/* Print the number of clusters found */ 
		System.out.println("Number of clusters: " + HC.numberOfClusters()); 


		//	/* Create object for the evaluation of the clusters */ 
		//	ClusterEvaluation eval;
		//	/* Measuring the quality of the clusters (multiple measures) */ 
		//	eval = new SumOfSquaredErrors(); 
		//	System.out.println("Score according to SumOfSquaredErrors: " + eval.score(clusters)); 
		//	eval = new SumOfCentroidSimilarities(); 
		//	System.out.println("Score according to SumOfCentroidSimilarities: " + eval.score(clusters)); 
		//	eval = new SumOfAveragePairwiseSimilarities(); 
		//	System.out.println("Score according to SumOfAveragePairwiseSimilarities: " + eval.score(clusters)); 
	}

}
