package main;

public class DCELHalfEdge {

    private int id;
    private DCELHalfEdge prev;
    private DCELHalfEdge next;
    private DCELHalfEdge twin;
    private DCELVertex origin;
    private DCELFace face;

    public DCELHalfEdge(DCELHalfEdge prev, DCELHalfEdge next, DCELHalfEdge twin, DCELVertex origin, DCELFace face) {
        this.prev = prev;
        this.next = next;
        this.twin = twin;
        this.origin = origin;
        this.face = face;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DCELHalfEdge getPrev() {
        return prev;
    }

    public void setPrev(DCELHalfEdge prev) {
        this.prev = prev;
    }

    public DCELHalfEdge getNext() {
        return next;
    }

    public void setNext(DCELHalfEdge next) {
        this.next = next;
    }

    public DCELHalfEdge getTwin() {
        return twin;
    }

    public void setTwin(DCELHalfEdge twin) {
        this.twin = twin;
    }

    public DCELVertex getOrigin() {
        return origin;
    }

    public void setOrigin(DCELVertex origin) {
        this.origin = origin;
    }

    public DCELFace getFace() {
        return face;
    }

    public void setFace(DCELFace face) {
        this.face = face;
    }
}
