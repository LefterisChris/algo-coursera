/**
 * Name: Lefteris Christoforidis
 * 
 * Description: A double-ended queue or deque (pronounced “deck”) 
 * is a generalization of a stack and a queue that supports adding 
 * and removing items from either the front or the back of the data structure.
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> head; // beginning of deque
    private Node<Item> tail; // end of deque
    private int n;           // number of elements in deque

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    /**
     *  Initializes an empty deque
     */
    public Deque() {
        head = null;
        tail = null;
        n = 0;
    }


    /**
     *  Checks if the deque is empty
     */
    public boolean isEmpty() {
        return (n==0);
    }

    /** 
     * Returns size of the deque
    */
    public int size() {
        return n;
    }

    /** 
     * Adds an Item in the beginning of the deque
    */
    public void addFirst(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Tried to add a null value.");
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.next = null;
        newnode.prev = null;
        if (isEmpty()) {
            head = newnode;
            tail = newnode;
        } else {
            newnode.next = head;
            head.prev = newnode;
            head = newnode;
        }
        n++;
    }

    /** 
     * Adds an Item in the end of the deque
    */
    public void addLast(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Tried to add a null value.");
        Node<Item> newnode = new Node<Item>();
        newnode.item = item;
        newnode.next = null;
        newnode.prev = null;
        if (isEmpty()) {
            head = newnode;
            tail = newnode;
        } else {
            tail.next = newnode;
            newnode.prev = tail;
            tail = newnode;
        }
        n++;
    }

    /** 
     * Removes the first item of the deque,
     * if it exists
    */
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Tried to remove from empty queue");
        
        Item item = head.item;
        if (n == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        n--;
        return item;
    }

    /** 
     * Removes the last item of the deque,
     * if it exists
    */
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Tried to remove from empty queue");
        
        Item item = tail.item;
        if (n == 1) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item>(head);
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> head) {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> mydeque = new Deque<Integer>();
        mydeque.addFirst(1);
        mydeque.addFirst(2);
        mydeque.addLast(3);
        for(Integer i : mydeque)
            StdOut.print(i+" ");
        StdOut.println();
        
        mydeque.addLast(4);
        mydeque.removeFirst();
        mydeque.removeLast();
        for(Integer i : mydeque)
            StdOut.print(i+" ");
        
        StdOut.println();
        mydeque.removeFirst();
        mydeque.removeLast();

        for(Integer i : mydeque)
            StdOut.println(i);

    }
}