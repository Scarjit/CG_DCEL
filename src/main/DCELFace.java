package main;

import java.util.ArrayList;

public class DCELFace {

    private int id;
    private ArrayList<DCELHalfEdge> halfEdgeArray;

    public DCELFace(ArrayList<DCELHalfEdge> halfEdgeArray) {
        this.halfEdgeArray = halfEdgeArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<DCELHalfEdge> getHalfEdgeArray() {
        return halfEdgeArray;
    }

    public void setHalfEdgeArray(ArrayList<DCELHalfEdge> halfEdgeArray) {
        this.halfEdgeArray = halfEdgeArray;
    }

    public void addHalfEdgeToFace(DCELHalfEdge halfEdge) {
        halfEdgeArray.add(halfEdge);
    }

    public void removeHalfEdgeFromFace(DCELHalfEdge halfEdge) {
        halfEdgeArray.remove(halfEdge);
    }
}
