package com.cvas.test;

public class TestMain {

    public static void main(String[] args) {


        Node a_node = new Node("a",true);

        Node b_node = a_node.insertLeft("b");

        Node c_node = a_node.insertRight("c");

        Node d_node = b_node.insertRight("d");

        Node e_node = c_node.insertLeft("e");
        Node f_node = a_node.insertRight("f");

        System.out.println(a_node.value);







        /**Speedmeter sm = new Speedmeter();

        sm.start();

        Controller c = new Controller(sm);
        c.start();
        BulkPublisher bp = new BulkPublisher(20);
        bp.setDaemon(false);
        bp.start();
        BulkSubscriber bs = new BulkSubscriber(5);
        bs.start();

        //m.read();
        //*/
        //insertsortdes();
        //TestMain tmm = new TestMain();

        //tmm.testshortc(null);
    }



    public static int getMessageLength(String message, boolean hasUnicode) {


        System.out.println("Message Length: "+message.length());
        int messageLength;
        Boolean test = (message.length() <= 70);
        int test1 = message.length() / 67;
        if (message == null) {
            throw new NullPointerException("Text message found to be null, thus throwing NPE.");
        } else if (hasUnicode) {
            messageLength = (message.length() <= 70) ? 1 : (message.length() / 67);
        } else {
            messageLength = (message.length() <= 160) ? 1 : (message.length() / 153);
        }
        return messageLength;
    }


    public void testshortc(Object o){

        if ((o != null) && (o.hashCode() != 1)){
            System.out.println(o.toString());
        }
    }




}

