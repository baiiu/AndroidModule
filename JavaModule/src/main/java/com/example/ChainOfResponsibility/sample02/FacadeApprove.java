package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:37
 * description:
 */
public class FacadeApprove {
    Approver approverDirector;
    Approver approverManager;
    Approver approverGeneralManager;


    public FacadeApprove() {
        approverDirector = new ApproverDirector("主任");
        approverManager = new ApproverManager("经理");
        approverGeneralManager = new ApproverGeneralManager("总经理");
    }


    public void approve(AskLeave askLeave) {

        approverDirector.setSuccessor(approverManager);
        approverManager.setSuccessor(approverGeneralManager);

        approverDirector.approve(askLeave);

    }
}
