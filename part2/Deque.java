import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {
    
    private class Node<Item> {
        public Item data;
        public Node<Item> next;
        public Node<Item> prev;
        
        public Node(Item data, Node<Item> next, Node<Item> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
        
    }
    
    private Node<Item> head;
    private Node<Item> tail;
    
    private int N = 0;
    
    public Deque() {                           // construct an empty deque
        
        
    }
    public boolean isEmpty() {                // is the deque empty?
        return N == 0;
    }
    public int size() {                        // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item)          // add the item to the front
    {
        if (item == null) {
            throw new java.lang.IllegalArgumentException("addFirst");
        }
        
        Node<Item> tmp = new Node<Item> (item, head, null);
        if (N++ == 0) {
            head = tail = tmp;
        } else {
            head.prev = tmp;
            head = tmp;
        }
    }
    
    
    
    public void addLast(Item item) {           // add the item to the end
        if (item == null) {
            throw new java.lang.IllegalArgumentException("addLast");
        }
        
        Node<Item> tmp = new Node<Item> (item, null, tail);
        if (N++ == 0) {
            head = tail = tmp;
        } else {
            tail.next = tmp;
            tail = tmp;
        }
    }
    
    
    
    public Item removeFirst() {                 // remove and return the item from the front
        if (N == 0) throw new java.util.NoSuchElementException();
        
        Node <Item> tmp = head;
        if (--N == 0) {
            head = tail = null;
        } else {
            head = tmp.next;
            head.prev = null;
        }
        
        return tmp.data;
    }
    public Item removeLast() {                 // remove and return the item from the end
        if (N == 0) throw new java.util.NoSuchElementException();
        
        Node <Item> tmp = tail;
        if (--N == 0) {
            tail = head = null;
        } else {
            tail = tmp.prev;
            tail.next = null;
        }
        
        return tmp.data;
    }
    public Iterator<Item> iterator() {return new ListIterator(); }        // return an iterator over items in order from front to end
    
    private class ListIterator implements Iterator<Item>
    {
        private Node<Item> current = head;
        public boolean hasNext() {  return current != null;  }
        public void remove()     {  
            /* not supported */
            throw new java.lang.UnsupportedOperationException();
        }      
        public Item next()
        {
            if (current == null) throw new java.util.NoSuchElementException();
            
            Item data = current.data;
            current   = current.next; 
            return data;
        }
    }   
    public static void main(String[] args){   // unit testing (optional)
/*
        Deque <Integer> deq = new Deque <Integer>();
        deq.addFirst(1);
        deq.addFirst(0);
        deq.addLast(2);
        deq.addLast(3);
        System.out.printf("\nFrom head:");
        while (deq.isEmpty() != true) {
            System.out.printf(" %d ", deq.removeFirst());
        }
        deq.addFirst(2);
        deq.addFirst(3);
        deq.addLast(1);
        deq.addLast(0);
        System.out.printf("\nFrom tail:");
        while (deq.isEmpty() != true) {
            System.out.printf(" %d ", deq.removeLast());
        }
        reached end of file while parsing
        */
    }
}
