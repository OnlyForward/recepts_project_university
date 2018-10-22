package com.example.a123.recepts_project_university.model;

import java.io.File;
import java.util.ArrayList;

public class FileSearch {

    public static ArrayList<String> getDirectoryPaths(String directory){
        ArrayList<String> pathArray = new ArrayList<>();
        File file = new File(directory);
        File[] listfiles = file.listFiles();
        for(int i=0;i<listfiles.length;i++){
            if(listfiles[i].isDirectory()){
                pathArray.add(listfiles[i].getAbsolutePath());
            }
        }
        return  pathArray;
    }

}
