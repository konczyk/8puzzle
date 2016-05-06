/**
 * Min priority queue implementation with a binary heap
 * and sorting using natural order
 */
public class MinPQ<T extends Object & Comparable<? super T>> {

    private T[] pq;
    private int size;

    // the  array will contain only T instances passed through insert()
    @SuppressWarnings("unchecked")
    public MinPQ() {
        pq = (T[]) new Object[0];
        size = 0;
    }

    public int size() {
        return size;
    }

    public void add(T item) {
        // resize the queue if needed
        if (pq.length == size) {
            resize(Math.max(2*pq.length, 2));
        }

        pq[size++] = item;
        swim(size);
    }

    // the tmp array will contain only T instances copied from the pq
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        T[] tmp = (T[]) new Object[newCapacity];
        System.arraycopy(pq, 0, tmp, 0, size);
        pq = tmp;
    }

    private void swim(int k) {
        while (k > 1 && pq[(k-1)/2].compareTo(pq[k-1]) > 0) {
            swap(k-1, (k-1)/2);
            k = k/2;
        }
    }

    public T poll() {
        T min = null;
        if (size > 0) {
            // extract the min by moving it to the tail of the array
            swap(0, --size);
            min = pq[size];
            // heapify the queue
            sink();
            // resize the queue if needed
            pq[size] = null;
            if (size == pq.length/4) {
                resize(pq.length / 2);
            }
        }

        return min;
    }

    private void sink() {
        int k = 1;
        while (2*k <= size) {
            int j = 2*k;
            // select smaller child
            if (j < size && pq[j-1].compareTo(pq[j]) > 0) {
                j++;
            }
            // exchange smaller child with the parent, if needed
            if (pq[k-1].compareTo(pq[j-1]) <= 0) {
                break;
            }
            swap(k-1, j-1);
            k = j;
        }
    }

    // swap pq items indexed by i and j
    private void swap(int i, int j) {
        T tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }
}
