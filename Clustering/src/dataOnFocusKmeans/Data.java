package dataOnFocusKmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;


public class Data {

	public HashMap<Integer,HashMap<Integer, Double>> extraireDonnees() throws Exception{
		// Calcul temps d'execution
		long debut = System.currentTimeMillis();
		HashMap<Integer, HashMap<Integer, Double>> notes_items = new HashMap<>();
		File f1 = new File("Data\\u.data");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line = "";
		String[] values;
		double idu, idi, k=0;
		double note;
		double indiv = 0;
		int nbF = 0;

		while (k<100000) {
			line = br.readLine();
			values = line.split("\t");
			idu = Integer.parseInt(values[0]);
			idi = Integer.parseInt(values[1]);
			note = Double.parseDouble(values[2]);
			if(notes_items.containsKey((int)idi)){
				// on contiens le film
				HashMap<Integer, Double> i = notes_items.get((int)idi);
				i.put((int) idu, note);
				notes_items.put((int)idi, i);
				
			}else{
				// il n'y a pas le film
				HashMap<Integer, Double> temp = new HashMap<>();
				temp.put((int) idu, note);
				notes_items.put((int)idi, temp);
				nbF ++;
			}
			k++;
		}
		br.close();
		return notes_items;

	}

}