package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:34
 * description:
 */
public class ApproverGeneralManager extends Approver {

    public ApproverGeneralManager(String name) {
        super(name);
    }

    @Override public void approve(AskLeave askLeave) {
        if (askLeave.days < 30) {
            System.out.println("总经理审批");
        } else {
            System.out.println("拒绝");
        }
    }
}
