package com.mdv.test;

import com.mdv.io.FileQueue;
import com.mdv.io.StdQueue;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args){
        StdQueue fq = StdQueue.getInstance();



        try {
            fq.pushMessage("Test message");
            fq.getCurrentSize();
            fq.popMessage();
            fq.getCurrentSize();
            fq.popMessage();

        } catch (IOException e) {
            e.printStackTrace();
        }






    }
}
