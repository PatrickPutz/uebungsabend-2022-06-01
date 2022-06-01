package org.campus02.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class UrlLoader {

//    public static void main(String[] args) throws UrlLoaderException {
//        WebPage webPage = loadWebPage("https://www.news.at");
//        System.out.println("webPage = " + webPage.getContent());
//    }

    public static WebPage loadWebPage(String url) throws UrlLoaderException {

        try {
            URL webUrl = new URL(url);
            try(BufferedReader br = new BufferedReader(new InputStreamReader(webUrl.openStream()))) {

                StringBuilder content = new StringBuilder();
                String line;

                while((line = br.readLine()) != null){

                    content.append(line);

                }

                return new WebPage(url, content.toString());
            } catch (IOException e) {
                throw new UrlLoaderException("error reading from Url", e);
            }
        } catch (MalformedURLException e) {
            throw new UrlLoaderException("error reading from Url", e);
        }

    }

}
