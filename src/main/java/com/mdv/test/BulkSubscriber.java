package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.io.Queue;

import java.util.ArrayList;

public class BulkSubscriber extends Thread {
    ArrayList<Subscriber> sal = new ArrayList();
    //FileQueue fq = new FileQueue();
    private Queue queue;
    int bulkLimit = 10; //default
    public BulkSubscriber(int bulkLimit, Queue q){
        this.setDaemon(true);
        this.bulkLimit = bulkLimit;
        this.queue = q;
    }

    @Override
    public void start(){
        for (int i = 0; i < this.bulkLimit; i++) {
            sal.add(new Subscriber("sub-" + i,queue));
            sal.get(i).start();

        }


    }


}
