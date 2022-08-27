package com.example.microblinkdemo.util;

import java.util.*;

public class Helper {

    public static  <T> Set<T> findDuplicates(Collection<T> collection) {
        Set<T> duplicates = new HashSet<>();
        Set<T> uniques = new HashSet<>();

        for(T t : collection) {
            if(!uniques.add(t)) {
                duplicates.add(t);
            }
        }

        return duplicates;
    }
}
