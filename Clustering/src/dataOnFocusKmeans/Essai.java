package dataOnFocusKmeans;

import java.util.HashMap;
import dataOnFocusKmeans.*;

public class Essai {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Data d = new Data();
		HashMap<Integer, HashMap<Integer, Double>> map =d.extraireDonnees();
		KMeans kmeans = new KMeans(map, 20, 4);
		kmeans.init();
		kmeans.calculate();
		kmeans.listFilm();
	}

}
