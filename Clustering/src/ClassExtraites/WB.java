package ClassExtraites;

import net.sf.javaml.clustering.evaluation.ClusterEvaluation;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

public class WB
implements ClusterEvaluation
{
	private DistanceMeasure dm;
	double dw = 0.0D;double fw = 0.0D;
	double db = 0.0D;double fb = 0.0D;

	public WB(DistanceMeasure dm)
	{
		this.dm = dm;
	}

	public double score(Dataset[] datas)
	{

		for (int i = 0; i < datas.length; i++) {
			for (int j = 0; j < datas[i].size(); j++)
			{
				Instance x = datas[i].instance(j);
				for (int k = j + 1; k < datas[i].size(); k++)
				{
					Instance y = datas[i].instance(k);
					double distance = ((EuclideanDistance) this.dm).calculateDistance(x, y);
					dw += distance;
					fw += 1.0D;
				}
				for (int k = i + 1; k < datas.length; k++) {
					for (int l = 0; l < datas[k].size(); l++)
					{
						Instance y = datas[k].instance(l);
						double distance = ((EuclideanDistance) this.dm).calculateDistance(x, y);
						db += distance;
						fb += 1.0D;
					}
				}
			}
		}
		double wb = dw / fw / (db / fb);
		return wb;
	}

	public boolean compareScore(double score1, double score2)
	{
		return score2 < score1;
	}

	public double getDw() {
		return dw;
	}

	public double getDb() {
		return db;
	}


}
