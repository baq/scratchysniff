package baq;

public class Quicksort {

	public static void quicksort(int[] array, int start, int end) {
		if (start < end) {
			int q = partition(array, start, end);
			quicksort(array, start, q);
			quicksort(array, q + 1, end);
		}
	}
	
	private static int partition(int[] array, int p, int r) {
		int x = array[p];
		int i = p - 1;
		int j = r + 1;
		
		while (true) {
			do {
				j = j - 1;
			} while (array[j] <= x);
			do {
				i = i + 1;
			} while (array[i] >= x);
			if (i < j) {
				int tmp = array[i];
				array[i] = array[j];
				array[j] = tmp;
			} else {
				return j;
			}
		}
	}
	
	public static void main(String[] args) {
		int[] a = {7, 10, 5, 19, 4};
		quicksort(a, 0, 4);
		
		System.out.println(a);
	}
}
