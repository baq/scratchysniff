package org.baq.cityfinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.baq.kdtree.Point;

class CityUtils {
    
    static Point parseLineFromFile(String line) throws IOException {
        StringTokenizer t = new StringTokenizer(line, "\t");
        if (t.countTokens() != CityDefs.NUM_TOKENS_FILE) {
            throw new IOException("Poorly formatted line");
        }
        
        String cityName = t.nextToken();
        float x = 0.0f;
        float y = 0.0f;
        
        try {
            y = Float.parseFloat(t.nextToken());
        } catch (NumberFormatException e) {
            throw new IOException("Latitude is not a number");
        }
        
        try {
            x = Float.parseFloat(t.nextToken());
        } catch (NumberFormatException e) {
            throw new IOException("Longitude is not a number");
        }
        
        Point point = new Point(x, y);
        Map<String, String> metaData = new HashMap<String, String>();
        metaData.put(CityDefs.CITY_NAME, cityName);
        point.setMetaData(metaData);
        
        return point;
    }
    
    static Point parseLineFromQuery(String line) throws IOException {
        StringTokenizer t = new StringTokenizer(line, ",");
        if (t.countTokens() != CityDefs.NUM_TOKENS_QUERY) {
            throw new IOException("Poorly formatted input, should be <long, lat>");
        }
        
        float x = 0.0f;
        float y = 0.0f;
        
        try {
            y = Float.parseFloat(t.nextToken().trim());
        } catch (NumberFormatException e) {
            throw new IOException("Latitude is not a number");
        }
        
        try {
            x = Float.parseFloat(t.nextToken().trim());
        } catch (NumberFormatException e) {
            throw new IOException("Longitude is not a number");
        }
        
        return new Point(x, y);
    }
    
}
