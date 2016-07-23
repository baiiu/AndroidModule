package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:32
 * description:
 */
public abstract class Approver {

    protected String name;//审批者姓名
    protected Approver successor;//下个审批者

    public Approver(String name) {
        this.name = name;
    }

    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }


    public abstract void approve(AskLeave askLeave);
}
