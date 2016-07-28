package com.example.CommandPattern.sample03;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/7/25 15:50
 * description:
 */
public class ConcreteCommand extends Command {

    private Adder adder = new Adder();

    private List<Integer> list = new ArrayList<>();


    @Override public int execute(int value) {
        int result = adder.add(value);
        list.add(result);

        return result;
    }

    @Override public int undo() {
        if (list.isEmpty()) {
            return 0;
        }

        return list.remove(list.size() - 1);
    }
}
