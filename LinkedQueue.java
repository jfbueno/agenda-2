package agenda;

import java.io.Serializable;

public class LinkedQueue<E> implements Queue<E>, List<E>, Serializable {
	protected Node<E> first, last;
	protected int numElements;

	public LinkedQueue() {
		first = last = null;
		numElements = 0;
	}

	public boolean isEmpty() {
		return numElements == 0;
	}

	public boolean isFull() {
		return false;
	}

	public int numElements() {
		return numElements;
	}

	public void enqueue(E element) {
		Node<E> newNode = new Node<E>(element);
		if (isEmpty())
			first = last = newNode;
		else {
			last.setNext(newNode);
			last = newNode;
		}
		numElements++;
	}

	public E dequeue() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();
		E element = first.getElement();
		if (first == last)
			first = last = null;
		else
			first = first.getNext();
		numElements--;
		return element;
	}

	public E front() throws UnderflowException {
		if (isEmpty())
			throw new UnderflowException();

		return first.getElement();
	}

	public E back() {
		if (isEmpty())
			throw new UnderflowException();

		return last.getElement();
	}

	public String toString() {
		if (isEmpty())
			return "[Empty]";
		else {
			String s = "[" + first.getElement();
			Node<E> cur = first.getNext();
			while (cur != null) {
				s += ", " + cur.getElement();
				cur = cur.getNext();
			}
			return s + "]";
		}
	}

	public E removeFirst() {
		if (isEmpty())
			throw new UnderflowException();

		E element = first.getElement();

		if (first == last)
			first = last = null;
		else
			first = first.getNext();

		numElements--;
		return element;
	}

	public E removeLast() {
		if (isEmpty())
			throw new UnderflowException();
		E element = last.getElement();
		if (first == last)
			first = last = null;
		else {
			Node<E> prev = first;
			while (prev.getNext() != last)
				prev = prev.getNext();
			last = prev;
			prev.setNext(null);
		}
		numElements--;
		return element;
	}

	public E remove(int pos) {
		if (pos < 0 || pos >= numElements)
			throw new IndexOutOfBoundsException();
		// casos especiais: remoção do início...
		if (pos == 0)
			return removeFirst();
		else if (pos == numElements - 1) // ... ou remoção do final
			return removeLast();
		else {
			Node<E> prev = first;
			for (int i = 0; i < pos - 1; i++)
				prev = prev.getNext();
			// guarda uma ref. temporária ao elemento sendo removido
			E element = prev.getNext().getElement();
			// ajusta o encadeamento "pulando" o nó sendo removido
			prev.setNext(prev.getNext().getNext());
			// ajusta o total de elementos e retorna o removido
			numElements--;
			return element;
		}
	}
	
	public int search(E element) {
		// percorre o encadeamento até encontrar o elemento
		Node <E> current = first;
		int i = 0;
		while (current != null) {
			if (element.equals(current.getElement())) 
				return i;
			i++;
			current = current.getNext();
		}
		return - 1;
	}

	@Override
	public E get(int pos) {
		if (pos < 0 || pos >= numElements) 
			throw new IndexOutOfBoundsException();
		
		Node <E> current = first;
		
		for (int i = 0; i < pos; i++)
			current = current.getNext();
		
		return current.getElement();
	}

	@Override
	public void insert(E element, int pos) {
		// TODO Auto-generated method stub
		
	}

}
