import java.util.*;

class Pais {

String nome;
int ouro;
int prata;
int bronze;

String getNome() {
    return nome;
}
}


class Main {

    public static void swap(Pais array[], int a, int b) {

            Pais tmp = array[a];
            array[a] = array[b];
            array[b] = tmp;

        }


    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = 0;

        n = sc.nextInt();
        sc.nextLine();


        Pais[] paises = new Pais[n];

        for(int i=0; i<n; i++) {

            paises[i] = new Pais();

            paises[i].nome = sc.next();
            paises[i].ouro = sc.nextInt();
            paises[i].prata = sc.nextInt();
            paises[i].bronze = sc.nextInt();

        }

        
        for(int i=0; i<n-1; i++) {
            for(int j=0; j<n-i-1; j++) {

                if( paises[j].ouro < paises[j+1].ouro) {
                    swap(paises, j, j+1);
                }
                else if( paises[j].ouro == paises[j+1].ouro && (paises[j].prata < paises[j+1].prata) ) {
                    swap(paises, j, j+1);
                }
                else if( (paises[j].prata == paises[j+1].prata) &&  (paises[j].bronze < paises[j+1].bronze)) {
                    swap(paises, j, j+1);
                }
                else if ( (paises[j].ouro == paises[j+1].ouro) &&  (paises[j].prata == paises[j+1].prata)
                            && (paises[j].bronze == paises[j+1].bronze) &&  (paises[j].getNome().compareTo(paises[j+1].getNome()) > 0))
                swap(paises, j, j+1);

            }

        }

        for(int i=0; i<n; i++) {
            System.out.println(paises[i].nome + " " + paises[i].ouro + " " + paises[i].prata + " " + paises[i].bronze);
        }

    }

}