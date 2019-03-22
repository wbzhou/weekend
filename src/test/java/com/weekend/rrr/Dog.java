package com.weekend.rrr;

/**
 * Created by weekend on 2019/2/22.
 */
public class Dog {
    private String name;
    private String strain;

    public Dog(String name, String strain) {
        this.name = name;
        this.strain = strain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrain() {
        return strain;
    }

    public void setStrain(String strain) {
        this.strain = strain;
    }

    public void find (int[]arr){
        for (int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }
    }
}
