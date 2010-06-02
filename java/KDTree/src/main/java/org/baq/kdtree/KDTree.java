package org.baq.kdtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This kd-tree class allows for a binary tree representation of
 * points in 2-dimensional space. 
 * @author Baq
 *
 */
public class KDTree {
	
	/**
	 * Constructor
	 * @param points
	 */
	public KDTree(List<Point> points) {
		// Make an internal copy of the array as the ordering of the 
		// elements of this array will be mutated.
		points_ = new ArrayList<Point>(points);
		// Find the dimension (either x or y) whose points have the greatest
		// variance--this will be the dimension to be partitioned first
		Dimension d = TreeUtils.findHighlyVariantDimension(points_);
		// Create the root node from our points.
		root_ = new Node(d, 0, points.size()-1, null);
		// Initialize the boundary rectangle for our root node.  Since the
		// root node has no boundaries, its rectangle is infinitely large.
		root_.boundary_ = new Rectangle(Float.POSITIVE_INFINITY,
                                        Float.NEGATIVE_INFINITY,
                                        Float.POSITIVE_INFINITY,
                                        Float.NEGATIVE_INFINITY);
		// Initialize a neighbors object for the root node where all directions
		// are null (there are no neighbors for the root node)
		root_.neighbors_ = new Neighbors(null, null, null, null);
		// Make the recursive call that will build the kd-tree
		splitNode(root_);
	}
	
	/**
	 * Finds the nearest point in the kd-tree to the supplied point
	 * @param probe
	 * @return
	 */
	public Point findNearestPoint(Point probe) {
		Node likeliestNode = TreeUtils.findLikeliestNode(root_, probe);
        // The PriorityQueue API doesn't allow us to pass a comparator without
        // passing a capacity value when constructing, so let's pass a value 
        // of '4' here.  
		PriorityQueue<Node> queue = new PriorityQueue<Node>(PQ_INIT_SIZE, new TreeUtils.NodeComparator(probe));
		queue.add(likeliestNode);
		
        Point currClosestPoint = INFINITE_POINT;
		Set<Node> visitedNodes = new HashSet<Node>();
		
		while (!queue.isEmpty()) {
			Node currNode = queue.poll();
            // First check if we should visit this node at all:
            // Has it already been visitied?
            // Is it closer to the probe point than the current closest point?
			if (!shouldVisitNode(currNode, probe, currClosestPoint, visitedNodes)) {
			    continue;
            }
            // If this is an interior node, we need to queue up its children
            if (currNode.isInterior()) {
                if (shouldVisitNode(currNode.leftNode_,
                                    probe, currClosestPoint,
                                    visitedNodes)) {
                    queue.add(currNode.leftNode_);
                }
                if (shouldVisitNode(currNode.rightNode_,
                                    probe, currClosestPoint,
                                    visitedNodes)) {
                    queue.add(currNode.rightNode_);
                }
            } else {
                // This is not an interior node, but rather a leaf node of the
                // tree.  Let's find the closest point in this node and see if
                // it's closer than our current closest point.  Also, we may need
                // to check its neighbors so queue them up in priority order
                List<Point> points = points_.subList(currNode.startIndex_, currNode.endIndex_+1);
                Point closestPoint = Utils.getClosestPointInList(probe,
                                                                     points);
                if (Utils.getEuclideanDistance(probe, closestPoint) <
                    Utils.getEuclideanDistance(probe, currClosestPoint)) {
                    currClosestPoint = closestPoint;
                }
                Iterator<Node> i = currNode.neighbors_.getIterator();
                while (i.hasNext()) {
                    Node neighbor = i.next();
                    if (shouldVisitNode(neighbor, probe, currClosestPoint, visitedNodes)) {
                        queue.add(neighbor);
                    }
                }
            }
            
			// Specify that we visited this node
			visitedNodes.add(currNode);
		}
		
        // Theoretically this should never happen, but just in case we've 
        // never found a closer point (and therefore never reassigned the 
        // variable--thus the reference equality check) let's return null
        if (currClosestPoint == INFINITE_POINT) {
            return null;
        }
		return currClosestPoint;
	}
	
	/**
	 * Prints kd-tree data to System.out
	 */
	public void printTree() {
		System.out.println(points_);
		// Recursive call to print tree
		printSubTree(root_, 0);
	}
	
	/**
	 * Package protected access to the root of the kd-tree (for testing).
	 * @return
	 */
	Node getRoot() {
		return root_;
	}
	
	// The internal list of points.  Sub-lists of this list will be referenced
	// by the kd-tree
	private final List<Point> points_;
	// The root node of the kd-tree
	private final Node root_;
	// The maximum number of points we will allow in the leaf nodes of the
	// kd-tree
	private final static int MAX_POINTS_IN_PARTITION = 2;
    // A point that's very very far away
    private final static Point INFINITE_POINT;
    static {
        INFINITE_POINT = new Point(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }
    // An initial size for the priority queue used in finding
    // the nearest neighbor
    private final static int PQ_INIT_SIZE = 4;
	
	private void splitNode(Node node) {
		int startIndex = node.startIndex_;
		int endIndex = node.endIndex_;
		Dimension d = node.dimension_;
		Rectangle boundary = node.boundary_;
		Neighbors neighbors = node.neighbors_;
		// Let's check to see if we're under the maximum limit for number
		// of points within a partition.  If we are, just return;
		if ((endIndex - startIndex)+1 <= MAX_POINTS_IN_PARTITION) {
			node.line_ = Float.NaN;
			return;
		}
		// Get the median index of the current section of the list.
		int medianIndex = (startIndex + endIndex)/2;
		// Get the median value for the current dimension on the current
		// section of the list.  Since the list.subList fxn takes an endIndex
		// which is exclusive, increment this endIndex by 1.
		float partitionLine = Utils.getMedian(d, points_.subList(startIndex, endIndex+1));
		node.line_ = partitionLine;
		// Now let's build the child nodes of this parent node.  First, alternate
		// to the other dimension.  (We could find the next highly variant 
		// dimension by making a call to Utils.findHighlyVariantDimension on
		// our current sublist, but for now let's just do the simple dimension
		// switching as defined by the Computational Geometry book).
		Dimension nextDim = TreeUtils.switchDimension(d);
		// Create the left node with the correct parameters
		Node leftNode = new Node(nextDim, startIndex, medianIndex, node);
		// Create the right node with the correct parameters
		Node rightNode = new Node(nextDim, medianIndex+1, endIndex, node);
		// Let's build the boundary rectangles & neighbors for the next two partitions
		if (d == Dimension.X) {
			leftNode.boundary_ = new Rectangle(partitionLine, 
					                           boundary.xMin_,
					                           boundary.yMax_,
					                           boundary.yMin_);
			rightNode.boundary_ = new Rectangle(boundary.xMax_,
					                            partitionLine, 
					                            boundary.yMax_,
					                            boundary.yMin_);
			leftNode.neighbors_ = new Neighbors(neighbors.north_,
					                            neighbors.south_,
					                            rightNode,
					                            neighbors.west_);
			rightNode.neighbors_ = new Neighbors(neighbors.north_,
                                                 neighbors.south_,
                                                 neighbors.east_,
                                                 leftNode);
			
		} else {
			// For the Y dimension, the left and right boundaries
			// are actually the top and bottom boundaries
			leftNode.boundary_ = new Rectangle(boundary.xMax_,
                                               boundary.xMin_,
                                               partitionLine,
                                               boundary.yMin_);
			rightNode.boundary_ = new Rectangle(boundary.xMax_,
                                                boundary.xMin_, 
                                                boundary.yMax_,
                                                partitionLine);
			leftNode.neighbors_ = new Neighbors(rightNode,
                                                neighbors.south_,
                                                neighbors.east_,
                                                neighbors.west_);
			rightNode.neighbors_ = new Neighbors(neighbors.north_,
                                                 leftNode,
                                                 neighbors.east_,
                                                 neighbors.west_);
		}
		node.leftNode_ = leftNode;
		node.rightNode_ = rightNode;
		// Split the left and right nodes
		splitNode(leftNode);
		splitNode(rightNode);
	}
    
    /**
     * Assesses whether or not 
     * @param node
     * @param closestPoint
     * @param visitedNodes
     * @return
     */
    private boolean shouldVisitNode(Node node,
                                    Point probePoint,
                                    Point closestPoint, 
                                    Set<Node> visitedNodes) {
        if (node == null) {
            return false;
        }
        if (visitedNodes.contains(node)) {
            return false;
        }
        return TreeUtils.isProbeCloserToBoundary(probePoint,
                                                 closestPoint,
                                                 node.boundary_);
    }
	
	/**
	 * Prints tree such that each successive depth is appropriately
	 * indented.
	 * @param node
	 * @param counter
	 */
	private void printSubTree(Node node, int numSpaces) {
		for (int i=0; i<numSpaces; i++) {
			System.out.print("  ");
		}
		System.out.println(node);
        numSpaces++;
		if (node.leftNode_ != null) {
			printSubTree(node.leftNode_, numSpaces);
		}
		if (node.rightNode_ != null) {
			printSubTree(node.rightNode_, numSpaces  );
		}
	}
	
	/**
	 * Internal class representing a Node of the kd-tree 
	 */
	static class Node {
		final Dimension dimension_;
		final int startIndex_;
		final int endIndex_;
		final Node parentNode_;
		Rectangle boundary_;
		Neighbors neighbors_;
		float line_;
		Node leftNode_;
		Node rightNode_;
		
		Node(Dimension dimension, 
			 int startIndex, 
			 int endIndex, 
			 Node parent) {
			dimension_ = dimension;
			startIndex_ = startIndex;
			endIndex_ = endIndex;
			parentNode_ = parent;
		}
        
        /**
         * Interior nodes will always have two children, so just
         * check that one node is not-null
         * @return
         */
        boolean isInterior() {
            return (leftNode_ != null);
        }
		
		public String toString() {
			StringBuilder b = new StringBuilder(200);
			b.append(dimension_);
			b.append(" (");
			b.append(line_);
			b.append(")");
			b.append(": points [");
			b.append(startIndex_);
			b.append(", ");
			b.append(endIndex_);
			b.append("]");
			/*b.append(" on boundary ");
			b.append(boundary_);*/
			b.append(" neighbors: ");
			b.append(neighbors_);
			return b.toString();
		}
	}
	
	/**
	 * Internal class representing the rectangle boundary of each sub-tree
	 * of a kd-tree
	 */
	static class Rectangle {
		final float xMax_;
		final float xMin_;
		final float yMax_;
		final float yMin_;
		
		Rectangle(float xMax, float xMin, float yMax, float yMin) {
			xMax_ = xMax;
			xMin_ = xMin;
			yMax_ = yMax;
			yMin_ = yMin;
		}
		
		public String toString() {
			StringBuilder b = new StringBuilder(200);
			b.append("[");
			b.append(xMax_);
			b.append(", ");
			b.append(xMin_);
			b.append(", ");
			b.append(yMax_);
			b.append(", ");
			b.append(yMin_);
			b.append("]");
			return b.toString();
		}
	}
	
	static class Neighbors {
		final Node north_;
		final Node south_;
		final Node east_;
		final Node west_;
		
		Neighbors(Node north, Node south, Node east, Node west) {
			east_ = east;
			north_ = north;
			south_ = south;
			west_ = west;
		}
        
        Iterator<Node> getIterator() {
            return new Iterator<Node>() {
                int count = -1;
                
                public boolean hasNext() {
                    return count < 4;
                }
                
                public Node next() {
                    count++;
                    if (count == 0) {
                        return north_;
                    } else if (count == 1) {
                        return south_;
                    } else if (count == 2) {
                        return east_;
                    } else if (count++ == 3) {
                        return west_;
                    }
                    throw new NoSuchElementException("No more neighbors");
                }
                
                public void remove() {
                    throw new UnsupportedOperationException("Removing neighbors is not supported");
                }
            };
        }
        
		public String toString() {
			StringBuilder b = new StringBuilder(200);
			b.append(" N->");
			if (north_ != null) {
				b.append(north_.dimension_);
				b.append(" (");
				b.append(north_.line_);
				b.append(")");
				b.append(": points [");
				b.append(north_.startIndex_);
				b.append(", ");
				b.append(north_.endIndex_);
				b.append("]");
			} else {
				b.append("null");
			}
			b.append(" S->");
			if (south_ != null) {
				b.append(south_.dimension_);
				b.append(" (");
				b.append(south_.line_);
				b.append(")");
				b.append(": points [");
				b.append(south_.startIndex_);
				b.append(", ");
				b.append(south_.endIndex_);
				b.append("]");
			} else {
				b.append("null");
			}
			b.append(" E->");
			if (east_ != null) {
				b.append(east_.dimension_);
				b.append(" (");
				b.append(east_.line_);
				b.append(")");
				b.append(": points [");
				b.append(east_.startIndex_);
				b.append(", ");
				b.append(east_.endIndex_);
				b.append("]");
			} else {
				b.append("null");
			}
			b.append(" W->");
			if (west_ != null) {
				b.append(west_.dimension_);
				b.append(" (");
				b.append(west_.line_);
				b.append(")");
				b.append(": points [");
				b.append(west_.startIndex_);
				b.append(", ");
				b.append(west_.endIndex_);
				b.append("]");
			} else {
				b.append("null");
			}
			return b.toString();
		}
        
	}
}