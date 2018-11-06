package com.example.fanganyi.coinz;

public class DownloadCompleteRunner {

    private static String result;

    public static void downloadComplete(String result) {
        DownloadCompleteRunner.result = result;
    }

    public static  String getResult(){
        return result;
    }

}
