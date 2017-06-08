package com.brightEdge.keywordSearch;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by niksat21 on 5/11/2017.
 */

class KeywordMatch {

    private static SearchRule searchRule;
    private static TreeMap<String, Integer> sorted_mapRes;
    private static Map<String, Integer> result;
    private static List<String> finalAns;


    KeywordMatch() {
        searchRule = new SearchRule();
        sorted_mapRes = new TreeMap<String, Integer>();
        result = new HashMap<String, Integer>();
        finalAns = new ArrayList<String>();


    }

    void doKeywordMatch() throws IOException {



        SortMap sortMapRes = new SortMap(result);
        sorted_mapRes = new TreeMap<String, Integer>(sortMapRes);
        sorted_mapRes.putAll(result);
        removeUnreal();
        System.out.println("final ans .......");
        System.out.println(finalAns);


    }

      void matchOnTitle(Element title){
        findKNbrs(title.text());
    }

      void matchOnDescription(Element description){

        findKNbrs(description.text());

    }
      void matchOnImages(Elements image){
          for (Element el : image) {

              String temp = el.attr("alt");
              findKNbrs(temp);

          }

    }
      void matchOnLinks(Elements link){

          for (Element el : link) {

              String temp = el.attr("a[href]");
              findKNbrs(temp);

          }

    }
      void matchOnHeading(Elements headings){


          for (Element head : headings) {

              String temp = head.text();
              findKNbrs(temp);
          }

    }
     void matchOnContent(Elements content){

         for (Element head : content) {

             String temp = head.text();
             findKNbrs(temp);
         }

    }

      void matchOnKeyword(Element keyword){

              String temp = keyword.text();
              findKNbrs(temp);


    }

    private static void findKNbrs(String s) {

        String[] temp = s.split("\\s+");
        if (temp.length > 1) {
            for (int i = 3; i < temp.length; i++) {
                densityGenerator(temp[i - 3], temp[i]);
                densityGenerator(temp[i - 2], temp[i]);
                densityGenerator(temp[i - 1], temp[i]);

            }
        }

        System.out.println();

    }

    private static void densityGenerator(String k, String l) {

        String temp = "";


        if (k != null && l != null) {
            k = k.toLowerCase();
            l = l.toLowerCase();

            if (SearchRule.wordFreq.containsKey(k) && SearchRule.wordFreq.containsKey(l)) {

                temp = k + " " + l;
                result.put(temp, searchRule.getWordFreq().get(k) + searchRule.getWordFreq().get(l));

            }
        }

    }

    private static void removeUnreal() {

        MaxentTagger tagger = new MaxentTagger("tagger/english-left3words-distsim.tagger");

        for (String str : sorted_mapRes.keySet()) {
            String pos = tagger.tagString(str);
            if (pos.contains("_NN")) {
                finalAns.add(str);
            }

        }
    }
}
