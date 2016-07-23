package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:37
 * description:
 */
public class ApproverPresident extends Approver {
    public ApproverPresident(String name) {
        super(name);
    }

    @Override public void processRequest(PurchaseRequest request) {
        if (request.amount < 500000) {
            System.out.println("董事长处理");
        } else {
            this.successor.processRequest(request);
        }
    }
}
