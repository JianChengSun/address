package com.csc.address;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jean
 * @date 2022/4/15
 */
public class Analyzer {

    public static City analyzeString(String address){
        if(address == null || address.length() < 2){
            return null;
        }

        HashMap<Character, Set<City>> cityMap = LocationDic.getCityMap();

        char[] addressChars = address.toCharArray();
        int length = addressChars.length;
        City result = null;
        Long parentId = 0L;
        for (int i = 0; i < length; i++){
            Character addressCharacter = addressChars[i];
            Set<City> cities = cityMap.get(addressCharacter);
            if(cities != null){
                City city = matchCities(addressCharacter.toString(), i, addressChars, new ArrayList<>(cities), parentId);
                if(city != null){
                    parentId = city.getId();
                    result = city;
                }
            }
        }
        return result;
    }

    private static City matchCities(String matchedPart, int index, char[] addressCharacters, List<City> cities, Long parentId){
        int length = addressCharacters.length;
        int nextNodeIndex = index + 1;
        if(nextNodeIndex < length){
            String matchStart = matchedPart + addressCharacters[nextNodeIndex];
            List<City> remainCities;
            if(parentId > 0){
                remainCities  = cities.stream().filter(q->q.getName().startsWith(matchStart) && parentId.equals(q.getParentId())).collect(Collectors.toList());
            }
            else{
                remainCities  = cities.stream().filter(q->q.getName().startsWith(matchStart)).collect(Collectors.toList());
            }
            if(remainCities.size() == 1){
                return remainCities.get(0);
            }
            else if(remainCities.size() == 0){
                return null;
            }
            else{
                //当命中多个的集合是同名的省，市时返回市
                List<Long> ids = remainCities.stream().map(City::getId).collect(Collectors.toList());
                List<Long> parentIds = remainCities.stream().map(City::getParentId).collect(Collectors.toList());
                ids.removeAll(parentIds);
                if(ids.size() == 1){
                    return remainCities.stream().filter(q->q.getId().equals(ids.get(0))).findFirst().get();
                }
                else{
                    return matchCities(matchStart, nextNodeIndex, addressCharacters, remainCities, parentId);
                }
            }
        }
        else{
            return null;
        }
    }
}
