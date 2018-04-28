package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.throttle.Controller;
import com.mdv.throttle.Speedmeter;

import java.io.IOException;

public class TPSControllerTest {

    public TPSControllerTest() {

    }

    public static void main(String args[]){


        Speedmeter sm = new Speedmeter();
        sm.start();

        BulkPublisher bp = new BulkPublisher(10);
        bp.setDaemon(false);
        bp.start();

        BulkSubscriber bs = new BulkSubscriber(10);
        bs.start();
        Controller c = new Controller(sm,bp);
        c.start();




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
