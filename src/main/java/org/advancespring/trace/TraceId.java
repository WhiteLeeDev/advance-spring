package org.advancespring.trace;

import java.util.UUID;

public class TraceId {
    private final int depth;
    private final String id;

    public TraceId(){
        this.depth = 0;
        this.id = createId();
    }

    private TraceId(String id, int depth) {
        this.id = id;
        this.depth = depth;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public int getDepth() {
        return this.depth;
    }

    public String getId() {
        return this.id;
    }

    public TraceId createNext() {
        return new TraceId(this.id,this.depth + 1);
    }

    public TraceId createPrevious() {
        return new TraceId(this.id,this.depth -1);
    }
}
