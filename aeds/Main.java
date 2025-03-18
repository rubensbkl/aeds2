import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int op = -1;

        System.out.println();
        System.out.println("Choose a data structure:");
        System.out.println("1 - Stack");
        System.out.println("2 - Queue");
        System.out.println("3 - Array");
        System.out.println("0 - Exit");
        System.out.println();
        System.out.print("Option: ");
        op = scanner.nextInt();

        while (op != 0) {

            if (op == 1) {
                int stackop = -1;
                System.out.println();
                System.out.print("Stack size: ");
                int size = scanner.nextInt();
                Stack stack = new Stack(size);
                while (stackop != 0) {
                    System.out.println();
                    stack.show();
                    System.out.println("1 - Push");
                    System.out.println("2 - Pop");
                    System.out.println("3 - Refresh");
                    System.out.println("0 - Exit");
                    System.out.println();
                    System.out.print("Option: ");
                    stackop = scanner.nextInt();

                    if (stackop == 1) {
                        System.out.print("Value: ");
                        int element = scanner.nextInt();
                        stack.push(element);
                    } else if (stackop == 2) {
                        System.out.println("Removido: " + stack.pop());
                        System.out.println();
                    } else if (stackop == 3) {
                        stack.show();
                    }
                }
            }

            if (op == 2) {
                int queueop = -1;
                System.out.println();
                System.out.print("Queue size: ");
                int size = scanner.nextInt();
                Queue queue = new Queue(size);
                while (queueop != 0) {
                    System.out.println();
                    queue.show();
                    System.out.println("1 - Enqueue");
                    System.out.println("2 - Dequeue");
                    System.out.println("3 - Refresh");
                    System.out.println("0 - Exit");
                    System.out.println();
                    System.out.print("Option: ");
                    queueop = scanner.nextInt();

                    if (queueop == 1) {
                        System.out.print("Value: ");
                        int element = scanner.nextInt();
                        queue.enqueue(element);
                    } else if (queueop == 2) {
                        System.out.println("Removido: " + queue.dequeue());
                        System.out.println();
                    } else if (queueop == 3) {
                        queue.show();
                    }
                }
            }

            if (op == 3) {
                int arrayop = -1;
                System.out.println();
                System.out.print("Array size: ");
                int size = scanner.nextInt();
                Array array = new Array(size);
                while (arrayop != 0) {
                    System.out.println();
                    array.show();
                    System.out.println("1 - Inserir no início");
                    System.out.println("2 - Inserir no fim");
                    System.out.println("3 - Inserir na posição");
                    System.out.println("4 - Remover do início");
                    System.out.println("5 - Remover do fim");
                    System.out.println("6 - Remover da posição");
                    System.out.println("7 - Pesquisa sequencial");
                    System.out.println("8 - Pesquisa binaria");
                    System.out.println("9 - Fill Random");
                    System.out.println("10 - Selection Sort");
                    System.out.println("0 - Exit");
                    System.out.println();
                    System.out.print("Option: ");
                    arrayop = scanner.nextInt();

                    if (arrayop == 1) {
                        System.out.print("Value: ");
                        int element = scanner.nextInt();
                        array.inserirInicio(element);
                    } else if (arrayop == 2) {
                        System.out.print("Value: ");
                        int element = scanner.nextInt();
                        array.inserirFim(element);
                    } else if (arrayop == 3) {
                        System.out.print("Value: ");
                        int element = scanner.nextInt();
                        System.out.print("Position: ");
                        int pos = scanner.nextInt();
                        array.inserir(element, pos);
                    } else if (arrayop == 4) {
                        System.out.println("Removido: " + array.removerInicio());
                        System.out.println();
                    } else if (arrayop == 5) {
                        System.out.println("Removido: " + array.removerFim());
                        System.out.println();
                    } else if (arrayop == 6) {
                        System.out.print("Position: ");
                        int pos = scanner.nextInt();
                        System.out.println("Removido: " + array.remover(pos));
                        System.out.println();
                    } else if (arrayop == 7) {
                        int num = scanner.nextInt();
                        System.out.println(array.pesquisaSequencial(num));
                    } else if (arrayop == 8) {
                        int num = scanner.nextInt();
                        System.out.println(array.pesquisaBinaria(num));
                    } else if (arrayop == 9) {
                        System.out.println("Mínimo:" );
                        int min = scanner.nextInt();
                        System.out.println("Máximo:" );
                        int max = scanner.nextInt();
                        array.fillRandom(min, max);
                    } else if (arrayop == 10) {
                        array.selectionSort();
                    }
                }
            }

        }
        System.out.println("Bye!");
        scanner.close();
    }
}
