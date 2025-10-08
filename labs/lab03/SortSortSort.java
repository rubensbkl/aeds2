import java.util.Scanner;

public class SortSortSort {

    public static int compare(int a, int b, int M) {
        int modA = a - (a / M) * M;
        int modB = b - (b / M) * M;

        if (modA != modB) return modA - modB;

        boolean oddA = (a % 2 != 0);
        boolean oddB = (b % 2 != 0);

        if (oddA && !oddB) return -1;
        if (!oddA && oddB) return 1;

        if (oddA && oddB) return b - a;
        return a - b;
    }

    public static void quickSort(Integer[] arr, int low, int high, int M) {
        if (low < high) {
            int pi = partition(arr, low, high, M);
            quickSort(arr, low, pi - 1, M);
            quickSort(arr, pi + 1, high, M);
        }
    }

    private static int partition(Integer[] arr, int low, int high, int M) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(arr[j], pivot, M) < 0) {
                i++;
                int tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            }
        }
        int tmp = arr[i + 1]; arr[i + 1] = arr[high]; arr[high] = tmp;
        return i + 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            System.out.println(n + " " + m);

            if (n == 0 && m == 0) break;

            Integer[] nums = new Integer[n];
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }

            quickSort(nums, 0, n - 1, m);

            for (int num : nums) {
                System.out.println(num);
            }
        }

        sc.close();
    }
}

