package main;

import java.util.ArrayList;

public class DCELFace {

    private int id;
    private ArrayList<DCELVertex> vertex;

    public DCELFace(ArrayList<DCELVertex> halfEdgeArray) {
        this.vertex = halfEdgeArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<DCELVertex> getvertex() {
        return vertex;
    }

    public void setvertex(ArrayList<DCELVertex> vertex) {
        this.vertex = vertex;
    }

    public void addvertex(DCELVertex vertex) {
        this.vertex.add(vertex);
    }

    public void removevertex(DCELHalfEdge vertex) {
        this.vertex.remove(vertex);
    }
}
