import java.util.Scanner;

class No {
    public int elemento;
    public No esq, dir;

    public No(int elemento) {
        this(elemento, null, null);
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}


class ArvoreBinariaFodasse  {
   private No raiz;

   public ArvoreBinariaFodasse() {
	   raiz = null;
	}

   public void inserir(int x) throws Exception {
		raiz = inserir(x, raiz);
	}

   private No inserir(int x, No i) throws Exception {
		if (i == null) {
         i = new No(x);

      } else if (x < i.elemento) {
         i.esq = inserir(x, i.esq);

      } else if (x > i.elemento) {
         i.dir = inserir(x, i.dir);

      } else {
         throw new Exception("Erro ao inserir!");
      }

		return i;
	}

	public boolean pesquisar(int x) {
		return pesquisar(x, raiz);
	}

	private boolean pesquisar(int x, No i) {
      boolean resp;
		if (i == null) {
         resp = false;

      } else if (x == i.elemento) {
         resp = true;

      } else if (x < i.elemento) {
         resp = pesquisar(x, i.esq);

      } else {
         resp = pesquisar(x, i.dir);
      }
      return resp;
	}

   public void mostrarEmOrdem() {
      mostrarEmOrdem(raiz);
      System.out.println();
   }

   private void mostrarEmOrdem(No i) {
      if (i != null) {
        mostrarEmOrdem(i.esq);
        System.out.print(i.elemento + " ");
        mostrarEmOrdem(i.dir);
      }
   }

}

public class Main {
    public static void main(String[] args) {
        ArvoreBinariaFodasse arvore = new ArvoreBinariaFodasse();
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU ÁRVORE BINÁRIA ---");
            System.out.println("1 - Inserir elemento");
            System.out.println("2 - Buscar elemento");
            System.out.println("3 - Mostrar em ordem");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();

            try {
                switch (opcao) {
                    case 1:
                        System.out.print("Digite o valor a inserir: ");
                        int valor = scanner.nextInt();
                        arvore.inserir(valor);
                        System.out.println("Inserido com sucesso.");
                        break;
                    case 2:
                        System.out.print("Digite o valor a buscar: ");
                        valor = scanner.nextInt();
                        boolean encontrado = arvore.pesquisar(valor);
                        System.out.println(encontrado ? "Encontrado!" : "Não encontrado.");
                        break;
                    case 3:
                        System.out.println("Elementos em ordem:");
                        arvore.mostrarEmOrdem();
                        break;
                    case 0:
                        System.out.println("Encerrando...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        scanner.close();
    }
}
