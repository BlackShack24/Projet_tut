package ClassExtraites;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import net.sf.javaml.core.Instance;
import net.sf.javaml.distance.DistanceMeasure;

abstract class AbstractDensityBasedClustering
{
	DistanceMeasure dm;

	class DataObject
	{
		int clusterIndex = -1;
		double c_dist;
		double r_dist;
		boolean processed = false;
		static final int UNCLASSIFIED = -1;
		static final int UNDEFINED = Integer.MAX_VALUE;
		static final int NOISE = -2;
		Instance instance;

		public DataObject(DensityBasedSpatialClustering densityBasedSpatialClustering, Instance inst)
		{
			this.instance = inst;
		}

		public DataObject(Instance inst)
		{
			this.instance = inst;
		}

		public boolean equals(Object obj)
		{
			DataObject tmp = (DataObject)obj;
			return tmp.instance.equals(this.instance);
		}

		public int hashCode()
		{
			return this.instance.hashCode();
		}

		public String getKey()
		{
			return this.instance.toString();
		}
	}

	Vector<DataObject> dataset = null;

	long debut=0, debut1=0;

	List<DataObject> epsilonRangeQuery(double epsilon, DataObject inst) {
		ArrayList<DataObject> epsilonRange_List = new ArrayList();

		for (int i = 0; i < this.dataset.size(); i++)
		{		
			DataObject tmp = (DataObject)this.dataset.get(i);
			debut = System.currentTimeMillis(); // time
			double distance = this.dm.measure(tmp.instance, inst.instance);
			debut1 += System.currentTimeMillis()-debut; // time
			if (distance < epsilon) epsilonRange_List.add(tmp);
		}
		return epsilonRange_List;
	}

	public long getDebut1() {
		return debut1;
	}

}
