package com.baiiu.multiprocess;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.baiiu.library.LogUtil;
import com.baiiu.multiprocess.custom.BookManagerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 17/12/27 17:15
 * description:
 */
public class BookManagerService extends Service {
    public static final String TAG_BINDER = "binder";

    private final List<Book> list = new ArrayList<>();

    private Binder mBinder = new BookManagerImpl() {

        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.d(TAG_BINDER, "BookManagerService#getBookList: " + Thread.currentThread().getId() + ", " + Binder.getCallingPid() + "," + Process.myPid());
            synchronized (list) {
                return list;
            }
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            LogUtil.d(TAG_BINDER, "BookManagerService#addBook: " + Thread.currentThread().getId() + ", " + Binder.getCallingPid() + ", " + Process.myPid());

            synchronized (list) {
                list.add(book);
            }
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        list.add(new Book(1, "book1"));
        list.add(new Book(2, "book2"));
        list.add(new Book(3, "book3"));
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

}
