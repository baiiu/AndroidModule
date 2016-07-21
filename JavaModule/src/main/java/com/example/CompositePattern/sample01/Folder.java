package com.example.CompositePattern.sample01;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/21 16:50
 * description:
 */
public class Folder extends AbstractFile {
    private List<AbstractFile> fileList = new ArrayList<>();
    private String folderName;

    public Folder(String folderName) {
        this.folderName = folderName;
    }

    @Override public void add(AbstractFile abstractFile) {
        fileList.add(abstractFile);
    }

    @Override public void remove(AbstractFile abstractFile) {
        fileList.remove(abstractFile);
    }

    @Override public AbstractFile getChild(int i) {
        return fileList.get(i);
    }

    @Override public void killVirus() {
        //遍历文件,调用每个File的killVirus()方法,达成递归

        System.out.println("Folder " + folderName + " kill virus");

        for (AbstractFile file : fileList) {
            file.killVirus();
        }

    }

}
