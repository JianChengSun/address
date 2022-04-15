package com.csc.address;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 读取JSON文件中的城市数据
 * @author jean
 * @date 2022/4/15
 */
public class LocationDic {


    public static  HashMap<Character, Set<City>> getCityMap() {
        HashMap<Character, Set<City>> cityMap = new HashMap<Character, Set<City>>();
        try {
            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("cities.json");
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new GsonBuilder().create();

            // Read file in stream mode
            reader.beginArray();
            while (reader.hasNext()) {
                // Read data into object model
                CityJson cityJson = gson.fromJson(reader, CityJson.class);
                if(cityJson != null){
                    Set<City> cities = cityMap.get(cityJson.getFirstChar());
                    if(cities == null){
                        cityMap.put(cityJson.getFirstChar(), new HashSet<City>(Collections.singleton((City)cityJson)));
                    }
                    else{
                        cities.add(cityJson);
                        cityMap.put(cityJson.getFirstChar(), cities);
                    }
                }
            }
            reader.close();
        } catch (UnsupportedEncodingException ex) {
        } catch (IOException ex) {
        }

        return cityMap;
    }
}
