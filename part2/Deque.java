import java.lang.NullPointerException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private class Node<Item> {
		public Node(Item item, Node<Item> prev, Node<Item> next) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}

		Item item;
		Node<Item> next;
		Node<Item> prev;
	}

	private int Size = 0;
	private Node<Item> first;
	private Node<Item> last;

	public Deque() {
		// construct an empty deque
	}

	public boolean isEmpty() {
		// is the deque empty?
		return (Size == 0);
	}

	public int size() {
		// return the number of items on the deque
		return Size;
	}

	public void addFirst(Item item) {
		// add the item to the front
		if (item == null)
			throw new NullPointerException();
		Node<Item> new_item = new Node<Item>(item, null, first);
		if (Size != 0) {
			first.prev = new_item;
			first = new_item;
		} else {
			last = first = new_item;
		}
		Size++;
	}

	public void addLast(Item item) {
		// add the item to the end
		if (item == null)
			throw new NullPointerException();
		Node<Item> new_item = new Node<Item>(item, last, null);
		if (Size != 0) {
			last.next = new_item;
			last = new_item;
		} else {
			last = first = new_item;
		}
		Size++;
	}

	public Item removeFirst() {
		// remove and return the item from the front
		if (Size == 0)
			throw new NoSuchElementException();

		Node<Item> temp = first;
		first = temp.next;
		if (first == null) {
			last = null;
		}
		Size--;
		return temp.item;
	}

	public Item removeLast() {
		// remove and return the item from the end
		if (Size == 0)
			throw new NoSuchElementException();

		Node<Item> temp = last;
		last = temp.prev;
		if (last == null) {
			first = null;
		}
		Size--;
		return temp.item;
	}

	public Iterator<Item> iterator() {
		// return an iterator over items in order from front to end
		return new ItemIterator();
	}

	private class ItemIterator implements Iterator<Item> {
		private Node<Item> current = first;

		public boolean hasNext() {
			return current != null;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

		public Item next() {
			if (current == null)
				throw new java.lang.UnsupportedOperationException();
			Item temp = current.item;
			current = current.next;
			return temp;
		}
	}
}
