package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class DCELVertex {

	private float[] position = new float[3];
	private ArrayList<float[]> faceNormals = new ArrayList<float[]>();
	private Map<String, Object> customData;

	private int id;
	private DCELHalfEdge halfEdge;

	public DCELVertex(float[] position, Map<String, Object> customdata, DCELHalfEdge halfEdge) {
		this.position = position;
		if (customdata != null) {
			this.customData = customdata;
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

	public float[] getPosition() {
		return position;
	}

	public void setPosition(float[] position) {
		this.position = position;
	}

	public Map<String, Object> getCustomdata() {
		return customData;
	}

	public void setCustomdata(Map<String, Object> customData) {
		this.customData = customData;
	}

	public ArrayList<float[]> getFaceNormals() {
		return faceNormals;
	}

	public void setFaceNormals(ArrayList<float[]> faceNormals) {
		this.faceNormals = faceNormals;
	}

	public void addFaceNormal(float[] faceNormal) {
		this.faceNormals.add(faceNormal);
	}

	@Override
	public String toString() {

		String faceNormalsString = "";

		for (float[] faceNormal : faceNormals) {
			faceNormalsString += Arrays.toString(faceNormal) + "\r\n";
		}

		return "DCELVertex{" +
				"position=" + Arrays.toString(position) +
				"faceNormals" + faceNormalsString +
				"}\r\n";
	}
}
