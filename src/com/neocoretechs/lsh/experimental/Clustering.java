package com.neocoretechs.lsh.experimental;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.neocoretechs.lsh.F32FloatTensor;
import com.neocoretechs.lsh.families.DistanceMeasure;
import com.neocoretechs.lsh.families.EuclideanDistance;

public class Clustering {
	
	private static class Cluster{
		List<F32FloatTensor> members;
		F32FloatTensor seed;
		public Cluster(F32FloatTensor seed){
			members = new ArrayList<F32FloatTensor>();
			this.seed=seed;
		}
		
		public F32FloatTensor getSeed(){
			return this.seed;
		}
		
		public void addVector(F32FloatTensor v){
			members.add(v);
		}
		
		public void determineNewSeed(){
			F32FloatTensor newSeed = new F32FloatTensor(seed.getSize());
			for(F32FloatTensor v: members){
				for(int d=0;d<v.getSize();d++){
					newSeed.setFloat(d,newSeed.getFloat(d) + v.getFloat(d));
				}
			}
			double memberSize = members.size();
			for(int d=0;d<newSeed.getSize();d++){
				newSeed.setFloat(d,newSeed.getFloat(d) / memberSize);
			}
			this.seed=newSeed;
		}
		
		public String toString(){
			StringBuilder sb = new StringBuilder("[");
			for(int i = 0 ; i < members.size();i++){
				sb.append(members.get(i).getKey());
				if(i != members.size() -1){
					sb.append(",");
				}
			}
			sb.append("]");
			return sb.toString();
		}
	}
	
	// Implements K-means clustering
	public static void kMeansClustering(int k,List<F32FloatTensor> dataset){
		Cluster[] clusters = new Cluster[k];
		Collections.shuffle(dataset);
		for(int i = 0;i<k;i++){
			F32FloatTensor randomVector = dataset.get(i);
			clusters[i] = new Cluster(randomVector);
			clusters[i].addVector(randomVector);
		}		
		DistanceMeasure dm = new EuclideanDistance();
		boolean clusteringStable = false;
		String previousClustering = "";
		while(!clusteringStable){
			for(Cluster c : clusters){
				c.determineNewSeed();
				c.members.clear();
			}
			for(F32FloatTensor v : dataset){
				double smallestDistance = Double.MAX_VALUE;
				Cluster closesCluster = null;
				for(Cluster c : clusters){
					double distance = dm.distance(v, c.getSeed()); 
					if(distance<smallestDistance){
						closesCluster = c;
						smallestDistance = distance;
					}
				}
				closesCluster.addVector(v);
			}
			
			StringBuilder newClustering = new StringBuilder();
			for(Cluster c : clusters){
				newClustering.append(c.toString());
			}
			clusteringStable = previousClustering.equals(newClustering.toString());
			previousClustering = newClustering.toString();
		}
		for(Cluster c : clusters){
			System.out.println(c.toString());
		}
	}
	
	public static void nearestNeighborClustering(double threshold,List<F32FloatTensor> dataset){
		//Nearest neighbor clustering
		List<Cluster> clusters = new ArrayList<Clustering.Cluster>();
		DistanceMeasure dm = new EuclideanDistance();
		for(F32FloatTensor v : dataset){
			double smallestDistance = Double.MAX_VALUE;
			Cluster closestCluster = null;
			for(Cluster c: clusters){
				for(F32FloatTensor w : c.members){
					double distance = dm.distance(w,v); 
					if(distance<smallestDistance){
						closestCluster = c;
						smallestDistance = distance;
					}
				}
			}
			if(smallestDistance<threshold){
				closestCluster.addVector(v);
			}else{
				closestCluster = new Cluster(v);
				closestCluster.addVector(v);
				clusters.add(closestCluster);
			}
		}
		for(Cluster c : clusters){
			System.out.println(c.toString());
		}
	}
	
	public static void main(String...strings){
		F32FloatTensor[] a = new F32FloatTensor[8];
		List<F32FloatTensor> dataset = new ArrayList<F32FloatTensor>();
		for(int i=0;i<a.length;i++){
			a[i] = new F32FloatTensor(2);
			dataset.add(a[i]);
		}
		//see http://webdocs.cs.ualberta.ca/~zaiane/courses/cmput695/F07/exercises/Exercises695Clus-solution.pdf
		a[0].setKey("a1");
		a[0].setFloat(0, 2);
		a[0].setFloat(1, 10);
		a[1].setKey("a2");
		a[1].setFloat(0, 2);
		a[1].setFloat(1, 5);
		a[2].setKey("a3");
		a[2].setFloat(0, 8);
		a[2].setFloat(1, 4);
		a[3].setKey("a4");
		a[3].setFloat(0, 5);
		a[3].setFloat(1, 8);
		a[4].setKey("a5");
		a[4].setFloat(0, 7);
		a[4].setFloat(1, 5);
		a[5].setKey("a6");
		a[5].setFloat(0, 6);
		a[5].setFloat(1, 4);
		a[6].setKey("a7");
		a[6].setFloat(0, 1);
		a[6].setFloat(1, 2);
		a[7].setKey("a8");
		a[7].setFloat(0, 4);
		a[7].setFloat(1, 9);
		

		System.out.println("\nK-means clustering");
		kMeansClustering(3, dataset);
		
		System.out.println("\nNearest neighbor clustering");
		nearestNeighborClustering(4, dataset);
		
		//nearestNeighborClustering(1000, LSH.readDataset("output_dataset.txt", Integer.MAX_VALUE));
	}

}
