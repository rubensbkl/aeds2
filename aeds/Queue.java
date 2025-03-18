

public class Queue {
    private int[] elements;
    private int n;
    private int capacity;

    public Queue(int capacity) {
        this.elements = new int[capacity];
        this.n = 0;
        this.capacity = capacity;
    }

    public void enqueue(int x) {
        if (this.n < this.capacity) {
            this.elements[this.n] = x;
            this.n++;
        }
    }

    public int dequeue() {
        if (n == 0) {
            System.out.println("Error: Queue is empty!");
            return -1;
        }
        int resp = this.elements[0];
        n--;
        for (int i = 0; i < this.n; i++) {
            this.elements[i] = this.elements[i + 1];
        }
        return resp;
    }

    public void show() {
        System.out.print("Queue:");
        for (int i = 0; i < this.n; i++) {
            System.out.print(" [" + this.elements[i] + "]");
        }
        for (int i = this.n; i < this.capacity; i++) {
            System.out.print(" {}");
        }
        System.out.println();
    }

}