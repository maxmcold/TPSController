package com.cvas.test;

import com.cvas.BulkPublisher;
import com.cvas.Controller;
import com.cvas.Speedmeter;

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
