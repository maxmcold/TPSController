package com.mdv.test;

import com.mdv.throttle.BulkPublisher;
import com.mdv.throttle.Controller;
import com.mdv.throttle.Speedmeter;

public class TPSControllerTest {

    public TPSControllerTest() {

    }

    public static void main(String args[]){

        Speedmeter sm = new Speedmeter();

        sm.start();

        Controller c = new Controller(sm);
        c.start();
        BulkPublisher bp = new BulkPublisher(5);
        bp.setDaemon(false);
        bp.start();
        //BulkSubscriber bs = new BulkSubscriber(5);
        //bs.start();



    }




}
