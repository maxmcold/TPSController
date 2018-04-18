package com.mdv.throttle;



import java.util.ArrayList;

public class BulkPublisher extends Thread {

    ArrayList<Publisher> sal = new ArrayList();
    int bulkLimit = 10; //default
    public BulkPublisher(int bulkLimit){
        this.setDaemon(true);
        this.bulkLimit = bulkLimit;
    }

    @Override
    public void start(){
        for (int i = 0; i < this.bulkLimit; i++) {
            sal.add(new Publisher("pub-" + i));
            sal.get(i).start();

        }


    }

}
