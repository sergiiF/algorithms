import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] s;
    private int N = 0;    
    
    
    private void resize(int capacity)
    {
        Item [] copy = (Item[]) new Object[capacity];
        
        for (int i = 0; i < N; i++)
            copy[i] = s[i];
        s = copy;
    }   
    
    public RandomizedQueue() {                // construct an empty randomized queue
        s = (Item[]) new Object[1];
    }
    
    public boolean isEmpty() {                // is the randomized queue empty?
        return N ==0;
    }
    
    public int size() {                        // return the number of items on the randomized queue
        return N;
    }
    public void enqueue(Item item) {          // add the item
        if (item == null) {
            throw new java.lang.IllegalArgumentException("");
        }
        
        if (N == s.length) resize(2 * s.length);
        
        s[N++]=item;
    }
    public Item dequeue() {                   // remove and return a random item
        
        if (N == 0) throw new java.util.NoSuchElementException("");
        
        int index = StdRandom.uniform(N--);
        Item tmp = s[index];
        if (N != 0){
            s[index] = s[N];
            if (N == s.length/4) resize(s.length/2);
        }
        
        return tmp;
    }
    public Item sample() {                    // return a random item (but do not remove it)
        if (N == 0) throw new java.util.NoSuchElementException("");
        
        return s[StdRandom.uniform(N)];
    }
    
    
    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new ArrayIterator();
    }
    
    private class ArrayIterator implements Iterator<Item>
    {
        private int i = 0;
        private int[] indexes;
        
        ArrayIterator() {
            indexes = new int[N];
            
            for (int i = 0; i<N;i++) indexes[i]=i;
            
            StdRandom.shuffle(indexes);
        }
        
        public boolean hasNext() {  return i < N;        }
        
        public void remove()     {  
            /* not supported */
            throw new java.lang.UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            
            return s[indexes[i++]];
        }
    }
    
    public static void main(String[] args) {   // unit testing (optional)
 /*
        RandomizedQueue <Integer> rq = new RandomizedQueue <Integer> ();
        
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        
        for (int item : rq) { 
            System.out.printf("%d, ", item);
        }
        System.out.println("");
        for (int item : rq) { 
            System.out.printf("%d, ", item);
        } 
        System.out.println("");        
        while (!rq.isEmpty()) { 
            System.out.printf("Removing %d, ", rq.dequeue());
        }
     */       
    }

}

