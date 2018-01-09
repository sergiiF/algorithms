import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String> ();
        String line;
        int count = 0;
        
        //read count from command line
        if ( args[0].matches( "(\\d+)" ) )
        {
            count = Integer.parseInt( args[0] );
        }
        
        while ((!StdIn.isEmpty()) && (line = StdIn.readString()) != null && line.length()!= 0) { 
            rq.enqueue(line);
        }
        //print "count" items from rq
        Iterator<String> it = rq.iterator();
        
        try {
            while (count-- > 0) {
                System.out.printf("%s\n", it.next());
            }
        } catch (java.util.NoSuchElementException e) {
            System.out.println("Wrong count number");
        }
    }
}
