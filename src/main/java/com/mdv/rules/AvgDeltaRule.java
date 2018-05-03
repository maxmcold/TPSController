package com.mdv.rules;

import com.mdv.io.Queue;
import com.mdv.throttle.RuleEngine;
import com.mdv.throttle.Speedmeter;

public class AvgDeltaRule implements RuleEngine {

    public int applyRule(Speedmeter sm, Queue q){
        return 0;
    }

}
