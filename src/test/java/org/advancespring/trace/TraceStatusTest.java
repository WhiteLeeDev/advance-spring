package org.advancespring.trace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TraceStatusTest {

    @Test
    @DisplayName("[positive] traceId, 시작시간, message 로 초기화 한다")
    void test1(){
        TraceId id = new TraceId();
        Long startMs = System.currentTimeMillis();
        TraceStatus traceStatus = new TraceStatus(id,startMs,"객체생성테스트");

        assertThat(traceStatus.getTraceId().getId()).hasSize(8);
        assertThat(traceStatus.getStartMs()).isPositive();
        assertThat(traceStatus.getMessage()).isEqualTo("객체생성테스트");
    }
}