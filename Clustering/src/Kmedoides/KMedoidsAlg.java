package Kmedoides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMedoids;
import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.clustering.evaluation.SumOfAveragePairwiseSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfCentroidSimilarities;
import net.sf.javaml.clustering.evaluation.SumOfSquaredErrors;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.data.FileHandler;

public class KMedoidsAlg {

	public static void main(String[] args) throws Exception {
		
		
		/*Create dateset*/
		Dataset data = new DefaultDataset();
		
		HashMap<Integer, HashMap<Integer, Double>> notes_items = new HashMap<>();
		File f1 = new File("Donnees\\ratings.csv");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line = br.readLine();
		String[] values;
		double idu, idi, note;
		int k=0;
		while ((line = br.readLine()) != null && k<10000) {
			k++;
			//if (k % 10000 == 0) System.out.println(k);
			values = line.split(",");
			idu = Double.parseDouble(values[0]);
			idi = Double.parseDouble(values[1]);
			note = Double.parseDouble(values[2]);
			if (note < 1.0) note = 1.0; // On vire les notes de 0.5 s'il y en a
//			if (!notes_items.containsKey(idi)) 	notes_items.put(idi, new HashMap());
//			notes_items.get(idi).put(idu, note);			
			double[] valeurs = new double[] { idi, idu, note };
			/* Create instance*/
			Instance instance = new DenseInstance(valeurs);
			data.add(instance);
		}
		
		br.close();
		DistanceMeasure dm = new EuclideanDistance();
		Clusterer cl = new KMedoids(10, 20000, dm);

		System.out.println("Méthode utilisée : KMedoids"); 

		for (int j = 0; j < data.size(); j++) System.out.println(data.get(j));

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 

		for (int i = 0; i < clusters.length; i++) { 
			FileHandler.exportDataset(clusters[i], new File("C:\\Users\\Clément\\Documents\\workspace\\M1_SC\\Projet_tut\\Output\\KMedoidsoutput" + i + ".txt")); 
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

