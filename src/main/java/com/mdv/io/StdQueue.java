package com.mdv.io;

import com.mdv.throttle.Configuration;

import java.io.IOException;
import java.util.LinkedList;

public class StdQueue extends LinkedList<String> implements com.mdv.io.Queue {

    private static StdQueue sq;
    protected StdQueue(){

    }
    public static StdQueue getInstance(){

        if (null == sq) sq = new StdQueue();
        return sq;
    }

    public void popMessage() throws IOException {
        this.removeLast();
    }

    public void getMessage() throws IOException {
        this.removeFirst();

    }

    public void pushMessage(String message) throws IOException {
        this.push(message);

    }

    public void clean() throws IOException {
        this.clear();

    }

    public int getLimit() {
        return Configuration.QUEUE_LIMIT;
    }

    public int getCurrentSize() {
        return this.size();
    }
}
