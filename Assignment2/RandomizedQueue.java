/**
 * Name: Lefteris Christoforidis
 * 
 * Description: A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random from items in the data structure
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q_arr;
    private int n;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q_arr = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {
        return (n==0);
    }
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q_arr[i];
        }
        q_arr = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new java.lang.IllegalArgumentException("Tried to add a null value.");
        
        if (n == q_arr.length)
            resize(2*q_arr.length);
        
        q_arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Tried to remove from empty queue");
        
        int idx_to_del = StdRandom.uniform(0, n);
        
        Item item = q_arr[idx_to_del];
        q_arr[idx_to_del] = q_arr[n-1];
        q_arr[--n] = null; // avoid loitering

        // shrink size of array if necessary
        if (n > 0 && n == q_arr.length/4) 
            resize(q_arr.length/2); 
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Tried to sample from empty queue");

        return q_arr[StdRandom.uniform(0, n)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    private class RandQueueIterator implements Iterator<Item> {
        private int idx;
        private int[] seq;

        public RandQueueIterator() {
            idx = 0;
            seq = StdRandom.permutation(n);
        }

        public boolean hasNext() { 
            return idx < n;
        }     
        public void remove() { 
            throw new UnsupportedOperationException();  
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = q_arr[seq[idx++]];
            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> randQ = new RandomizedQueue<Integer>();
        for (int i = 0; i < 10; i++)
            randQ.enqueue(i);
        
        for (Integer k : randQ)
            StdOut.print(k+" ");
        StdOut.println();
        for (int i = 0; i < 10; i++) {
            Integer item = randQ.sample();
            // StdOut.println("Sample element " + item);
            randQ.dequeue();
            for (Integer k : randQ)
                StdOut.print(k+" ");
            StdOut.println();
        }
    }
 }