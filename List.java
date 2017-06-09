package agenda;

public interface List<E> {
    int numElements();
    E get(int index);
    void insert(E element, int pos);
    boolean isEmpty();
    boolean isFull();
    E remove(int pos);
}
