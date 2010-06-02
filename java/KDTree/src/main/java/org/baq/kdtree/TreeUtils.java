package org.baq.kdtree;

import java.util.Comparator;
import java.util.List;

/**
 * Static helper methods for various computations required by a
 * kd-tree.  
 * @author Baq
 */
class TreeUtils {
	
	/**
	 * Finds the dimension with the greatest variance in the internal list of
	 * points.
	 * @return
	 */
	static Dimension findHighlyVariantDimension(List<Point> points) {
		// First compute average of each dimension
		float sumX = 0.0f;
		float sumY = 0.0f;
		for (Point p : points) {
			sumX += p.x_;
			sumY += p.y_;
		}
		float avgX = sumX/points.size();
		float avgY = sumY/points.size();
		
		// Now we have the average, let's compute the
		// variance
		float sumXVariance = 0.0f;
		float sumYVariance = 0.0f;
		for (Point p : points) {
			float xDiff = p.x_ - avgX;
			sumXVariance += (xDiff*xDiff);
			float yDiff = p.y_ - avgY;
			sumYVariance += (yDiff*yDiff);
		}
		
		float varianceX = sumXVariance/points.size();
		float varianceY = sumYVariance/points.size();
		if (varianceX >= varianceY) {
			return Dimension.X;
		}
		return Dimension.Y;
	}
	
	/**
	 * Returns the opposite dimension of the given dimension, i.e. if d is
	 * X, this function will return Y, and vice versa
	 * @param d 
	 * @return
	 */
	static Dimension switchDimension(Dimension d) {
		if (d == Dimension.X) {
			return Dimension.Y;
		}
		return Dimension.X;
	}
	
	/**
	 * Finds the likeliest node of the tree where this point should have
	 * been found were it in the tree.
	 * @param point
	 * @return
	 */
	static KDTree.Node findLikeliestNode(KDTree.Node node, Point point) {
		// If we've hit a leaf then this is the leaf node where
		// this point belongs.  Exit up the call stack.
		if (node.leftNode_ == null && node.rightNode_ == null) {
			return node;
		}
		// Get the dimension the current node is split on
		Dimension d = node.dimension_;
		float dimensionToCompare = (d == Dimension.X ? point.x_ : point.y_); 
		if (dimensionToCompare <= node.line_) {
			return findLikeliestNode(node.leftNode_, point);
		} else {
			return findLikeliestNode(node.rightNode_, point);
		}
	}
	
	static boolean isProbeCloserToBoundary(Point probe, 
	                                       Point candidatePoint,
	                                       KDTree.Rectangle boundary) {
		// Get the distance of our point from the candidate closest point
		float pointDistance = Utils.getEuclideanDistance(probe, candidatePoint);
		// Get the distance of our point from the closest boundary
		float minX = Math.min(Math.abs(boundary.xMax_ - probe.x_), 
				              Math.abs(boundary.xMin_ - probe.x_));
		float minY = Math.min(Math.abs(boundary.yMax_ - probe.y_), 
	                          Math.abs(boundary.yMin_ - probe.y_));
		float boundaryDist = Math.min(minX, minY);
		
		// If the distance of our candidate point is closer than or equal
		// to the boundary distance, then this will return true; otherwise
		// false.
		return boundaryDist <= pointDistance;
	}
	
	static class NodeComparator implements Comparator<KDTree.Node> {
		
		final Point point_;
		
		NodeComparator(Point point) {
			point_ = point;
		}
		
		public int compare(KDTree.Node n1, KDTree.Node n2) {
			KDTree.Rectangle boundary1 = n1.boundary_;
			// Get the distance of our point from the closest n1 boundary
			float minX1 = Math.min(Math.abs(boundary1.xMax_ - point_.x_), 
					               Math.abs(boundary1.xMin_ - point_.x_));
			float minY1 = Math.min(Math.abs(boundary1.yMax_ - point_.y_), 
		                           Math.abs(boundary1.yMin_ - point_.y_));
			float minDist1 = Math.min(minX1, minY1);
			
			KDTree.Rectangle boundary2 = n2.boundary_;
			// Get the distance of our point from the closest n2 boundary
			float minX2 = Math.min(Math.abs(boundary2.xMax_ - point_.x_), 
					               Math.abs(boundary2.xMin_ - point_.x_));
			float minY2 = Math.min(Math.abs(boundary2.yMax_ - point_.y_), 
		                           Math.abs(boundary2.yMin_ - point_.y_));
			float minDist2 = Math.min(minX2, minY2);
			
			return (int)(minDist1 - minDist2);
		}
		
	}
	
	
	// Disallow construction of this class
	private TreeUtils() {}
}
