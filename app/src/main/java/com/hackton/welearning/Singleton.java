package com.hackton.welearning;

/**
 * Created by kbf384 on 3/21/2018.
 */

public class Singleton {

    private static Singleton instance = null;
    protected Singleton() {
        // Exists only to defeat instantiation.
    }
    public static Singleton getInstance() {
        if(instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    private String val;

    public String getValue() {
        return val;
    }

    public void setValue(String value) {
        this.val = value;
    }


}


