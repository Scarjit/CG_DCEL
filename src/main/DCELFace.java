package main;

import java.util.ArrayList;

public class DCELFace {

    private int id;
    private DCELHalfEdge halfEdge;

    public DCELFace(DCELHalfEdge halfEdge) {
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

}
