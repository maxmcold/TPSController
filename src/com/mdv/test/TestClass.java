package com.mdv.test;

import com.mdv.io.FileQueue;

import java.io.IOException;

public class TestClass {

    public static void main(String[] args){
        FileQueue fq = new FileQueue();
        try {
            fq.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
