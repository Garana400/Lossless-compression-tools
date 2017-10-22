/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzw;

import java.io.*;
import java.util.*;
import java.lang.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author lenovo
 */
public class LZW {

   
    public static void writeInFile(String fileName,Vector<Integer> vec)
    {
        try (FileWriter file=new FileWriter(fileName))
        {
            for(int i=0 ; i<vec.size();i++)
            {
                file.write(((int)vec.elementAt(i))+" ");
            }
            file.close();
        }
        catch(IOException e){
                System.out.print("Error");
        }
    }
    public static Vector<Integer> readFromFile(String fileName) 
    {
        Vector<Integer> vec=new Vector<Integer>();
        try(FileReader file=new FileReader(fileName)){
        Scanner f=new Scanner(file);
        while(f.hasNext())
        {
            int temp=f.nextInt();
            vec.addElement(temp);
        }
              
         file.close();   
        }
        catch(IOException e){
                System.out.print("Error: file not found");
            }
        return vec;
    }
    public static Vector<Integer> compress(String input)
    {
        Vector vec=new Vector();
        HashMap<String,Integer> map=new HashMap<String,Integer>();
       
        String  p="",found;
        char c;
        int cnt=128;
        for(int i=0 ; i<128 ;i++)
            map.put(Character.toString((char)i), i);
        for(int i=0 ; i<input.length() ; i++)
        {
            c = input.charAt(i);
            p+=c;
                   
            if(!map.containsKey(p))
            {
                
                map.put(p, cnt);
                cnt++;
                found=p.substring(0, p.length()-1);
                vec.addElement(map.get(found));
                p=Character.toString(c);
            }
           if(i==input.length()-1 && ( (map.containsKey(p))))
           {
               vec.addElement(map.get(p));
           }
          
        }
        for(int j=0 ; j<vec.size() ; j++)
        {
            System.out.print("<"+vec.elementAt(j)+"> ");
        }
        writeInFile("LZW.txt",vec);
        return vec;
        
    }
    public static String decompress(String fileName)
    {
        Vector<Integer> vec=new Vector<Integer>();
        vec=readFromFile(fileName);
 
        HashMap<Integer,String> map=new HashMap<Integer,String>();
        String output="";
        String p="",c="",toadd="";
        int ci=-1,pi=-1,cnt=127;
        for(int i=0; i<128 ; i++)
        {
            map.put(i, Character.toString((char)i));
        }
        for(int i=0 ; i<vec.size() ; i++)
        {
           
           if(map.containsKey((int)vec.elementAt(i)))
           {
               output+=map.get((int)vec.elementAt(i));
               pi=ci;
               ci=(int)vec.elementAt(i);
               if(pi!=-1)
               {
                    p=map.get(pi);
                    c=map.get(ci);
                    toadd=p+c.charAt(0);
                    cnt++;
                    map.put(cnt,toadd);
               }
           }
           else
           {
               c=map.get(ci);
               toadd=c+c.charAt(0);
               output+=toadd;
               cnt++;
               ci=cnt;
               map.put(cnt,toadd);
           }
           
        }
        return output;
    }
   
    
    public static void main(String[] args) {
        Scanner cin=new Scanner(System.in);
        String input=cin.nextLine();
        compress(input);
        System.out.println(decompress("LZW.txt"));
        
        
        
    }
    
}
