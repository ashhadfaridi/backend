package com.iastate.cloud.myapp.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * Author @ Pawan Namagiri
 **/


public class Test {

    public static Map<Double, int[]> kClosest(int[][] points, int k) {

        Map<Double, int[]> map = new TreeMap<Double, int[]>();

        int res[][] = new int[k][2];

        for (int i = 0; i < points.length; i++) {


            int x = points[i][0];
            int y = points[i][1];



            double dist =  Math.sqrt((x * x) + (y * y));
            System.out.println(dist);
            int arr[] = {x, y};
            map.put(dist, arr);


        }


        return map;
    }

    public static void main(String[] args) {

        int points[][] = { { 3, 3 },
                { 5, -1 },
                { -2, 4 } };
        System.out.println(Test.kClosest(points,2));
    }
}
