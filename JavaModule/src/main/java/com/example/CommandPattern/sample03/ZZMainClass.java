package com.example.CommandPattern.sample03;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 */
public class ZZMainClass {

    public static void main(String[] args) {

        Calculator calculator = new Calculator(new ConcreteCommand());

        calculator.compute(5);
        calculator.compute(10);
        calculator.compute(15);

        calculator.undo();
        calculator.undo();
        calculator.undo();
        calculator.undo();
    }
    
}
