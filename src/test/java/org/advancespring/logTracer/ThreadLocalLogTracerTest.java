package org.advancespring.logTracer;

import org.advancespring.trace.TraceId;
import org.advancespring.trace.TraceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.util.ReflectionTestUtils.getField;

class ThreadLocalLogTracerTest {

    private LogTracer logTracer = new ThreadLocalLogTracer();

    @Test
    @DisplayName("[positive] begin을 하면 traceStatus가 생성되고 TraceId가 ThreadLocal에 저장됨")
    void test1(){
        TraceStatus log_begin = logTracer.begin("log begin");

        ThreadLocal<TraceId> traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");

        assertThat(log_begin.getTraceId()).isEqualTo(traceIdHolder.get());
    }

    @Test
    @DisplayName("[positive] begin을 할 때 마다 TraceId가 증가되어 ThreadLocal에 저장됨")
    void test2(){
        TraceStatus beginStatus = logTracer.begin("parent method");
        ThreadLocal<TraceId> traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");
        assertThat(beginStatus.getTraceId()).isEqualTo(traceIdHolder.get());
        assertThat(beginStatus.getTraceId().getDepth()).isEqualTo(0);

        TraceStatus nextStatus= logTracer.begin("child method");
        traceIdHolder = (ThreadLocal<TraceId>) getField(logTracer, "traceIdHolder");
        assertThat(nextStatus.getTraceId()).isEqualTo(traceIdHolder.get());
        assertThat(nextStatus.getTraceId().getId()).isEqualTo(beginStatus.getTraceId().getId());
        assertThat(nextStatus.getTraceId().getDepth()).isEqualTo(1);
    }
}