import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MinPQTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void sizeOfEmptyQueue() {
        MinPQ<Integer> pq = new MinPQ<>();

        assertThat(pq.size(), is(0));
    }

    @Test
    public void addNullThrowsException() {
        thrown.expect(NullPointerException.class);
        thrown.expectMessage("item is null");

        new MinPQ<Integer>().add(null);
    }

    @Test
    public void add() {
        MinPQ<Integer> pq = new MinPQ<>();

        pq.add(6);
        pq.add(2);
        pq.add(9);
        pq.add(1);

        assertThat(pq.size(), is(4));
    }

    @Test
    public void poll() {
        MinPQ<Integer> pq = new MinPQ<>();

        pq.add(6);
        pq.add(2);
        pq.add(9);
        pq.add(1);

        assertThat(pq.poll(), is(1));
        assertThat(pq.poll(), is(2));
        assertThat(pq.poll(), is(6));
        assertThat(pq.poll(), is(9));
        assertThat(pq.poll(), is(nullValue()));
    }

}
