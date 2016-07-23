package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:33
 * description:
 */
public class ApproverDirector extends Approver {

    public ApproverDirector(String name) {
        super(name);
    }

    @Override public void processRequest(PurchaseRequest request) {
        if (request.amount < 50000) {
            System.out.println("主任处理");
        } else {
            this.successor.processRequest(request);
        }
    }
}
