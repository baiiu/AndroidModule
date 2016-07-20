package com.example.PrototypePattern.DeepClone;

import com.example.PrototypePattern.ShallowClone.InnerObject;
import java.io.Serializable;

/**
 * author: baiiu
 * date: on 16/7/20 10:37
 * description:
 */
public class InnerInnerObject implements Serializable {
    public String innerInnerString;

    public InnerObject innerObject;
}
