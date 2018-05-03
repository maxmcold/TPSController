package com.mdv.throttle;

import com.mdv.test.Publisher;

import java.util.Stack;

public interface Controllable {
    boolean increase();
    boolean decrease();
    void setRule(RuleEngine re);
    Stack<Producer> getProducers();
}
