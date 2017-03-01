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
	DataSource source = new DataSource("C:\\Users\\Clément\\Desktop\\ml-latest-small\\ratingbis.csv");
	Instances instances = source.getDataSet();
	
	HierarchicalClusterer HC = new HierarchicalClusterer();
	HC.buildClusterer(instances);
	
	System.out.println("Méthode utilisée : CAH"); 

//	for (int j = 0; j < HC.numberOfClusters(); j++) System.out.println(HC.get(j));

	/* The actual clustering of the data */
//	Dataset[] clusters = HC.cluster(data); 
//
//	for (int i = 0; i < clusters.length; i++) { 
//		FileHandler.exportDataset(clusters[i], new File("C:\\Users\\Clément\\Documents\\workspace\\M1_SC\\Projet_tut\\Output\\DBSCANoutput" + i + ".txt")); 
//	} 
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
