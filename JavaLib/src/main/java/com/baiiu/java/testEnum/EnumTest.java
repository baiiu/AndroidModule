package com.baiiu.java.testEnum;

/**
 * Created by zhuzhe.zz on 2024/6/28
 *
 * @author zhuzhe.zz@bytedance.com
 */
public class EnumTest {
    public State state = State.ONE;
    public State2 state2 = State2.ONE2;
    public State3 state3 = State3.ONE3;

    public enum State {
        ONE, TWO, Three, Four, Five
    }

    public enum State2 {
        ONE2, TWO2, Three2, Four2, Five2
    }

    public enum State3 {
        ONE3, TWO3, Three3, Four3, Five3
    }

    @Override
    public String toString() {
        return "EnumTest{" +
                "state=" + state +
                ", state2=" + state2 +
                ", state3=" + state3 +
                '}';
    }
}
