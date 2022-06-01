package org.campus02.web;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket client;
    private WebProxy proxy;

    public ClientHandler(Socket client, WebProxy proxy) {
        this.client = client;
        this.proxy = proxy;
    }

    private void start(){
        try(BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

            String input;
            while((input = br.readLine()) != null){
                if(input.equalsIgnoreCase("bye")){
                    System.out.println("client wants to exit");
                    bw.write("good bye!");
                    bw.newLine();
                    bw.flush();
                    client.close();
                    break;
                }

                String[] cmds = input.split(" ");
                if(cmds.length != 2){
                    bw.write("invalid command");
                    bw.newLine();
                    bw.flush();
                    continue;
                }

                switch (cmds[0]){
                    case "fetch":
                        try {
                            WebPage webPage = proxy.fetch(cmds[1]);
                            bw.write(webPage.getContent());
                        } catch (UrlLoaderException e) {
                            bw.write("error: loading url failed");
                        }
                        break;
                    case "stats":
                        if(cmds[1].equalsIgnoreCase("hits")){
                            bw.write(proxy.statsHits());
                        } else if(cmds[1].equalsIgnoreCase("misses")){
                            bw.write(proxy.statsMisses());
                        } else {
                            bw.write("error: invalid command");
                        }
                        break;
                    default:
                        bw.write("error: invalid command");
                }
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        start();
    }
}
