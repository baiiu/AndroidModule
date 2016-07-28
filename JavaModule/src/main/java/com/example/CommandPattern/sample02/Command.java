package com.example.CommandPattern.sample02;

/**
 * author: baiiu
 * date: on 16/7/25 14:54
 * description: 抽象命令类,其子类持有请求接收者,在execute方法中调用该接受者来处理请求.
 *
 * 命令模式分离了请求的发送者和接受者,请求的发送者调用命令类的execute()方法,命令类在此方法中转接
 * 到真正的接受者处理方法action中.这就是命令模式.
 *
 * 所有的请求者都封装在Invoker中,并且面对Command编程,使之解耦.需要使用不同的具体功能时,替换Command即可
 */
abstract class Command {

    public abstract void execute();
}