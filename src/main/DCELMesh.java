package main;

import java.util.ArrayList;
import java.util.List;


public class DCELMesh {

	private List<DCELHalfEdge> edges;
	private List<DCELFace> faces;

	public DCELMesh() {
		edges = new ArrayList<DCELHalfEdge>();
		faces = new ArrayList<DCELFace>();
	}


	public DCELMesh(String Name){
		System.out.println("Creating new Mesh: "+Name);
	}


}
