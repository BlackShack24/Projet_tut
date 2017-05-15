package ClassExtraites;

import java.util.Random;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.DatasetTools;

public class KMedoids
implements Clusterer
{
	private DistanceMeasure dm;
	private int numberOfClusters;
	private Random rg;
	private int maxIterations;
	int count = 0;

	public KMedoids()
	{
		this(4, 100, new EuclideanDistance());
	}

	public KMedoids(int numberOfClusters, int maxIterations, DistanceMeasure dm)
	{
		this.numberOfClusters = numberOfClusters;
		this.maxIterations = maxIterations;
		this.dm = dm;
		this.rg = new Random(System.currentTimeMillis());
	}

	public Dataset[] cluster(Dataset data)
	{
		Instance[] medoids = new Instance[this.numberOfClusters];
		Dataset[] output = new DefaultDataset[this.numberOfClusters];
		for (int i = 0; i < this.numberOfClusters; i++)
		{
			int random = this.rg.nextInt(data.size());
			medoids[i] = data.instance(random);
		}
		boolean changed = true;

		while ((changed) && (count < this.maxIterations))
		{
			changed = false;
			count++;
			int[] assignment = assign(medoids, data);
			changed = recalculateMedoids(assignment, medoids, output, data);
		}
		return output;
	}

	public int getCount() {
		return count;
	}

	private int[] assign(Instance[] medoids, Dataset data)
	{
		int[] out = new int[data.size()];
		for (int i = 0; i < data.size(); i++)
		{
			double bestDistance = this.dm.measure(data.instance(i), medoids[0]);
			int bestIndex = 0;
			for (int j = 1; j < medoids.length; j++)
			{
				double tmpDistance = this.dm.measure(data.instance(i), medoids[j]);
				if (this.dm.compare(tmpDistance, bestDistance))
				{
					bestDistance = tmpDistance;
					bestIndex = j;
				}
			}
			out[i] = bestIndex;
		}
		return out;
	}

	private boolean recalculateMedoids(int[] assignment, Instance[] medoids, Dataset[] output, Dataset data)
	{
		boolean changed = false;
		for (int i = 0; i < this.numberOfClusters; i++)
		{
			output[i] = new DefaultDataset();
			for (int j = 0; j < assignment.length; j++) {
				if (assignment[j] == i) {
					output[i].add(data.instance(j));
				}
			}
			if (output[i].size() == 0)
			{
				medoids[i] = data.instance(this.rg.nextInt(data.size()));
				changed = true;
			}
			else
			{
				Instance centroid = DatasetTools.average(output[i]);
				Instance oldMedoid = medoids[i];
				medoids[i] = ((Instance)data.kNearest(1, centroid, this.dm).iterator().next());
				if (!medoids[i].equals(oldMedoid)) {
					changed = true;
				}
			}
		}
		return changed;
	}
}
