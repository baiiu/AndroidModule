package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 *
 * 一企业的采购审批是分级进行的，即根据采购金额的不同由不同层次的主管人员来审批.
 * 主任可以审批5万元以下（不包括5万元）的采购单，副董事长可以审批5万元至10万元（不包括10万元）的采购单，董事长可以审批10万元至50万元（不包括50万元）的采购单，50万元及以上的采购单就需要开董事会讨论决定。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 3000000, "买东西");
        new FacadeApprover().approver(purchaseRequest);

    }
}
