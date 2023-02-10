package dk.sdu.mmmi.cbse.util;

public class ArrayUtil {


    public static float greatest(float[] arr)
    {
        float current = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > current){
                current = i;
            }
        }
        return current;
    }

    public static float leastMost(float[] arr){
        float current = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i] < current){
                current = i;
            }
        }
        return current;
    }


}
