package com.example.CompositePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/21 16:48
 * description:
 */
public class TextFile extends AbstractFile {

    private String name;

    public TextFile(String name) {
        this.name = name;
    }

    @Override public void add(AbstractFile abstractFile) {

    }

    @Override public void remove(AbstractFile abstractFile) {

    }

    @Override public AbstractFile getChild(int i) {
        return null;
    }

    @Override public void killVirus() {
        System.out.println("TextFile " + name + " kill virus");
    }
}
