package org.baq.kdtree;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Yeah it's a bad name for a class so sue me.  This class contains
 * publicly available utility methods that the outside world may
 * want to use.
 * @author Baq
 *
 */
public class Utils {

    /**
     * Returns the euclidean distance between the two points.
     * @param p1
     * @param p2
     * @return
     */
    public static float getEuclideanDistance(Point p1, Point p2) {
        float a = p1.x_ - p2.x_;
        float b = p1.y_ - p2.y_;
        float c = (float)Math.sqrt(a*a + b*b);
        return c;
    }
    
    /**
     * Finds the point in the node that's closest to the given point
     * @param point
     * @param node
     * @return
     */
    public static Point getClosestPointInList(Point point,
                                              List<Point> points) {
        float minDistance = Float.MAX_VALUE;
        Point closestPoint = null;
        for (Point currPoint : points) {
            float distance = getEuclideanDistance(point, currPoint);
            if (distance < minDistance) {
                minDistance = distance;
                closestPoint = currPoint;
            }
        }
        return closestPoint;
    }
    
    // A comparator that compares the y-coordinates of a point
    private final static Comparator<Point> Y_COMPARATOR = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            return (int)(p1.y_ - p2.y_);
        }
    };
    // A comparator that compares the x-coordinates of a point
    private final static Comparator<Point> X_COMPARATOR = new Comparator<Point>() {
        public int compare(Point p1, Point p2) {
            return (int)(p1.x_ - p2.x_);
        }
    };
    /**
     * Gets the median value on the given dimension against the given list of
     * points.
     * @param dimension
     * @param points
     * @return
     */
    public static float getMedian(Dimension dimension, List<Point> points) {
        Comparator<Point> c = (dimension == Dimension.X ? X_COMPARATOR : 
            Y_COMPARATOR);
        Collections.sort(points, c);
        Point medianPoint = points.get((points.size()-1)/2);
        return (dimension == Dimension.X ? medianPoint.x_ : medianPoint.y_);
    }
    
    // --------------------------------------------------
    // Quicksort implementation
    // --------------------------------------------------   
    public static void quicksort(float[] A) {
        if (A.length < 2) {
            return;
        }
        qsort(A, 0, A.length-1);
    }
    
    private static void qsort(float[] A, int pivot, int end) {
        if (pivot < end) {
            int q = partition(A, pivot, end);
            qsort(A, pivot, q);
            qsort(A, q+1, end);
        }
    }
    
    private static int partition(float[] A, int pivot, int end) {
        float pivotValue = A[pivot];
        int i = pivot;// - 1;
        int j = end;// + 1;
        while (true) {
            // Keep moving the right index to the left
            // until a value is found that is less than
            // or equal to the pivot value.
            while (A[j] > pivotValue) {
                j--;
            }
            // Keep moving the left index to the right
            // until a value is found that is greater
            // than or equal to the pivot value
            while (A[i] < pivotValue) {
                i++;
            }
            if (i < j) { 
                // If we've reached this point, then we've 
                // found values that are "misplaced" in the
                // array. So swap them.
                float temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            } else {
                // If we've reached here, then all values are
                // in the right "halves" of the array
                return j;
            }
        }
        
    }
    
    // Discourage instances of this class
    private Utils() {}
    
}
