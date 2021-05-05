package edu.uci.ics.jindongy.service.gateway;

public class experiment {
    public static void main(String[] args){
        String url="gate/way/nb";

        System.out.println(url.split("/",2)[0]);
        System.out.println(url.split("/",2)[1]);

    }
}
