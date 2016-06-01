package main;

import java.util.ArrayList;
import java.util.Map;


public class DCELVertex {

	private int[] position = new int[3];
	private Map<String, Object> customdata;

	private int id;
	private DCELHalfEdge halfEdge;

	public DCELVertex(int[] position, Map<String, Object> customdata, DCELHalfEdge halfEdge) {
		this.position = position;
		if (customdata != null) {
			this.customdata = customdata;
		}
		this.halfEdge = halfEdge;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DCELHalfEdge getHalfEdge() {
		return halfEdge;
	}

	public void setHalfEdge(DCELHalfEdge halfEdge) {
		this.halfEdge = halfEdge;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public Map<String, Object> getCustomdata() {
		return customdata;
	}

	public void setCustomdata(Map<String, Object> customdata) {
		this.customdata = customdata;
	}
}
