package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/21 21 22:54
 * description:
 *
 * 如果员工请假天数小于3天，主任可以审批该假条；
 * 如果员工请假天数大于等于3天，小于10天，经理可以审批；
 * 如果员工请假天数大于等于10天，小于30天，总经理可以审批；
 * 如果超过30天，总经理也不能审批，提示相应的拒绝信息。试用职责链模式设计该假条审批模块。
 */
public class ZZMainClass {

    public static void main(String[] args) {

        AskLeave askLeave = new AskLeave(31, "请假");
        new FacadeApprove().approve(askLeave);

    }

}
