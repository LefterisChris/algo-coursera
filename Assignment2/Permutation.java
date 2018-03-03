/**
 * Name: Lefteris Christoforidis
 * 
 * Description: takes an integer k as a command-line argument; 
 * reads in a sequence of strings from standard input using StdIn.readString(); 
 * and prints exactly k of them, uniformly at random. 
 */

import java.util.Iterator;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Permutation
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randQ = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            randQ.enqueue(StdIn.readString());
        }
        Iterator<String> it = randQ.iterator();

        int cnt = 0;
        while (cnt < k && it.hasNext()) {
            StdOut.println(it.next());
            cnt++;
        }
      
    /*    String[] s_in = StdIn.readAllStrings();
        
        int[] indicator = new int[s_in.length];
        for (int i = 0; i < k; i++)
            indicator[i] = 1;
        
        StdRandom.shuffle(indicator);

        Deque<String> Q = new Deque<String>();
        for (int i = 0; i < s_in.length; i++)
            if (indicator[i] == 1) Q.addLast(s_in[i]);
        
        for (String s : Q)
            StdOut.println(s); */
    }
}