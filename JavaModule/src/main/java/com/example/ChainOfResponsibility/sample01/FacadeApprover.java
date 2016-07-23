package com.example.ChainOfResponsibility.sample01;

/**
 * auther: baiiu
 * time: 16/7/23 23 10:42
 * description:
 */
public class FacadeApprover {


    Approver approverDirector;
    Approver approverVicePresident;
    Approver approverPresident;
    Approver approverCongress;

    public FacadeApprover() {
        approverDirector = new ApproverDirector("主任");
        approverVicePresident = new ApproverVicePresident("副董事");
        approverPresident = new ApproverPresident("董事");
        approverCongress = new ApproverCongress("董事会");
    }

    public void approver(PurchaseRequest request) {

        //构造责任链
        approverDirector.setSuccessor(approverVicePresident);
        approverVicePresident.setSuccessor(approverPresident);
        approverPresident.setSuccessor(approverCongress);

        approverDirector.processRequest(request);

    }

}
