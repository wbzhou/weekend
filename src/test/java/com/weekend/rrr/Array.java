package com.weekend.rrr;

/**
 * Created by weekend on 2019/2/23.
 */
public class Array {

    public void find (int[]arr){
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
    public int findmax(int[] arr){
        int max=arr[0];
        for(int i=0;i<arr.length;i++){
            if(max<arr[i]){
                max=arr[i];
            }
        }
        return max;
    }
    public static void main (String[] args){
        Array a =new Array();
        int arr[]={3,4,5,9};
        int b=a.findmax(arr);
        System.out.println(b);
    }
}
