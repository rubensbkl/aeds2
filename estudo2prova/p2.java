import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		int n = 0;
		Scanner sc = new Scanner(System.in);
		n = sc.nextInt();

		while (n > 0) {
			int m = sc.nextInt();
			int[] alunos = new int[1000];
			for (int i = 0; i < m; i++) {
				alunos[i] = sc.nextInt();
			}

			int acc = 0;

			for (int i = 0; i < m - 1; i++) {
				for (int j = i + 1; j < m; j++) {
					if (alunos[j] > alunos[i]) {
						int temp = alunos[i];
						alunos[i] = alunos[j];
						alunos[j] = temp;
						acc++;
					}
				}
			}


			System.out.println(m - (acc * 2));
			n--;
		}
	}
}
