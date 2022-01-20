package com.baiiu.multiprocess.custom;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;

import com.baiiu.multiprocess.Book;

import java.util.List;

/**
 * author: baiiu
 * date: on 18/1/3 14:25
 * description:
 */
public interface CustomBookManager extends IInterface {

    // Binder的唯一标识
    String DESCRIPTOR = "com.baiiu.mutiprocess.custom.IBookManager";

    int TRANSACTION_getBookList = IBinder.FIRST_CALL_TRANSACTION + 0;
    int TRANSACTION_addBook = IBinder.FIRST_CALL_TRANSACTION + 1;

    List<Book> getBookList() throws RemoteException;

    void addBook(Book book) throws RemoteException;
}
