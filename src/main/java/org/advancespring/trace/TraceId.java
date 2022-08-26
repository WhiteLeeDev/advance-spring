package org.advancespring.trace;

import java.util.UUID;

public class TraceId {
    private final int level;
    private final String id;

    public TraceId(){
        this.level = 0;
        this.id = createId();
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public int getLevel() {
        return this.level;
    }

    public String getId() {
        return this.id;
    }

    public TraceId createNextId() {
        return new TraceId(this.id,this.level + 1);
    }

    public TraceId createPreviousId() {
        return new TraceId(this.id,this.level -1);
    }
}
