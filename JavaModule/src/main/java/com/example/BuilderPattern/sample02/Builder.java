package com.example.BuilderPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/20 14:55
 * description:
 */
public class Builder {

    public String os;
    public String cpu;
    public String gpu;
    public String memory;

    public Builder os(String os) {
        this.os = os;
        return this;
    }

    public Builder cpu(String cpu) {
        this.cpu = cpu;
        return this;
    }

    public Builder gpu(String gpu) {
        this.gpu = gpu;
        return this;
    }

    public Builder memory(String memory) {
        this.memory = memory;
        return this;
    }

    public Computer build() {
        Computer computer = new Computer();
        computer.setCpu(cpu);
        computer.setGpu(gpu);
        computer.setOs(os);
        computer.setMemory(memory);

        return computer;
    }

}
