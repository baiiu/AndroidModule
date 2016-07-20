package com.example.PrototypePattern.DeepClone;

import java.io.Serializable;

/**
 * author: baiiu
 * date: on 16/7/20 10:24
 * description:
 */
public class DeepInnerObject implements Serializable {
    public String inner;
    public InnerInnerObject innerInnerObject;
}
