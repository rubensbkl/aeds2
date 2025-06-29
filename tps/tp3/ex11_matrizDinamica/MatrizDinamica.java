/**
 *
 * Pontificia Universidade Catolica de Minas Gerais
 *
 * Curso de Ciencia da Computacao
 * Algoritimo e Estruturas de Dados II
 *
 * TP03Q10 - Matriz Dinamica - v2.0 - 08 / 06 / 2025
 * 855796 - Rubens Dias Bicalho
 *
 */

import java.util.*;

// Inicio celula
class Celula {

    public int elemento;
    public Celula inf, sup, esq, dir;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this.elemento = elemento;
        this.inf = sup = esq = dir = null;
    }
}
// Fim celula

// Inicio Matriz (classe principal)
public class MatrizDinamica {

    private Celula inicio;
    private int linha, coluna;

    public MatrizDinamica() {
        this(3, 3);
    }

    public MatrizDinamica(int linha, int coluna) {

        this.linha = linha;
        this.coluna = coluna;

        inicio = new Celula();
        Celula i = inicio;
        Celula j;

        for (int a = 0; a < linha; a++) {
            j = i;
            for (int b = 1; b < coluna; b++) {
                j.dir = new Celula();
                j.dir.esq = j;
                j = j.dir;
            }
            if (a < linha - 1) {
                i.inf = new Celula();
                i.inf.sup = i;
                i = i.inf;
            }
        }

        i = inicio;
        for (int a = 0; a < linha - 1; a++) {
            j = i;
            Celula k = i.inf;
            for (int b = 0; b < coluna; b++) {
                j.inf = k;
                k.sup = j;
                j = j.dir;
                k = k.dir;
            }
            i = i.inf;
        }
    }

    public void mostrar() {

        Celula i = inicio;
        Celula j = inicio;
        for (int a = 0; a < linha; a++) {
            for (int b = 0; b < coluna; b++) {
                System.out.print(j.elemento + " ");
                j = j.dir;
            }
            System.out.println();
            i = i.inf;
            j = i;
        }
    }

    public Celula getInicio() {
        return inicio;
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    public Celula getPos(int i, int j) {

        Celula temp = inicio;

        for (int linha = 0; linha < i; linha++) {
            temp = temp.inf;
        }
        for (int coluna = 0; coluna < j; coluna++) {
            temp = temp.dir;
        }
        return temp;
    }

    public void set(int i, int j, int elemento) {
        Celula temp = getPos(i, j);
        temp.elemento = elemento;
    }

    public static MatrizDinamica soma(MatrizDinamica x, MatrizDinamica y) {

        if (x.linha != y.linha || x.coluna != y.coluna) {
            throw new IllegalArgumentException("Tamanhos diferentes");
        }

        MatrizDinamica resp = new MatrizDinamica(x.linha, x.coluna);

        for (int i = 0; i < x.linha; i++) {
            for (int j = 0; j < x.coluna; j++) {
                resp.set(i, j, x.get(i, j) + y.get(i, j));
            }
        }
        return resp;
    }

    public static MatrizDinamica multiplicacao(MatrizDinamica x, MatrizDinamica y) {

        if (x.coluna != y.linha) {
            throw new IllegalArgumentException("Tamanhos invalidos"); 
        }

        MatrizDinamica resp = new MatrizDinamica(x.linha, y.coluna);

        for (int i = 0; i < x.linha; i++) {
            for (int j = 0; j < y.coluna; j++) {
                int soma = 0;
                for (int k = 0; k < x.coluna; k++) {
                    soma += x.get(i, k) * y.get(k, j);
                }
                resp.set(i, j, soma);
            }
        }
        return resp;
    }

    public int[] getDiagonalPrincipal() {

        int[] diagPrin = new int[Math.min(linha, coluna)];

        Celula temp = inicio;

        for (int i = 0; i < diagPrin.length; i++) {
            diagPrin[i] = temp.elemento;
            if (temp.inf != null && temp.dir != null) {
                temp = temp.inf.dir;
            }
        }
        return diagPrin;
    }

    public void mostrarDiagonalPrincipal() {

        int[] diagPrin = getDiagonalPrincipal();

        for (int i = 0; i < diagPrin.length; i++) {
            System.out.print(diagPrin[i] + " ");
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {

        int[] diagSec = getDiagonalSecundaria();

        for (int i = 0; i < diagSec.length; i++) {
            System.out.print(diagSec[i] + " ");
        }
        System.out.println();
    }

    public int[] getDiagonalSecundaria() {

        int [] diagSec = new int[Math.min(linha, coluna)];

        Celula temp = inicio;

        for (int i = 0; i < coluna - 1; i++){
            if (temp.dir != null){
                temp = temp.dir;
            }
        }
        for (int i = 0; i < diagSec.length; i++){
            diagSec[i] = temp.elemento;
            if (temp.inf != null && temp.esq != null){
                temp = temp.inf.esq;
            }
        }
        return diagSec;
    }

    public Celula remover(int i, int j) {

        Celula temp = getPos(i, j);

        if (temp == null) {
            throw new IllegalArgumentException("Posicao invalida");
        }
        if (temp.esq != null) {
            temp.esq.dir = temp.dir;
        }
        if (temp.dir != null) {
            temp.dir.esq = temp.esq;
        }
        if (temp.sup != null) {
            temp.sup.inf = temp.inf;
        }
        if (temp.inf != null) {
            temp.inf.sup = temp.sup;
        }
        return temp;
    }

    public int get(int i, int j) {
        Celula temp = getPos(i, j);
        return temp.elemento;
    }

    // Main
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        for (int teste = 0; teste < n; teste++) {

            int lin1 = sc.nextInt();
            int col1 = sc.nextInt();

            MatrizDinamica matriz1 = new MatrizDinamica(lin1, col1);

            for (int i = 0; i < lin1; i++) {
                for (int j = 0; j < col1; j++) {
                    int valor = sc.nextInt();
                    matriz1.set(i, j, valor);
                }
            }

            int lin2 = sc.nextInt();
            int col2 = sc.nextInt();

            MatrizDinamica matriz2 = new MatrizDinamica(lin2, col2);

            for (int i = 0; i < lin2; i++) {
                for (int j = 0; j < col2; j++) {
                    int valor = sc.nextInt();
                    matriz2.set(i, j, valor);
                }
            }

            matriz1.mostrarDiagonalPrincipal();
            matriz1.mostrarDiagonalSecundaria();

            MatrizDinamica soma = MatrizDinamica.soma(matriz1, matriz2);
            soma.mostrar();

            MatrizDinamica mult = MatrizDinamica.multiplicacao(matriz1, matriz2);
            mult.mostrar();
        }
        sc.close();
    }
}
