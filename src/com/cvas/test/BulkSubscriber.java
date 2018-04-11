package com.cvas.test;

import java.util.ArrayList;

public class BulkSubscriber extends Thread {
    ArrayList<Subscriber> sal = new ArrayList();
    int bulkLimit = 10; //default
    public BulkSubscriber(int bulkLimit){
        this.setDaemon(true);
        this.bulkLimit = bulkLimit;
    }

    @Override
    public void start(){
        for (int i = 0; i < this.bulkLimit; i++) {
            sal.add(new Subscriber("sub-" + i));
            sal.get(i).start();

        }


    }

}
