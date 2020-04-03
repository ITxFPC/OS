package itxfpc.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ReadData {

    static public int[] read(String path) throws IOException {
        File file = new File(path);
        FileReader fr=new FileReader(file);   //reads the file  
        BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
        StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
        String line;  
        ArrayList<Integer> temp = new ArrayList<Integer> ();
        while((line=br.readLine())!=null)  
        {
            temp.add(Integer.parseInt(line));
        }  
        fr.close();   
        int [] result = new int[temp.size()];
        for(int i=0;i<temp.size();i++){
            result[i] = temp.get(i);
        }
        return result;
    }
}