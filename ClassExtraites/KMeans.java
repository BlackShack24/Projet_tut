package ClassExtraites;

import java.util.Random;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.DenseInstance;
import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.EuclideanDistance;
import net.sf.javaml.tools.DatasetTools;
import net.sf.javaml.clustering.Clusterer;

public class KMeans
implements Clusterer
{
	private int numberOfClusters = -1;
	private int numberOfIterations = -1;
	private Random rg;
	private DistanceMeasure dm;
	private Instance[] centroids;
	int iterationCount = 0;

	public KMeans()
	{
		this(4);
	}

	public KMeans(int k)
	{
		this(k, 100);
	}

	public KMeans(int clusters, int iterations)
	{
		this(clusters, iterations, new EuclideanDistance());
	}

	public KMeans(int clusters, int iterations, DistanceMeasure dm)
	{
		this.numberOfClusters = clusters;
		this.numberOfIterations = iterations;
		this.dm = dm;
		this.rg = new Random(System.currentTimeMillis());
	}

	public Dataset[] cluster(Dataset data)
	{
		if (data.size() == 0) {
			throw new RuntimeException("The dataset should not be empty");
		}
		if (this.numberOfClusters == 0) {
			throw new RuntimeException("There should be at least one cluster");
		}
		Instance min = DatasetTools.minAttributes(data);
		Instance max = DatasetTools.maxAttributes(data);
		this.centroids = new Instance[this.numberOfClusters];
		int instanceLength = data.instance(0).noAttributes();
		for (int j = 0; j < this.numberOfClusters; j++)
		{
			double[] randomInstance = DatasetTools.getRandomInstance(data, this.rg);
			this.centroids[j] = new DenseInstance(randomInstance);
		}

		boolean centroidsChanged = true;
		boolean randomCentroids = true;
		while ((randomCentroids) || ((iterationCount < this.numberOfIterations) && (centroidsChanged)))
		{
			iterationCount++;

			int[] assignment = new int[data.size()];
			for (int i = 0; i < data.size(); i++)
			{
				int tmpCluster = 0;
				double minDistance = this.dm.measure(this.centroids[0], data.instance(i));
				for (int j = 1; j < this.centroids.length; j++)
				{
					double dist = this.dm.measure(this.centroids[j], data.instance(i));
					if (this.dm.compare(dist, minDistance))
					{
						minDistance = dist;
						tmpCluster = j;
					}
				}
				assignment[i] = tmpCluster;
			}
			double[][] sumPosition = new double[this.numberOfClusters][instanceLength];
			int[] countPosition = new int[this.numberOfClusters];
			for (int i = 0; i < data.size(); i++)
			{
				Instance in = data.instance(i);
				for (int j = 0; j < instanceLength; j++) {
					sumPosition[assignment[i]][j] += in.value(j);
				}
				countPosition[assignment[i]] += 1;
			}
			centroidsChanged = false;
			randomCentroids = false;
			for (int i = 0; i < this.numberOfClusters; i++) {
				if (countPosition[i] > 0)
				{
					double[] tmp = new double[instanceLength];
					for (int j = 0; j < instanceLength; j++) {
						tmp[j] = ((float)sumPosition[i][j] / countPosition[i]);
					}
					Instance newCentroid = new DenseInstance(tmp);
					if (this.dm.measure(newCentroid, this.centroids[i]) > 1.0E-4D)
					{
						centroidsChanged = true;
						this.centroids[i] = newCentroid;
					}
				}
				else
				{
					double[] randomInstance = new double[instanceLength];
					for (int j = 0; j < instanceLength; j++)
					{
						double dist = Math.abs(max.value(j) - min.value(j));
						randomInstance[j] = ((float)(min.value(j) + this.rg.nextDouble() * dist));
					}
					randomCentroids = true;
					this.centroids[i] = new DenseInstance(randomInstance);
				}
			}
		}
		Dataset[] output = new Dataset[this.centroids.length];
		for (int i = 0; i < this.centroids.length; i++) {
			output[i] = new DefaultDataset();
		}
		for (int i = 0; i < data.size(); i++)
		{
			int tmpCluster = 0;
			double minDistance = this.dm.measure(this.centroids[0], data.instance(i));
			for (int j = 0; j < this.centroids.length; j++)
			{
				double dist = this.dm.measure(this.centroids[j], data.instance(i));
				if (this.dm.compare(dist, minDistance))
				{
					minDistance = dist;
					tmpCluster = j;
				}
			}
			output[tmpCluster].add(data.instance(i));
		}
		return output;
	}

	public int getIterationCount() {
		return iterationCount;
	}



}
