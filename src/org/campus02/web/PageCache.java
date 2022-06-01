package org.campus02.web;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class PageCache {

//    public static void main(String[] args) {
//        PageCache pageCache = new PageCache();
//        pageCache.warmUp(".\\data\\demo_urls.txt");
//        System.out.println(pageCache.cache.keySet());
//    }

    private HashMap<String, WebPage> cache = new HashMap<>();

    public WebPage readFromCache(String url) throws CacheMissException {
        if(!cache.containsKey(url)){
            throw new CacheMissException("url not in cache");
        }
        return cache.get(url);
    }

    public void writeToCache(WebPage webPage){
        cache.put(webPage.getUrl(), webPage);
    }

    public void warmUp(String pathToUrls){
        try(BufferedReader br = new BufferedReader(new FileReader(pathToUrls))) {

            String url;
            while((url = br.readLine()) != null){
                try{
                    WebPage webPage = UrlLoader.loadWebPage(url);
                    writeToCache(webPage);
                } catch (UrlLoaderException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, WebPage> getCache() {
        return cache;
    }
}
