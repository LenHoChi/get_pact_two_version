package com.example.test1;

import java.io.*;

public class Test {
    public static void main(String[] args) throws Exception {
        String anyCommand="kafka-server-start.bat C:\\kafka_2.12-2.7.0\\config\\server.properties";
//        String anyCommand1 ="zookeeper-server-start.bat C:\\kafka_2.12-2.7.0\\config\\zookeeper.properties\n";
        String anyCommand1 ="zookeeper-server-start.bat C:\\Users\\len.ho\\Downloads\\kafka_2.12-2.7.02\\config\\zookeeper.properties\n";
        String anyCommand2="kafka-topics.bat --list --zookeeper localhost:2181";
        //zookeeper-server-start.bat C:\kafka_2.12-2.7.02\config\zookeeper.properties


        try {
            //Process process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K " + anyCommand);
            Process process1 = Runtime.getRuntime().exec("cmd /c start cmd.exe /K"+anyCommand1);
           // Process process = Runtime.getRuntime().exec("cmd.exe /c ..\\scripts\\start-zookeeper.cmd");
            // Process process2 = Runtime.getRuntime().exec("cmd /c start cmd.exe /K " + anyCommand2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}