package core.util.estructuradedatos;

import java.util.Collection;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * Artificial Intelligence A Modern Approach (3rd Edition): pg 80.<br>
 * <br>
 * The priority queue, which pops the element of the queue with the highest
 * priority according to some ordering function.
 * 
 * @author Ciaran O'Reilly
 */
public class ColaPrioridad<E> extends java.util.PriorityQueue<E> implements
		Queue<E> {
	private static final long serialVersionUID = 1;

	public ColaPrioridad() {
		super();
	}

	public ColaPrioridad(Collection<? extends E> c) {
		super(c);
	}

	public ColaPrioridad(int initialCapacity) {
		super(initialCapacity);
	}

	public ColaPrioridad(int initialCapacity, Comparator<? super E> comparator) {
		super(initialCapacity, comparator);
	}

	public ColaPrioridad(ColaPrioridad<? extends E> c) {
		super(c);
	}

	public ColaPrioridad(SortedSet<? extends E> c) {
		super(c);
	}

	//
	// START-Queue
	public boolean isEmpty() {
		return 0 == size();
	}

	public E pop() {
		return poll();
	}

	public Queue<E> insert(E element) {
		if (offer(element)) {
			return this;
		}
		return null;
	}
	// END-Queue
	//
}