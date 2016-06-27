package main;

public class DCELFace {

    private int id;
    private DCELHalfEdge halfEdge;
    private float[] faceNormal = new float[3];

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

    public float[] getFaceNormal() {
        return faceNormal;
    }

    public void setFaceNormal(float[] faceNormal) {
        this.faceNormal = faceNormal;
    }

}
