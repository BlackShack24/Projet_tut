package commun;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
				File f1 = new File("Donnees\\ratings.csv");
				BufferedReader br = new BufferedReader(new FileReader(f1));
				String line = br.readLine();
				String[] values;
				double idu, idi, k=0;
				double note;
				while (k<200) {
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
		
		return data;
	}

	public Dataset extraireDonneesTest() throws Exception{
		// Calcul temps d'execution
		long debut = System.currentTimeMillis();
		// on cree le DataSet a retourner
		Dataset data = new  DefaultDataset();
		// creation du fichier
		File f1 = new File("Donnees\\ratings.csv");
		BufferedReader br = new BufferedReader(new FileReader(f1));
		BufferedReader br2 = new BufferedReader(new FileReader(f1));
		String line = br.readLine();
		String line2 = br2.readLine();
		String[] values;
		double idu, idi;
		int k=0, l=0;
		double note;
		ArrayList<double[]> donnes = new ArrayList<double[]>();
		// boucle de parcours du fichier
		double idimax = 0;
		// on recupere l idfilmMax
		while(l<200){
			line2 = br2.readLine();
			values = line2.split(",");
			idi = Double.parseDouble(values[1]);
			if(idi > idimax){
				idimax = idi;
			}
			l++;
		}
		br2.close();
		System.out.println("idfilmMax : " + idimax);
		
		double iduprec = 1;
		while(k<200){ //3627 = 20 user
			line = br.readLine();
			//if (k % 10000 == 0) System.out.println(k);
			values = line.split(",");
			idu = Double.parseDouble(values[0]);
			idi = Double.parseDouble(values[1]);
			note = Double.parseDouble(values[2]);
			if (note < 1.0) note = 1.0; // On vire les notes de 0.5 s'il y en a
			if(iduprec != idu){
				System.out.println("Test1");
				if(donnes.get(k-1)[1] != idimax){
					System.out.println("Test2");
					double[] valeurs = new double[] { iduprec, idimax, 0};
					iduprec = idu;
				}
			}
			// on doit regrouper les ID film ensembles 
			double[] valeurs = new double[] { idu, idi, note };
			/* Create instance*/
			donnes.add(valeurs);
			k++;
		}
		br.close();

		// on ajoute une valeur pour la note du "dernier" film si il n'y en a pas
		double user = donnes.get(0)[0];
		for(int i=0;i<donnes.size();i++){
			if(donnes.get(i)[0] !=user){
				if(donnes.get(i-1)[1] != idimax){
					
				}
			}
		}
		// tri de l ArrayList
		double indiv = donnes.get(0)[0];
		Instance instance = new SparseInstance();
		for(int i=0; i<donnes.size();i++){
			if(donnes.get(i)[0]!=indiv) {
				data.add(instance);
				indiv = donnes.get(i)[0];
				instance = new SparseInstance();
			}
			instance.put((int) donnes.get(i)[1], donnes.get(i)[2]);
		}
		data.add(instance);
		// Temps d'execution pour récuperer les données
		System.out.println("Temps récupération de données : "+(System.currentTimeMillis()-debut)+" millisecondes");
		this.printData(data);
		return data;
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
