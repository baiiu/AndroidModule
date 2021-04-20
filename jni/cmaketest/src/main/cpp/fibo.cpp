//
// Created by baiiu on 2021/4/19.
//

int test(int n) {
    if (n <= 2) {
        return 1;
    }

    int first = 1;
    int second = 1;
    int num = 0;

    while (n > 2) {
        num = first + second;
        --n;
        first = second;
        second = num;
    }

    return num;

}