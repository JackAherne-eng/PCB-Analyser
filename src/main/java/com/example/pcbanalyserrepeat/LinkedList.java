package com.example.pcbanalyserrepeat;

import java.io.Serializable;
import java.util.function.Predicate;

public class LinkedList<O> implements Serializable{
    public Node<O> head = null;



    public void addElement(O element){
        Node<O> node = new Node<>();
        node.setContents(element);
        node.next = head;
        head = node;
    }

    public int size(){
        Node<O> temp = head;
        int counter = 0;
        while(temp != null){
            temp = temp.next;
            counter++;
        }
        return counter;
    }

    public O get(int index){
        Node<O> temp = head;
        for (int i = 0; i < index; i++){
            temp = temp.next;
        }
        return temp.getContents();
    }

    public void remove(int index){
        index = size() - index - 1;

        if (index == 0){
            head = head.next;
        }
        else {
            Node<O> currentNode = head;
            for (int i = 0; i < index - 1; i++){
                currentNode = currentNode.next;
            }
            currentNode.next = currentNode.next.next;
        }
    }

    public O search(Predicate<O> p){
        Node<O> temp = head;

        while(temp != null && !p.test((temp.getContents()))){
            temp = temp.next;
        }
        return temp != null ? temp.getContents() : null;
    }

    public void clear(){
        head = null;
    }


    private class Node<O> implements Serializable {
        public Node<O> next = null;
        private O contents;

        public O getContents(){
            return contents;
        }

        public void setContents(O c){
            contents = c;
        }
    }

}