package commun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;

public class Donnees {

	public Dataset extraireDonnees() throws Exception{
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
				while (k<100) {
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
				return data;
	}
}