package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.throttle.Controller;
import com.mdv.throttle.Speedmeter;

import java.io.IOException;

public class TPSControllerTest {

    public TPSControllerTest() {

    }

    public static void main(String args[]){
        FileQueue fq = new FileQueue();


        Speedmeter sm = new Speedmeter(fq);
        sm.start();

        BulkPublisher bp = new BulkPublisher(1);
        bp.setDaemon(false);
        bp.start();

        BulkSubscriber bs = new BulkSubscriber(1);
        bs.start();
        Controller c = new Controller(sm,bp,fq);
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
