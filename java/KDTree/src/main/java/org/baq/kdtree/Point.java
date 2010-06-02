package org.baq.kdtree;

import java.util.Map;

public class Point {

	protected final float x_;
	protected final float y_;
	private Map<String, String> metaData_;
    
	public Point(float x, float y) {
		x_ = x;
		y_ = y;
	}
	
    public void setMetaData(Map<String, String> metaData) {
        metaData_ = metaData;
    }
    
    public Map<String, String> getMetaData() {
        return metaData_;
    }
    
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Point)) {
			return false;
		}
		Point p = (Point)obj;
		return (x_ == p.x_ && y_ == p.y_);
	}
	
	public String toString() {
		StringBuilder b = new StringBuilder(10);
		b.append("[");
		b.append(x_);
		b.append(", ");
		b.append(y_);
		b.append("]");
		return b.toString();
	}
}
