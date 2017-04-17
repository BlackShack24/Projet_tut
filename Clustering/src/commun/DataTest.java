package commun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

public class DataTest {

	public Dataset extraireDonneesTest() throws Exception{
		// Calcul temps d'execution
		long debut = System.currentTimeMillis();

		/*Create dateset*/
		Dataset data = new DefaultDataset();

		//HashMap<Integer, HashMap<Integer, Double>> notes_items = new HashMap<>();
		File f1 = new File("Donnees\\u.data");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line;
		String[] values;
		double idu, idi, k=0;
		double note;
		HashMap<Double,Double[]> donnes = new HashMap<>();
		double indiv = 0;
		Instance i = null;
		while (k<200) {
			line = br.readLine();
			k++;
			if (k % 10000 == 0) System.out.println(k);
			values = line.split("\t");
			idu = Double.parseDouble(values[0]);
			idi = Double.parseDouble(values[1]);
			note = Double.parseDouble(values[2]);
			if (note < 1.0) note = 1.0; // On vire les notes de 0.5 s'il y en a			
			Double[] valeurs = new Double[] { idu, note };
			System.out.println(idi);
			/* Create instance*/

			donnes.put(idi, valeurs);
			if(indiv == 0){
				i = new SparseInstance();
				indiv = idi;
			}else{
				if(idi != indiv){
					data.add(i);
					indiv = idi;
					i = new SparseInstance();
				}
			}
			i.put((int) valeurs[0].intValue(), valeurs[1]);
			data.add(i);
		}
		br.close();

		this.printData(data);
		return null;
	}

	public void printData(Dataset d){

		//		System.out.println("Nombre attributs : "+d.noAttributes());
		System.out.println("Taille : "+d.size());
		//		System.out.println(d);
		System.out.println("------------INSTANCE-------------");
		Instance i = d.get(0);
		System.out.println("Nombre attribut de l 'instance : "+i.noAttributes());
		System.out.println("Taille de l'instance : "+i.size());
		System.out.println(i);
		//		for(int j=0; j<i.noAttributes();j++){
		//			if(i.value(j)!=0){
		//				System.out.println("Valeur "+j+" : "+i.value(j));
		//			}
		//			
		//		}
		System.out.println(d.get(1).noAttributes());
		System.out.println(d.get(1));
		System.out.println(d.get(2).noAttributes());
		System.out.println(d.get(2));
		System.out.println(d.get(3).noAttributes());
		System.out.println(d.get(2));
		System.out.println("----------------------------------");

	}
}
