/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package localspider;

import java.io.*;     // for File
import java.util.*;   // for Scanner

/**
 *
 * @author Sayyed Shozib Abbas
 */
public class LocalSpider {

    public static Map<String, ArrayList<String>> hm;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        hm = new HashMap<String, ArrayList<String>>();
        Scanner console = new Scanner(System.in);
        System.out.print("Directory to crawl? ");
        String directoryName = console.nextLine();
        
        File f = new File(directoryName);
        crawl(f);
        
        System.out.print("Directory to Search? ");
        directoryName = console.nextLine();
        search(directoryName);
    }
    
    public static void search(String s) {
        
        if(hm.containsKey(s)) {
            System.out.println("Word found at:");
            System.out.println(hm.get(s));
        }
        else {
            System.out.println("Given word not found");
        }
    }
    
    public static void crawl(File f) {
    	crawl(f, "", "");
    }
    
    private static void crawl(File f, String indent, String path) {
        path += "/" + f.getName();
        String fileName = f.getName();
        String record = null;
        String[] parts;
        ArrayList<String> temp;
        
        temp = new ArrayList<String>();
        temp.add(path);
        
        if(hm.containsKey(f.getName())) {
            ArrayList<String> temp2 = new ArrayList<String>();
            temp2 = hm.get(f.getName());
            temp2.add(path);
            hm.put(f.getName(), temp2);
        }
        else
            hm.put(f.getName(), temp);
        
        if(hm.containsKey(path)) {
            ArrayList<String> temp2 = new ArrayList<String>();
            temp2 = hm.get(path);
            temp2.add(path);
            hm.put(path, temp2);
        }
        else
            hm.put(path, temp);
        
        parts = f.getName().split("/");
        for(int i = 0; i < parts.length; i++) {
            String[] parts1 = f.getName().split(" ");
            for(int j = 0; j < parts1.length; j++) {
                System.out.println(parts1[j]);
                if(hm.containsKey(parts1[j])) {
                    ArrayList<String> temp2 = new ArrayList<String>();
                    temp2 = hm.get(parts1[j]);
                    temp2.add(parts1[j]);
                    hm.put(parts1[j], temp2);
                }
                else
                    hm.put(parts1[j], temp);
            }
        }
        
        if(f.getName().endsWith(".txt")){

            try
            {
              BufferedReader reader = new BufferedReader(new FileReader(f));
              String line;
              while ((line = reader.readLine()) != null)
              {
                record = line;
              }
              reader.close();
              if(record != null) {
                parts = record.split(" ");
                for(int i = 0; i < parts.length; i++) {
                if(hm.containsKey(parts[i])) {
                    ArrayList<String> temp2 = new ArrayList<String>();
                    temp2 = hm.get(parts[i]);
                    temp2.add(parts[i]);
                    hm.put(parts[i], temp2);
                }
                else
                    hm.put(parts[i], temp);                 
                    
                }
              }
            }
            catch (Exception e)
            {
              System.err.format("Exception occurred trying to read '%s'.", f);
              e.printStackTrace();
            }
        }
        
        
        System.out.println(path);
    	if (f.isDirectory()) {
        	// recursive case: directory
        	// print everything in the directory
        	File[] subFiles = f.listFiles();
    		indent += "    ";
                
        	for (int i = 0; i < subFiles.length; i++) {
                    crawl(subFiles[i], indent, path);
        	}
        }
    }
    
}
