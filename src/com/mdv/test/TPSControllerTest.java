package com.mdv.test;

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

        BulkPublisher bp = new BulkPublisher(5);
        bp.setDaemon(false);

        bp.start();



        BulkSubscriber bs = new BulkSubscriber(5);
        bs.start();
        Controller c = new Controller(sm,bp);
        c.start();




    }




}
