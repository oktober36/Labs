package queue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class QueueTest {
    Queue queue;


    @BeforeEach
    void init(){
        queue = new Queue();
    }

    @Test
    void iterator() {
    }

    @Test
    void size() {
        assertEquals(0, queue.size());
        queue.add(1);
        assertEquals(1, queue.size());
    }

    @Test
    void offer() {
        boolean added = queue.add(1);
        assertEquals(true, added);
        assertEquals(1, queue.size());
        assertEquals(false,queue.offer(null));
    }

    @Test
    void poll(){
        queue.add(1);
        queue.add(2);
        assertEquals(1, queue.poll());
        assertEquals(1 , queue.size());
    }

    @Test
    void peek() {
        queue.add(1);
        queue.add(2);
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
    }
}