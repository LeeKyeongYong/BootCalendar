package com.calendar.demo2.standard.util;

public class Ut {
    public static class str{
        public static String padWithZeros(int value,int len){
            return padWithZeros(String.valueOf(value),len);
        }
        public static String padWithZeros(String value,int len){
            StringBuilder sb = new StringBuilder(value);
            while(sb.length()<len){
                sb.insert(0,'0');
            }
            return sb.toString();
        }
    }
}
