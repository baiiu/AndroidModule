package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:27
 * description: 审批者抽象类
 */
public abstract class Approver {

    protected String name;//当前审批者姓名
    protected Approver successor;//下一个审批者

    public Approver(String name) {
        this.name = name;
    }

    public Approver(String name, Approver successor) {
        this.name = name;
        this.successor = successor;
    }

    public void setSuccessor(Approver successor) {
        this.successor = successor;
    }

    public abstract void processRequest(PurchaseRequest request);
}
