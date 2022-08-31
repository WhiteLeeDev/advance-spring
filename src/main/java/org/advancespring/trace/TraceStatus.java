package org.advancespring.trace;

public class TraceStatus {
    private final TraceId traceId;
    private final Long startMs;
    private final String message;

    public TraceStatus(TraceId traceId, Long startMs, String message) {
        this.traceId = traceId;
        this.startMs = startMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartMs() {
        return startMs;
    }

    public String getMessage() {

        return message;
    }
}
