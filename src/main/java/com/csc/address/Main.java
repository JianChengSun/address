package com.csc.address;

/**
 * @author jean
 * @date 2022/4/15
 */
public class Main {

    public static void main(String[] args) {
        City city = Analyzer.analyzeString("江苏无锡");

        if(city == null){
            System.out.println("未命中");
        }
        else{
            System.out.println(city.getName() + "Id:" + city.getId());
        }
    }
}
