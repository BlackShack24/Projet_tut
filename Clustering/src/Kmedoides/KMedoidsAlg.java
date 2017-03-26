package Kmedoides;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import ClassExtraites.KMedoids;
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

		// Calcul temps d'execution
		long debut = System.currentTimeMillis();
		
		/*Create dateset*/
		Dataset data = new DefaultDataset();
		
		HashMap<Integer, HashMap<Integer, Double>> notes_items = new HashMap<>();
		File f1 = new File("Donnees\\ratings.csv");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line = br.readLine();
		String[] values;
		double idu, idi, k=0;
		double note;
		while (k<400) {
			line = br.readLine();
			k++;
			if (k % 10000 == 0) System.out.println(k);
			values = line.split(",");
			idu = Double.parseDouble(values[0]);
			idi = Double.parseDouble(values[1]);
			note = Double.parseDouble(values[2]);
			if (note < 1.0) note = 1.0; // On vire les notes de 0.5 s'il y en a			
			double[] valeurs = new double[] { idi, idu, note };
			/* Create instance*/
			Instance instance = new DenseInstance(valeurs);
			data.add(instance);
		}
		
		br.close();
		
		// Temps d'execution pour r�cuperer les donn�es
		System.out.println("Temps r�cup�ration de donn�es : "+(System.currentTimeMillis()-debut)+" millisecondes");
		debut = System.currentTimeMillis();
		
		Clusterer cl = new KMedoids();
		
		System.out.println("M�thode utilis�e : KMedoids"); 

		/* The actual clustering of the data */ 
		Dataset[] clusters = cl.cluster(data); 
		
		// Nombre d'iterations
		System.out.println("Nombre iterations : "+((KMedoids) cl).getCount());
		
		// Temps d'execution du clustering
		System.out.println("Temps clustering : "+(System.currentTimeMillis()-debut)+" millisecondes");

		for (int i = 0; i < clusters.length; i++) System.out.println("Cluster "+(i+1)+" : "+clusters[i].size());
		
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
