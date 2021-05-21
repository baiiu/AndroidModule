package com.baiiu.nativeleak;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("test-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
            需要test-lib先加载进，再调用xhook才有效，也就说hook的是已经加载的so；

            所以一定要确保so先加载，再调用相关hook代码，否则不生效；
         */
        XHook.get().fixLeak();
    }

    public void onClick(View view) {
//        testASan();
        testLeak();
    }

    private native void testLeak();

    /*
        按照官方文档依赖进asan，可以检测如下错误：
            堆栈和堆缓冲区上溢/下溢
            释放之后的堆使用情况
            超出范围的堆栈使用情况
            重复释放/错误释放

        asan不能检测内存泄露
     */
    private native void testASan();


}