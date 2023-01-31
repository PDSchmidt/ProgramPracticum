package controller;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {

    public Test() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ArrayList <String> list = new ArrayList<>();
        list.add("Cameron");
        list.add("Derek");
        list.add("Cool Guy");
        System.out.println(list);
        doubles(list);
        System.out.println(list);
        String s = "There is always one more bug to fix";
        final Scanner scn = new Scanner(s);
        int length = 0;
        while (scn.hasNext()) {
            scn.next();
            length++;
        }
        //System.out.println(length);
        final String[] ary = new String[length];
        final Scanner nu = new Scanner(s);
        int count = 0;
        while (nu.hasNext()) {
            ary[count] = nu.next();
            count++;
        }
        for (int i = 0; i < ary.length; i++) {
            System.out.println(ary[i]);
        }
    }
    
    public static void doubles(ArrayList<String> list) {
        int size = list.size()*2;
        for(int i = 0; i < size; i +=2) {
            list.add(i + 1, list.get(i));
        }
    }

}
