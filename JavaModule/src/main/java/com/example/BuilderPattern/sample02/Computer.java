package com.example.BuilderPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 14:54
 * description:
 */
public class Computer {
    public String os;
    public String cpu;
    public String gpu;
    public String memory;

    @Override public String toString() {
        return "Computer{" +
                "os='" + os + '\'' +
                ", cpu='" + cpu + '\'' +
                ", gpu='" + gpu + '\'' +
                ", memory='" + memory + '\'' +
                '}';
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public void setGpu(String gpu) {
        this.gpu = gpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }
}
