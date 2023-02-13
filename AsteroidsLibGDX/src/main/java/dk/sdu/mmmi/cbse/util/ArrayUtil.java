package dk.sdu.mmmi.cbse.util;

import dk.sdu.mmmi.cbse.collisions.Mesh;

public class ArrayUtil {


    public static float greatest(float[] arr)
    {
        float current = arr[0];
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > current){
                current = arr[i];
            }
        }
        return current;
    }

    public static float leastMost(float[] arr){
        float current = arr[0];
        for(int i = 0; i < arr.length; i++){
            if(arr[i] < current){
                current = arr[i];
            }
        }
        return current;
    }

    public static float[] greatest(Mesh.Point[] arr)
    {
        float currentX = arr[0].x;
        float currentY = arr[0].y;
        for(Mesh.Point p : arr){
            if(p.x > currentX)
                currentX = p.x;
            if(p.y > currentY)
                currentY = p.y;
        }
        return new float[]{currentX,currentY};
    }
    public static float[] leastMost(Mesh.Point[] arr)
    {
        float currentX = arr[0].x;
        float currentY = arr[0].y;
        for(Mesh.Point p : arr){
            if(p.x < currentX)
                currentX = p.x;
            if(p.y < currentY)
                currentY = p.y;
        }
        return new float[]{currentX,currentY};
    }
    public static String toString(Mesh.Point[] arr)
    {
        StringBuilder toReturn = new StringBuilder();
        for(Mesh.Point p: arr){
            toReturn.append("[").append(p.x).append(",").append(p.y).append("]");
        }
        return toReturn.toString();
    }


    public static void print(Object[] arr){
        StringBuilder build = new StringBuilder();
        for(Object o : arr){
            build.append(o.toString()).append(",");
        }
        System.out.println("Array["+build+"]");
    }

    public static void print(float[] arr){
        StringBuilder build = new StringBuilder();
        for(float f : arr){
            build.append(f).append(",");
        }
        System.out.println("Array["+build+"]");
    }


}
