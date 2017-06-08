package com.brightEdge.keywordSearch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by niksat21 on 5/11/2017.
 */
public class Driver {

    private static com.brightEdge.keywordSearch.HTMLParser htmlParser;
    public static void main(String[] args) throws IOException, URISyntaxException {
        try{
            String url="";
            if(args.length>0){
                url=args[0];
            }else{
                url = "https://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster";
            }

//         url = "http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/";

            com.brightEdge.keywordSearch.KeywordMatch keywordMatch = new com.brightEdge.keywordSearch.KeywordMatch();
            htmlParser = new HTMLParser(url);
            SearchRule searchRule = new SearchRule();
            searchRule.parseStopWords();


            searchRule.searchOnTitle(htmlParser.getTitle());
            searchRule.searchOnDescription(htmlParser.getDescription());
            searchRule.searchOnImage(htmlParser.getImages());
            searchRule.searchOnKeyword(htmlParser.getKeyword());
            searchRule.searchOnLink(htmlParser.getLinks());
            searchRule.searchOnHeading(htmlParser.getHeadings());
            searchRule.searchOnContent(htmlParser.getContent());


            SortMap sortMap = new SortMap(SearchRule.wordFreq);

            Map<String,Integer> sorted_map = new TreeMap<String, Integer>(sortMap);
            sorted_map.putAll(SearchRule.wordFreq);



            keywordMatch.matchOnTitle(htmlParser.getTitle());
            keywordMatch.matchOnDescription(htmlParser.getDescription());
            keywordMatch.matchOnContent(htmlParser.getContent());
            keywordMatch.matchOnLinks(htmlParser.getLinks());
            keywordMatch.matchOnImages(htmlParser.getImages());
            keywordMatch.matchOnHeading(htmlParser.getHeadings());
            keywordMatch.matchOnKeyword(htmlParser.getDescription());

            keywordMatch.doKeywordMatch();
        }catch (Exception e){
            e.printStackTrace();
        }





    }

    HTMLParser getHtmlParser(){
        return htmlParser;
    }
}
