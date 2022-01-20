package com.baiiu.multiprocess;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.baiiu.library.LogUtil;

import java.util.List;

import static com.baiiu.multiprocess.BookManagerService.TAG_BINDER;


public class MainActivity extends AppCompatActivity {
    private IBookManager mRemoteBookManager;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mRemoteBookManager == null) return;

            mRemoteBookManager.asBinder()
                    .unlinkToDeath(mDeathRecipient, 0);
            mRemoteBookManager = null;

            Intent intent = new Intent(MainActivity.this, BookManagerService.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtil.d(UserManager.INSTANCE.age + ", " + UserManager.sId);
        UserManager.sId += 1;
        UserManager.INSTANCE.age += 1;
        LogUtil.d(UserManager.INSTANCE.age + ", " + UserManager.sId);

        LogUtil.d(TAG_BINDER, "MainActivity: " + Thread.currentThread().getId() + ", " + Process.myPid());
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            /*
                MainActivity: 2, 16377
                onServiceConnected#service: android.os.BinderProxy@1d3a70c, 2, 16377  // 回调到主线程
                BookManagerImpl#Proxy#addBook: 2, 16377  // 在主线程触发
                BookManagerImpl#onTransact: code is 2, 3553, 16407  // 在远端binder线程池里
                BookManagerService#addBook: 3553, 16377, 16407
                BookManagerImpl#Proxy#getBookList: 2, 16377
                BookManagerImpl#onTransact: code is 1, 3553, 16407
                BookManagerService#getBookList: 3553, 16377,16407
             */

            LogUtil.d("onServiceConnected:" + name); // BinderProxy，只有这个才有跨进程的能力
            LogUtil.d(TAG_BINDER, "onServiceConnected#service: " + service + ", " + Thread.currentThread().getId() + ", " + Process.myPid());

            mRemoteBookManager = IBookManager.Stub.asInterface(service);
            try {
                mRemoteBookManager.asBinder()
                        .linkToDeath(mDeathRecipient, 0);

                mRemoteBookManager.addBook(new Book(4, "book4"));

                List<Book> bookList = mRemoteBookManager.getBookList();

                LogUtil.d(bookList);

            } catch (RemoteException e) {
                LogUtil.e(e.toString());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtil.d("onServiceDisconnected:" + name);
            mRemoteBookManager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (connection != null) {
            unbindService(connection);
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_bind:
                Intent intent = new Intent(this, BookManagerService.class);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_second:
                startActivity(new Intent(this, SecondActivity.class));
                break;
        }
    }
}
