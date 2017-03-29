package ClassExtraites;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.distance.DistanceMeasure;
import net.sf.javaml.distance.NormalizedEuclideanDistance;

public class DensityBasedSpatialClustering
extends AbstractDensityBasedClustering
implements Clusterer
{
	private double epsilon;
	private int minPoints;
	private int clusterID;

	int compteurIteration = 0; // Init compteur iteration

	public DensityBasedSpatialClustering()
	{
		this(0.1D, 6);
	}

	public DensityBasedSpatialClustering(double epsilon, int minPoints)
	{
		this(epsilon, minPoints, null);
	}

	public DensityBasedSpatialClustering(double epsilon, int minPoints, DistanceMeasure dm)
	{
		this.dm = dm;
		this.epsilon = epsilon;
		this.minPoints = minPoints;
	}

	private boolean expandCluster(AbstractDensityBasedClustering.DataObject dataObject)
	{
		HashSet<AbstractDensityBasedClustering.DataObject> usedSeeds = new HashSet();
		List<AbstractDensityBasedClustering.DataObject> seedList = epsilonRangeQuery(this.epsilon, dataObject);
		usedSeeds.addAll(seedList);
		if (seedList.size() < this.minPoints)
		{
			dataObject.clusterIndex = -2;
			return false;
		}
		for (int i = 0; i < seedList.size(); i++)
		{
			AbstractDensityBasedClustering.DataObject seedListDataObject = (AbstractDensityBasedClustering.DataObject)seedList.get(i);

			seedListDataObject.clusterIndex = this.clusterID;
			if (seedListDataObject.equals(dataObject))
			{
				seedList.remove(i);
				i--;
			}
		}
		while (seedList.size() > 0)
		{
			AbstractDensityBasedClustering.DataObject seedListDataObject = (AbstractDensityBasedClustering.DataObject)seedList.get(0);
			List<AbstractDensityBasedClustering.DataObject> seedListDataObject_Neighbourhood = epsilonRangeQuery(this.epsilon, seedListDataObject);
			if (seedListDataObject_Neighbourhood.size() >= this.minPoints) {
				for (int i = 0; i < seedListDataObject_Neighbourhood.size(); i++)
				{
					AbstractDensityBasedClustering.DataObject p = (AbstractDensityBasedClustering.DataObject)seedListDataObject_Neighbourhood.get(i);
					if (((p.clusterIndex == -1) || (p.clusterIndex == -2)) && 
							(p.clusterIndex == -1) && 
							(!usedSeeds.contains(p)))
					{
						seedList.add(p);
						usedSeeds.add(p);
					}
					p.clusterIndex = this.clusterID;
				}
			}
			seedList.remove(0);
		}
		return true;
	}

	private Dataset originalData = null;

	long debut, debut1, debut2;
	
	public Dataset[] cluster(Dataset data)
	{
		
		this.originalData = data;
		if (this.dm == null) {
			this.dm = new NormalizedEuclideanDistance(this.originalData);
		}
		this.clusterID = 0;
		this.dataset = new Vector();
		for (int i = 0; i < data.size(); i++) {
			this.dataset.add(new AbstractDensityBasedClustering.DataObject(this, data.instance(i)));
		}
		
		Collections.shuffle(this.dataset);

		ArrayList<Dataset> output = new ArrayList();
		for (AbstractDensityBasedClustering.DataObject dataObject : this.dataset) {
			if ((dataObject.clusterIndex == -1) && (expandCluster(dataObject))) {
				compteurIteration++;
				output.add(extract(this.clusterID));
				this.clusterID += 1;
			}
		}
		
		debut2 = System.currentTimeMillis()-debut2;
		
		return (Dataset[])output.toArray(new Dataset[0]);
	}

	
	public int getCompteurIteration() {
		return compteurIteration;
	}

	public long getDebut() {
		return debut;
	}

	public long getDebut1() {
		return debut1;
	}

	public long getDebut2() {
		return debut2;
	}

	private Dataset extract(int clusterID)
	{
		Dataset cluster = new DefaultDataset();
		for (AbstractDensityBasedClustering.DataObject dataObject : this.dataset) {
			if (dataObject.clusterIndex == clusterID) {
				cluster.add(dataObject.instance);
			}
		}
		return cluster;
	}
}
