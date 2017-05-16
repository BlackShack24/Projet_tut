package commun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.core.SparseInstance;

public class Donnees {

	public Dataset extraireDonnees() throws Exception{
		// Calcul temps d'execution
		long debut = System.currentTimeMillis();

		/*Create dateset*/
		Dataset data = new DefaultDataset();

		//HashMap<Integer, HashMap<Integer, Double>> notes_items = new HashMap<>();
		File f1 = new File("Data\\u.data");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		String line = "";
		String[] values;
		double idu, idi, k=0;
		double note;
		double indiv = 0;
		// l objet data est un objet qui se comporte comme une liste
		// on passe donc par une hashmap pour que les film soient triés 
		// meme si dans le fichier il ne le sont pas
		HashMap<Integer, Instance> notes_items = new HashMap<>();
		// l instance I va avoir comme clef les idFilm lies a une Instance
		// la seconde contiens en clef les idu et en valeur le film.
		int nbF = 0;

		while (k<100000) {
			line = br.readLine();
			values = line.split("\t");
//			System.out.println("IDU : "+ values[0]+ " IDF : "+values[1]+" note : " +values[2]);
			idu = Double.parseDouble(values[0]);
			idi = Double.parseDouble(values[1]);
			note = Double.parseDouble(values[2]);
			if(notes_items.containsKey((int)idi)){
				// la map contiens le film
				// on recupere la liste des notes pour ce film
				// on ajoute l utilisateur et la note lue
				notes_items.get((int) idi).put((int) idu, note);
				// on part du principe qu'il n'y a pas un utilisateur qui note 2x un film
			}else{
				// la map ne contiens pas le film
				// on cree une Instance
				Instance i = new SparseInstance();
				// on ajoute la note de l utilisateur pour le film
				i.put((int) idu, note);
				// on ajoute le film a la liste des film
				notes_items.put((int)idi, i);
				//System.out.println("Pas dans la map");
				nbF ++;
			}
			k++;
		}
		System.out.println("Nombre de films : "+nbF);

		
		Set cles = notes_items.keySet();
		Iterator it = cles.iterator();
		// une fois la Hashmap remplie on va tout mettre dans la liste Data
		while(it.hasNext()) {
			data.add(notes_items.get(it.next()));
			// voir le add avec indice et Instance
		}
		
		System.out.println("Hashmap taille : "+notes_items.size());
		System.out.println("Data taille :"+data.size());

		br.close();

		return data;

	}


}
