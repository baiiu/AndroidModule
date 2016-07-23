package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:34
 * description:
 */
public class ApproverManager extends Approver {

    public ApproverManager(String name) {
        super(name);
    }

    @Override public void approve(AskLeave askLeave) {
        if (askLeave.days < 10) {
            System.out.println("经理审批");
        } else {
            this.successor.approve(askLeave);
        }
    }
}
