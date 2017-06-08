package com.brightEdge.keywordSearch;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by niksat21 on 5/10/2017.
 */

class SearchRule {

    private static Set<String> stopWords;
     static Map<String,Integer> wordFreq;
    private String titleText;
    private String descriptionText;
    private List<String> contentText;
    private String bodyText;
    private List<String> linkText;
    private List<String> imageText;
    private List<String> headingText;
    private List<String> keywordText;


    SearchRule() {
        stopWords = new HashSet<String>();
        wordFreq = new HashMap<String, Integer>();
    }


    void searchOnTitle(Element title){
        titleText = title.text();

        performSearch(titleText);
    }

    void searchOnBody(Element body){
        bodyText = body.text();
        performSearch(bodyText);
    }

    void searchOnDescription(Element description){
        descriptionText = description.text();
        performSearch(descriptionText);
    }

    void searchOnHeading(Elements headings){
        headingText = new ArrayList<String>();
        for (Element head : headings) {

            String temp = head.text();
            headingText.add(temp);
            performSearch(temp);
        }

    }

    void searchOnImage(Elements image){
        imageText = new ArrayList<String>();
        for (Element el : image) {

            String temp = el.attr("alt");
            imageText.add(temp);
            performSearch(temp);

        }
    }

    void searchOnLink(Elements link){
        linkText = new ArrayList<String>();
        for (Element el : link) {

            String temp = el.attr("a[href]");
            linkText.add(temp);
            performSearch(temp);

        }

    }

    void searchOnKeyword(Elements keywords){
        keywordText = new ArrayList<String>();
        for (Element kw : keywords) {

            String temp = kw.attr("content");
            keywordText.add(temp);
            performSearch(temp);
        }

    }

    void searchOnContent(Elements contents){

        contentText = new ArrayList<String>();
        for (Element cn : contents) {

            String temp = cn.attr("content");
            performSearch(temp);
        }
    }


    private static void performSearch(String titleText){
        titleText = removePunc(titleText);
        keywordFinder(titleText);
    }



    void parseStopWords() throws IOException, URISyntaxException {
        try {

            BufferedReader br = new BufferedReader(new FileReader("stopWordList.txt"));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                stopWords.add(sCurrentLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String removePunc(String s) {
        String str = s.trim();

        str = str.replace("!", "");
        str = str.replace("?", "");
        str = str.replace("\\", "");
        str = str.replace("/", "");
        str = str.replace(",", "");
        str = str.replace("(", "");
        str = str.replace(")", "");
        str = str.replace("<", "");
        str = str.replace(">", "");
        str = str.replace("#", "");
        str = str.replace("\"", "");
        str = str.replace("+", "");
        str = str.replace(":", "");
        str = str.replace("=", "");
        str = str.replace("--", "");
        str = str.replace("*", "");
        str = str.replace("\\s+", "");


        if (str.startsWith("'") || str.endsWith("'")) {
            str = str.replace("'", "");
        }

        if (str.endsWith(".")) {
            str = str.replace(".", "");
        }

        return str;

    }

    private static void keywordFinder(String s) {

        s = s.trim();
        String[] temp = s.split("\\s+");
        for (String str : temp) {
            str = removePunc(str);
            str = str.trim();
            str = str.toLowerCase();
            if (!stopWords.contains(str)) {
                if (!wordFreq.containsKey(str)) {
                    wordFreq.put(str.trim(), 1);
                } else {
                    wordFreq.put(str.trim(), wordFreq.get(str) + 1);
                }
            }

        }

    }

    Map<String,Integer> getWordFreq(){
        return wordFreq;
    }

    String getTitleText() {
        System.out.println(titleText);
        return titleText;
    }

    String getDescriptionText() {
        return descriptionText;
    }

    List<String> getContentText() {
        return contentText;
    }

    public String getBodyText() {
        return bodyText;
    }

    List<String> getLinkText() {
        return linkText;
    }

    List<String> getImageText() {
        return imageText;
    }

    List<String> getHeadingText() {
        return headingText;
    }

     List<String> getKeywordText() {
        return keywordText;
    }
}
