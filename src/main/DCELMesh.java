package main;

import java.util.ArrayList;


public class DCELMesh {
	private ArrayList<DCELVertex> vertexArray;

	public DCELMesh(String Name){
		System.out.println("Creating new Mesh: "+Name);
	}

	public void addVertex(DCELVertex vertex){
		vertexArray.add(vertex);
	}

	public void removeVertex(DCELVertex vertex){
		vertexArray.remove(vertex);
	}

	public ArrayList<DCELVertex> getVertexArray(){
		return vertexArray;
	}
}
