package com.example.CompositePattern.sample01;

/**
 * author: baiiu
 * date: on 16/7/21 16:46
 * description:
 */
public abstract class AbstractFile {
    public abstract void add(AbstractFile abstractFile);

    public abstract void remove(AbstractFile abstractFile);

    public abstract AbstractFile getChild(int i);

    public abstract void killVirus();
}
