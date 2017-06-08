package com.brightEdge.keywordSearch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by niksat21 on 5/11/2017.
 */
public class HTMLParser {


    private String url;
    private Document document;

    private Element title;

    HTMLParser(String url) throws IOException {
        this.url = url;
        document = Jsoup.connect(url).get();
    }

    Element getTitle(){
        return document.getElementsByTag("title").first();
    }

    Elements getImages(){

        return document.getElementsByTag("img");

    }

    Element getDescription(){
        return document.select("meta[name=description]").first();
    }

    Elements getContent(){
        return document.getElementsByTag("meta");
    }
    Elements getLinks(){

        return document.getElementsByTag("a[href]");

    }
    Elements getHeadings(){

        return document.select("h1");
    }

    protected Element getBody(){

        return document.body();
    }

    Elements getKeyword(){
        return document.select("meta[name=keywords]");
    }







}
