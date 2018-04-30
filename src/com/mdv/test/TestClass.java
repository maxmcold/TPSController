package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.throttle.BulkPublisher;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args){
        FileQueue fq = new FileQueue();
        try {
            fq.getMessage();
            fq.getCurrentSize();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BulkPublisher bp = new BulkPublisher(1);
        bp.increase();




    }
}
