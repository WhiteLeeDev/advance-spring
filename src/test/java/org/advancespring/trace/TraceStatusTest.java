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

    @Test
    @DisplayName("[positive] trace의 depth가 0일 때, message가 원본과 동일함")
    void test2() {
        TraceId id = new TraceId();
        TraceStatus status = new TraceStatus(id, System.currentTimeMillis(), "원본");

        assertThat(status.getMessage()).isEqualTo("원본");
    }

    @Test
    @DisplayName("[positive] trace의 depth가 1일 때, message가 한단계 들여쓰기 됨")
    void test3() {
        TraceId id = new TraceId();
        TraceId next = id.createNext();
        TraceStatus status = new TraceStatus(next, System.currentTimeMillis(), "원본");

        assertThat(status.getMessage()).hasSize("원본".length()+4);
        //Trace가 begin일 때와 complete일 때 찍히는 로그 형식이 다름
        //이걸 어떻게 간단하게 처리할까?
    }
}