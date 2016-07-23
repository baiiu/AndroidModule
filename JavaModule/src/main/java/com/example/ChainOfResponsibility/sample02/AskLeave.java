package com.example.ChainOfResponsibility.sample02;

/**
 * auther: baiiu
 * time: 16/7/23 23 11:29
 * description:
 */
public class AskLeave {

    public int days;
    public String reason;

    public AskLeave(int days, String reason) {
        this.days = days;
        this.reason = reason;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
