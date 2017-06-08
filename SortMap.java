package com.brightEdge.keywordSearch;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by niksat21 on 5/10/2017.
 */
public class SortMap implements Comparator<String> {

    Map<String, Integer> base;

    public SortMap(Map<String, Integer> wordFreq) {
        this.base=wordFreq;
    }


    // Note: this comparator imposes orderings that are inconsistent with
    // equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
