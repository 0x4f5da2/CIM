package com.chenzhicheng.cim.util;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QDBC {
    public static boolean verify(String... args) throws Exception {
        Socket socket = new Socket("118.89.182.24", 51202);
        PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String st = String.format("select * from test where user='%s' and passwd='%s';", args[0], args[1]);
        pw.println(st);
        pw.flush();
        String str;
        boolean isVerified = false;
        while((str = br.readLine()) != null){
            if(str.contains("seconds"))
                break;
            if(str.contains("rows")){
                String regex = "(\\d+)(\\s*rows\\.)";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(str);
                if(matcher.find()){
                    int t = Integer.parseInt(matcher.group(1));
                    if(t > 0){
                        isVerified = true;
                    }
                }
            }
        }
        socket.close();
        return isVerified;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(QDBC.verify("Jack", "jddk"));
//        String s = "skadjf;lkasdjfl;kasfdj30 rows.alsdjkhfljkasdfhkjl";
//        String regex = "(\\d+)(\\s*rows\\.)";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(s);
//        System.out.println(matcher.find());
//        System.out.println(matcher.groupCount());
//        System.out.println(matcher.group(1));
    }

}
