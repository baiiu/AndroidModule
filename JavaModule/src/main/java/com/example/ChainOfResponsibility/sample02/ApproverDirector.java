package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:34
 * description:
 */
public class ApproverDirector extends Approver {

    public ApproverDirector(String name) {
        super(name);
    }

    @Override public void approve(AskLeave askLeave) {
        if (askLeave.days < 3) {
            System.out.println("主任审批");
        } else {
            this.successor.approve(askLeave);
        }
    }
}
