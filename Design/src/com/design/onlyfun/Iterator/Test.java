package com.design.onlyfun.Iterator;
/**
 *
 */
import java.io.*;
import java.util.*;

public class Test  {
    public static void main(String[] args) {
    	String path= Test.class.getResource("/").getPath();
//    	System.out.println(path);
        String fileName = path+"data.txt";
        DataVector dataVector = new DataVector(fileName);
        Iterator iVector = dataVector.CreateIterator();
        for(iVector.First(); ! iVector.IsDone(); iVector.Next()) {
            iVector.CurrentItem();
        }
    }
}