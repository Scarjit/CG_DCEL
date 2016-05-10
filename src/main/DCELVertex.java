package main;

import java.util.ArrayList;
import java.util.Map;


public class DCELVertex {

	private double[] position = new double[3];
	private Map<String, Object> customdata;

	private int id;
	private ArrayList<DCELHalfEdge> halfEdgeArray;

	public DCELVertex(double[] position, Map<String, Object> customdata) {
		this.position = position;
		if (customdata != null) {
			this.customdata = customdata;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<DCELHalfEdge> getHalfEdgeArrayList() {
		return halfEdgeArray;
	}

	public void setHalfEdgeArrayList(ArrayList<DCELHalfEdge> halfEdgeArray) {
		this.halfEdgeArray = halfEdgeArray;
	}

	public void addHalfEdgeToVertex(DCELHalfEdge halfEdge) {
		halfEdgeArray.add(halfEdge);
	}

	public void removeHalfEdgeFromVertex(DCELHalfEdge halfEdge) {
		halfEdgeArray.remove(halfEdge);
	}

	public double[] getPosition() {
		return position;
	}

	public void setPosition(double[] position) {
		this.position = position;
	}

	public Map<String, Object> getCustomdata() {
		return customdata;
	}

	public void setCustomdata(Map<String, Object> customdata) {
		this.customdata = customdata;
	}
}
