package org.baq.cityfinder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.baq.kdtree.Point;
import org.baq.kdtree.Utils;

public class CityFinderStart {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("No latitude/longitude file specified");
            System.exit(-1);
        }
        
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(args[0]));
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        
        
        List<Point> points = null;
        int lineNumber = 1;
        try {
            String currentLine = in.readLine();
            points = new ArrayList<Point>();
            while (currentLine != null) {
                points.add(CityUtils.parseLineFromFile(currentLine));
                currentLine = in.readLine();
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println(lineNumber + ": " + e.getMessage());
            System.exit(-1);
        }
        
        if (points == null || points.size() < 1) {
            System.err.println("No valid cities found in file.");
            System.exit(-1);
        }
        
        CityFinderApp app = new CityFinderApp(points);
        System.out.println("CityFinder has been successfully initialized.");
        System.out.println("To exit, type 'exit'.");
        System.out.println("Please enter points as <latitude, longitude>:");
        System.out.print("CityFinder>");
        
        boolean isRunning = true;
        
        try {
            BufferedReader prompt = new BufferedReader(new InputStreamReader(System.in));
            while (isRunning) {
                String line = prompt.readLine().trim();
                if (line.equalsIgnoreCase("exit")) {
                    isRunning = false;
                } else {
                    Point queryPoint = CityUtils.parseLineFromQuery(line);
                    long startTime = System.currentTimeMillis();
                    String city = app.getClosestCity(queryPoint);
                    long endTime = System.currentTimeMillis();
                    float totalTime = (endTime - startTime)/1000.0f;
                    System.out.println("[KDTree: (" + totalTime + " s)] The closest city is " + city);
                    startTime = System.currentTimeMillis();
                    Point point = Utils.getClosestPointInList(queryPoint, points);
                    city = point.getMetaData().get(CityDefs.CITY_NAME);
                    endTime = System.currentTimeMillis();
                    totalTime = (endTime - startTime)/1000.0f;
                    System.out.println("[Brute : (" + totalTime + " s)] The closest city is " + city);
                    System.out.print("CityFinder>");
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Program successfully shut down");
        System.exit(1);
    }
    
}
