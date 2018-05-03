package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.io.StdQueue;
import com.mdv.throttle.Controller;
import com.mdv.throttle.Speedmeter;

import java.io.IOException;

public class TPSControllerTest {

    public TPSControllerTest() {

    }

    public static void main(String args[]){

        StdQueue queue = StdQueue.getInstance();

        Speedmeter sm = new Speedmeter(queue);
        sm.start();

        com.mdv.test.BulkPublisher bp = new com.mdv.test.BulkPublisher(10,queue);
        bp.setDaemon(false);
        bp.start();

        BulkSubscriber bs = new com.mdv.test.BulkSubscriber(10,queue);
        bs.start();
        Controller c = new Controller(sm,bp,queue);
        Thread t = new Thread(c);
        t.setDaemon(false);
        t.start();




    }

    //tightly related to implementation
    private static void cleanQueue(){
        FileQueue fq = new FileQueue();
        try {
            fq.clean();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
