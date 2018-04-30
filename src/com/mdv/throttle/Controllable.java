package com.mdv.throttle;

import java.util.Stack;

public interface Controllable {
    boolean increase();
    boolean decrease();
    void setRule(RuleEngine re);
    Stack<Publisher> getPublishers();
}
