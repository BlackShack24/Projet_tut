package Test;

import java.util.HashMap;

public class Essai {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		
		
//		kmeans.calculate();
		
		Data d = new Data();
		HashMap<Integer, HashMap<Integer, Double>> map =d.extraireDonnees();
		
		KMeans kmeans = new KMeans(map);

		kmeans.init();
	}

}
