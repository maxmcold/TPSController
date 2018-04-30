package com.mdv.throttle;

import com.mdv.io.FileQueue;
import com.mdv.logging.Logger;
import com.mdv.throttle.Configuration;
import com.mdv.throttle.Controllable;
import com.mdv.throttle.Publisher;
import com.mdv.throttle.RuleEngine;

import java.util.Stack;
import java.util.StringTokenizer;

public class BulkPublisher extends Thread implements Controllable {

    Logger logger = new Logger();
    private static Stack<Publisher> publishers = new Stack();
    private FileQueue fq;
    int bulkInit = 10; //default
    RuleEngine rule;
    public BulkPublisher(int bi){
        this.setDaemon(true);
        this.bulkInit = bi;

        //set the default rule from configuration
        try {
            Class<?> cls = Class.forName(Configuration.RULE);
            this.rule = (RuleEngine)cls.newInstance();


        } catch (ClassNotFoundException e) {
           logger.log(e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(){
        this.fq = new FileQueue();
        for (int i = 0; i < this.bulkInit; i++) {

            Publisher p = new Publisher("pub-"+i,fq);
            publishers.push(p);
            p.start();

        }


    }

    @Override
    public boolean increase(){
        this.addPublisher();
        if(Configuration.PUBLISH_INTERVAL_MILLISEC > 100)
        Configuration.PUBLISH_INTERVAL_MILLISEC -= 100;
        logger.log("Decreased publish interval by 100 ms, current: "+ Configuration.PUBLISH_INTERVAL_MILLISEC);
        return true;
    }

    @Override
    public boolean decrease() {
        this.popPublisher();
        Configuration.PUBLISH_INTERVAL_MILLISEC += 100;
        logger.log("Increased publish interval by 100 ms, current" + Configuration.PUBLISH_INTERVAL_MILLISEC);
        return true;
    }



    public boolean popPublisher(){
        if (publishers.empty()) return false;
        Publisher p = publishers.peek();
        p.stop();
        publishers.pop();
        return true;
    }
    public Stack<Publisher> getPublishers(){
        return publishers;
    }

    public boolean addPublisher(){

        if (null == this.fq) this.fq = new FileQueue();
        //gets the last added index in the stack
        Publisher p = (publishers.empty()) ? new Publisher("pub-1",fq) : publishers.peek();

        //Evaluate the last index in the thread to create new one
        int nextToken;
        String tname = p.getName();
        StringTokenizer st = new StringTokenizer(tname, "-");
        if (st.nextToken() != null){
            String s = st.nextToken();
            if (s != null) {
                int lastToken = Integer.parseInt(s);
                nextToken = ++lastToken;
                Publisher publisher = new Publisher("pub-"+nextToken,fq);
                publishers.push(publisher);
                p.start();


                logger.log("adding thread pub-"+nextToken);
                logger.log("Publisher Stack size: "+publishers.size());


            }
        } else { //if you cannot get the token name just do not do anything
            //TODO: improve this threadname management. Like this is shit
            return false;
        }
        return true;




    }
    public void setRule(RuleEngine ruleEngine){
        this.rule = ruleEngine;

    }
}
