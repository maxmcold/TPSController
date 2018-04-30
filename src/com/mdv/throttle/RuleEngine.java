package com.mdv.throttle;

import com.mdv.io.Queue;

public interface RuleEngine {

    int applyRule(Speedmeter sm, Queue q);
}
