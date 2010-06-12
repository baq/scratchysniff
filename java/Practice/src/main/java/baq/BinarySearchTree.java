package baq;

import java.util.Stack;

public class BinarySearchTree {

	public static Node buildBinarySearchTree(int[] array, int start, int end) {
		if (end - start <= 0) {
			return null;
		}
		if (end - start == 1) {
			return new Node(array[start]);
		}
		Node node = new Node(array[(end-start)/2]);
		System.out.println(node.value);
		node.left = buildBinarySearchTree(array, start, (end-start)/2);
		node.right = buildBinarySearchTree(array, ((end-start)/2)+1, end);
		return node;
	}
	
	public static void printInorderRecursive(Node node) {
		if (node != null) {
			printInorderRecursive(node.left);
			System.out.print(node.value + " ");
			printInorderRecursive(node.right);
		}
	}
	
	public static void printInorderNonRecursive(Node node) {
		Stack<Node> stack = new Stack<Node>();
		stack.push(node);
		
		boolean left = true;
		while (!stack.isEmpty()) {
			Node currNode = stack.peek();
			if (left) {
				if (currNode.left != null) {
					stack.push(currNode.left);
				} else {
					left = false;
				}
			} else {
				stack.pop();
				System.out.print(currNode.value + " ");
				if (currNode.right != null) {
					stack.push(currNode.right);
					left = true;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Node p = new Node(5);
		Node l = new Node(3);
		Node r = new Node(7);
		p.left = l;
		p.right = r;
		
		Node ll = new Node(2);
		Node lr = new Node(5);
		l.left = ll;
		l.right = lr;
		
		Node rr = new Node(8);
		r.right = rr;
		
		printInorderRecursive(p);
		System.out.println();
		printInorderNonRecursive(p);
		
		int[] array = new int[]{2, 3, 5, 5, 7, 8};
		Node tree = buildBinarySearchTree(array, 0, 6);
		printInorderRecursive(tree);
	}
}
