package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.throttle.BulkPublisher;
import com.mdv.throttle.Controller;
import com.mdv.throttle.Speedmeter;

import java.io.IOException;

public class TPSControllerTest {

    public TPSControllerTest() {

    }

    public static void main(String args[]){


        Speedmeter sm = new Speedmeter();
        sm.start();

        BulkPublisher bp = new BulkPublisher(1);
        bp.setDaemon(false);
        bp.start();

        BulkSubscriber bs = new BulkSubscriber(1);
        bs.start();
        Controller c = new Controller(sm,bp,new FileQueue());
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
