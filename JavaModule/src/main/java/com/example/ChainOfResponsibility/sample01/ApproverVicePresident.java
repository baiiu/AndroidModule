package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:35
 * description:
 */
public class ApproverVicePresident extends Approver {
    public ApproverVicePresident(String name) {
        super(name);
    }

    @Override public void processRequest(PurchaseRequest request) {
        if (request.amount < 100000) {
            System.out.println("副董事长处理");
        } else {
            this.successor.processRequest(request);
        }
    }
}
