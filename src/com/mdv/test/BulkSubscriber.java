package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.logging.Logger;
import com.mdv.throttle.Controllable;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class BulkSubscriber extends Thread {
    ArrayList<Subscriber> sal = new ArrayList();
    FileQueue fq = new FileQueue();
    int bulkLimit = 10; //default
    public BulkSubscriber(int bulkLimit){
        this.setDaemon(true);
        this.bulkLimit = bulkLimit;
    }

    @Override
    public void start(){
        for (int i = 0; i < this.bulkLimit; i++) {
            sal.add(new Subscriber("sub-" + i,fq));
            sal.get(i).start();

        }


    }


}
