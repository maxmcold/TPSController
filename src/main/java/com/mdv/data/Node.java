package com.mdv.data;

public class Node {

    public Node left_child = null;
    public Node right_child = null;
    public String value = null;
    public boolean is_root = false;
    public Node parent = null;


    public Node(String v, boolean ir) {
        try {
            this.createNode(v,ir);
        } catch (ParentConflictException e) {
            e.printStackTrace();
        }


    }

    private void createNode(String v, boolean ir) throws ParentConflictException{
        this.value = v;
        // this is subject to client control
        if (null == this.parent && ir)
            this.is_root = ir;
        else
            throw new ParentConflictException();


    }

    public Node insertRight(String value){

        if (null == this.right_child){

            this.right_child = new Node(value,false);


        }
        else{

            this.right_child.insertRight(value);
            //this.right_child = newNode;

        }
        return this.right_child;
    }
    public Node insertLeft(String value){

        if (null == this.left_child){

            this.left_child = new Node(value,false);


        }
        else{
            this.left_child.insertLeft(value);

        }
        return this.left_child;
    }

}

