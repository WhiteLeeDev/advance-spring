package org.advancespring.trace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TraceIdTest {
    @Test
    @DisplayName("[positive] 기본 객체를 생성하면 level=0, 8자리 UUID를 갖는다")
    void test1(){
        TraceId id = new TraceId();

        assertThat(id.getLevel()).isEqualTo(0);
        assertThat(id.getId()).hasSize(8);
    }

    @Test
    @DisplayName("[positive] 한 뎁스 깊은 객체를 생성하면 level이 1올라가고, 8자리 UUID를 갖는다")
    void test2(){
        TraceId levelZeroTrace = new TraceId();
        TraceId levelOneTrace = levelZeroTrace.createNextId();

        assertThat(levelOneTrace.getLevel()).isEqualTo(levelZeroTrace.getLevel()+1);
        assertThat(levelOneTrace.getId()).hasSize(8);
        assertThat(levelOneTrace.getId()).isEqualTo(levelZeroTrace.getId());
    }

    @Test
    @DisplayName("[positive] 한 뎁스 위의 객체를 생성하면 level이 1 감소하고, 8자리 UUID를 갖는다")
    void test3(){
        TraceId levelZeroTrace = new TraceId();
        TraceId levelMinusTrace = levelZeroTrace.createPreviousId();

        assertThat(levelMinusTrace.getLevel()).isEqualTo(levelZeroTrace.getLevel()-1);
        assertThat(levelMinusTrace.getId()).hasSize(8);
        assertThat(levelMinusTrace.getId()).isEqualTo(levelZeroTrace.getId());
    }
}