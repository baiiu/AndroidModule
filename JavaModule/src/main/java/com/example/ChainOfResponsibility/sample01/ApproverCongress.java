package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:38
 * description:
 */
public class ApproverCongress extends Approver {
    public ApproverCongress(String name) {
        super(name);
    }

    @Override public void processRequest(PurchaseRequest request) {
        System.out.println("董事会处理");
    }
}
