package agenda;

public interface Queue<E> {
    boolean isEmpty();
    boolean isFull();
    int numElements();
    void enqueue(E element);
    E dequeue();
    E front();
    E back();
}
