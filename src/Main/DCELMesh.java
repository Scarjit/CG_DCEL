package main;

import java.util.ArrayList;


public class DCELMesh {
	private ArrayList<DCELVertex> VertexArray;

	public DCELMesh(String Name){
		System.out.println("Creating new Mesh: "+Name);
	}

	public void addVertex(DCELVertex vertex){
		VertexArray.add(vertex);
	}

	public void removeVertex(DCELVertex vertex){
		VertexArray.remove(vertex);
	}

	public ArrayList<DCELVertex> getVertexArray(){
		return VertexArray;
	}
}
