

public class Stack {
    private int[] elements;
    private int n;
    private int capacity;

    public Stack(int capacity) {
        this.elements = new int[capacity];
        this.n = 0;
        this.capacity = capacity;
    }

    public void push(int element) { // Empilhar // Inserir no fim
        if (this.n < this.capacity) {
            this.elements[this.n] = element;
            this.n++;
        } else {
            System.out.println("Error: Stack is full!");
        }
    }

    public int pop() { // Desempilhar // Remover do fim
        if (n == 0) {
            System.out.println("Error: Stack is empty!");
            return -1;
        }
        n--;
        int resp = this.elements[this.n];
        return resp;
    }

    public void show() {
        System.out.print("Stack:");
        for (int i = 0; i < this.n; i++) {
            System.out.print(" [" + this.elements[i] + "]");
        }
        for (int i = this.n; i < this.capacity; i++) {
            System.out.print(" {}");
        }
        System.out.println();
    }
}
