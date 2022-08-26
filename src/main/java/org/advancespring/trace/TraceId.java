package org.advancespring.trace;

public class TraceId {
    public int getLevel() {
        return 0;
    }

    public String getId() {
        return "";
    }

    public TraceId createNextId() {
        return null;
    }

    public TraceId createPreviousId() {
        return null;
    }
}
