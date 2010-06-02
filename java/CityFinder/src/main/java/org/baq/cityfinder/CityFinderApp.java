package org.baq.cityfinder;

import java.util.List;

import org.baq.kdtree.KDTree;
import org.baq.kdtree.Point;

public class CityFinderApp {

    private final KDTree kdTree_;
    
    public CityFinderApp(List<Point> points) {
        kdTree_ = new KDTree(points);
    }
    
    public String getClosestCity(Point probe) {
        Point cityPoint = kdTree_.findNearestPoint(probe);
        return cityPoint.getMetaData().get(CityDefs.CITY_NAME);
    }
}
