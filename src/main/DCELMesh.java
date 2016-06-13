package main;

import java.util.ArrayList;
import java.util.List;


public class DCELMesh {

	private List<DCELVertex> vertices;
	private List<DCELHalfEdge> edges;
	private List<DCELFace> faces;
	private List<Integer> indicesForEdges;


	public DCELMesh() {
		vertices = new ArrayList<DCELVertex>();
		edges = new ArrayList<DCELHalfEdge>();
		faces = new ArrayList<DCELFace>();
	}

	public List<DCELVertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<DCELVertex> vertices) {
		this.vertices = vertices;
	}

	public List<DCELHalfEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<DCELHalfEdge> edges) {
		this.edges = edges;
	}

	public List<DCELFace> getFaces() {
		return faces;
	}

	public void setFaces(List<DCELFace> faces) {
		this.faces = faces;
	}

	public List<Integer> getIndicesForEdges() {
		return indicesForEdges;
	}

	public void setIndicesForEdges(List<Integer> indicesForEdges) {
		this.indicesForEdges = indicesForEdges;
	}

	public DCELMesh(String Name){
		System.out.println("Creating new Mesh: "+Name);
	}


}
