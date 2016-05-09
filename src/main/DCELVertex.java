package main;

import java.util.ArrayList;

public class DCELVertex {

    private int id;
    private ArrayList<DCELHalfEdge> halfEdgeArray;

    public DCELVertex(ArrayList<DCELHalfEdge> halfEdgeArrayList) {
        this.halfEdgeArray = halfEdgeArrayList;
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
}
