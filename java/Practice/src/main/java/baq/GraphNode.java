package baq;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GraphNode {
	
	public int value;
	public LinkedList<GraphNode> neighbors;
	
	public GraphNode(int value) {
		this.value = value;
		this.neighbors = new LinkedList<GraphNode>();
	}
	
	public GraphNode(int value, LinkedList<GraphNode> neighbors) {
		this.value = value;
		this.neighbors = neighbors;
	}
	
	public void add(GraphNode node) {
		neighbors.add(node);
	}
	
	public void add(GraphNode... nodes) {
		for (GraphNode node : nodes) {
			neighbors.add(node);
		}
	}
	
	@Override
	public int hashCode() {
		return new Integer(value).hashCode();
	}

	public static List<GraphNode> shortestPath(GraphNode startNode, GraphNode finalNode) {
		
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		Set<GraphNode> visited = new HashSet<GraphNode>();
		// Map that holds node -> predecessor relationship as key - value pairs
		Map<GraphNode, GraphNode> shortestPath = new HashMap<GraphNode, GraphNode>();
		
		shortestPath.put(startNode, null);
		queue.add(startNode);
		visited.add(startNode);
		boolean found = false;
		while (!queue.isEmpty()) {
			GraphNode currNode = queue.remove();
			if (currNode.value == finalNode.value) {
				found = true;
				break;
			}
			for (GraphNode childNode : currNode.neighbors) {
				if (!visited.contains(childNode)) {
					queue.add(childNode);
					visited.add(childNode);
					shortestPath.put(childNode, currNode);
				}
			}
		}
		
		if (found) {
			LinkedList<GraphNode> pathList = new LinkedList<GraphNode>();
			GraphNode currNode = finalNode;
			while (currNode != null) {
				pathList.push(currNode);
				currNode = shortestPath.get(currNode);
			}
			return pathList;
		} 
		return null;
	}
	
	public static boolean breadthFirstSearch(GraphNode node, int value) {
		Queue<GraphNode> queue = new LinkedList<GraphNode>();
		Set<Integer> visited = new HashSet<Integer>();
		
		queue.add(node);
		boolean found = false;
		visited.add(node.value);
		while (!queue.isEmpty()) {
			GraphNode currNode = queue.remove();
			if (currNode.value == value) {
				found = true;
				break;
			}
			LinkedList<GraphNode> neighbors = currNode.neighbors;
			for (GraphNode graphNode : neighbors) {
				if (!visited.contains(graphNode.value)) {
					queue.add(graphNode);
					visited.add(graphNode.value);
				}
			}
		}
		return found;
	}
	
	public static void main(String[] args) {
		GraphNode node1 = new GraphNode(1);
		GraphNode node2 = new GraphNode(2);
		GraphNode node3 = new GraphNode(3);
		GraphNode node4 = new GraphNode(4);
		GraphNode node5 = new GraphNode(5);
		
		node1.add(node2, node5);
		node2.add(node1, node5, node3, node4);
		node3.add(node2, node4);
		node4.add(node2, node5, node3);
		node5.add(node4, node1, node2);
		
		System.out.println(breadthFirstSearch(node1, 3));
		
		List<GraphNode> shortestPath = shortestPath(node1, node5);
		for (GraphNode node : shortestPath) {
			System.out.print(node.value + " ");
		}
		System.out.println();
	}
}
