package baq;

public class Heap {

	private static int left(int i) {
		return 2 * i + 1;
	}
	
	private static int right(int i) {
		return 2 * i + 2;
	}
	
	public static void heapify(int[] heap, int rootIndex) {
		// Get the index of the left subtree
		int leftIndex = left(rootIndex);
		// Get the index of the right subtree
		int rightIndex = right(rootIndex);
		// Assume for now that the largest number is at the root index
		int largest = rootIndex;
		// If the number at the left index is larger, than the largest index is the left index
		if (leftIndex < heap.length && heap[leftIndex] > heap[largest]) {
			largest = leftIndex;
		}
		// If the number at the right index is larger than the new largest, then the largest index
		// is the right index
		if (rightIndex < heap.length && heap[rightIndex] > heap[largest]) {
			largest = rightIndex;
		}
		// If the largest ends up not being the original index, then we need to swap the larger
		// value for the one at the original index.  
		if (largest != rootIndex) {
			int tmp = heap[rootIndex];
			heap[rootIndex] = heap[largest];
			heap[largest] = tmp;
			heapify(heap, largest);
		}
	}
	
	public static void buildHeap(int[] array) {
		for (int i = array.length/2 - 1; i >= 0; i--) {
			heapify(array, i);
		}
	}
	
	public static void printArray(int[] array) {
		System.out.print("[");
		for (int i = 0; i < array.length; i++) {
			if (i != array.length - 1)
				System.out.print(array[i] + ", ");
			else 
				System.out.print(array[i]);
		}
		System.out.print("]");
	}
	
	public static void main(String[] args) {
		int[] sample = new int[]{2, 7, 4, 9, 12, 1, 3};
		buildHeap(sample);
		printArray(sample);
	}
	
	
}
