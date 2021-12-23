package com.baiiu.multiprocess.custom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Process;
import android.os.RemoteException;

import com.baiiu.library.LogUtil;
import com.baiiu.multiprocess.Book;

import java.util.List;

import static com.baiiu.multiprocess.BookManagerService.TAG_BINDER;

/**
 * author: baiiu
 * date: on 18/1/3 19:15
 * description:
 */
public abstract class BookManagerImpl extends Binder implements IBookManager {

    protected BookManagerImpl() {
        this.attachInterface(this, DESCRIPTOR);
    }

    @Override
    public IBinder asBinder() {
        return this;
    }

    // 将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象
    // 这种转换是区分进程的
    public static IBookManager asInterface(android.os.IBinder obj) {
        if (obj == null) return null;

        IInterface inn = obj.queryLocalInterface(DESCRIPTOR);
        if (inn != null && inn instanceof IBookManager) {
            return (IBookManager) inn;
        }

        return new BookManagerImpl.Proxy(obj);
    }

    // 运行在服务端中的Binder线程池中，当客户端发起跨进程请求时，远程请求会通过这个系统底层封装后交由此方法处理。
    // 1. 服务端从code可以确定客户端所请求的目标方法是什么
    // 2. 接着从data中去除目标方法所需的参数(如果有)，然后执行目标方法
    // 3. 执行完毕后，就像reply中写入返回值(如果有)，
    // 如果此方法返回false，表示客户端请求失败。可以使用这个特性来做权限验证，以使随便一个进程不能调用我们的服务。
    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        LogUtil.d(TAG_BINDER, "BookManagerImpl#onTransact: code is " + code + ", " + Thread.currentThread().getId() + ", " + Process.myPid());

        switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_getBookList: {
                data.enforceInterface(DESCRIPTOR);
                java.util.List<Book> _result = this.getBookList();
                reply.writeNoException();
                reply.writeTypedList(_result);
                return true;
            }
            case TRANSACTION_addBook: {
                data.enforceInterface(DESCRIPTOR);
                Book _arg0;
                if ((0 != data.readInt())) {
                    _arg0 = Book.CREATOR.createFromParcel(data);
                } else {
                    _arg0 = null;
                }
                this.addBook(_arg0);
                reply.writeNoException();
                return true;
            }
        }

        return super.onTransact(code, data, reply, flags);
    }

    private static class Proxy implements IBookManager {
        private IBinder mRemote;

        Proxy(IBinder remote) {
            this.mRemote = remote;
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }

        public String getInterfaceDescriptor() {
            return DESCRIPTOR;
        }

        /*
            运行在客户端，当客户端远程调用此方法时，内部实现：

            1. 创建该方法需要的输入型Parcel对象_data、输出型Parcel对象_reply和返回值对象List
            2. 将该方法的参数信息写入_data中(如果有)
            3. 调用transact方法发起RPC请求，BinderProxy跨进程，同时挂起当前线程
            4. 服务端的onTransact方法被调用
            5. 直到RPC过程返回后，当前线程继续执行，并从_reply中取出RPC过程的返回结果
            6. 返回_reply中的数据
          */
        @Override
        public List<Book> getBookList() throws RemoteException {
            LogUtil.d(TAG_BINDER, "BookManagerImpl#Proxy#getBookList: " + Thread.currentThread().getId() + ", " + Process.myPid());

            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            java.util.List<Book> _result;
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                mRemote.transact(TRANSACTION_getBookList, _data, _reply, 0);
                _reply.readException();
                _result = _reply.createTypedArrayList(Book.CREATOR);
            } finally {
                _reply.recycle();
                _data.recycle();
            }
            return _result;
        }

        /*
            addBook没有返回值，所以不需要从_reply中取出返回值
         */
        @Override
        public void addBook(Book book) throws RemoteException {
            LogUtil.d(TAG_BINDER, "BookManagerImpl#Proxy#addBook: " + Thread.currentThread().getId() + ", " + Process.myPid());

            android.os.Parcel _data = android.os.Parcel.obtain();
            android.os.Parcel _reply = android.os.Parcel.obtain();
            try {
                _data.writeInterfaceToken(DESCRIPTOR);
                if ((book != null)) {
                    _data.writeInt(1);
                    book.writeToParcel(_data, 0);
                } else {
                    _data.writeInt(0);
                }
                mRemote.transact(TRANSACTION_addBook, _data, _reply, 0);
                _reply.readException();
            } finally {
                _reply.recycle();
                _data.recycle();
            }
        }

    }

}
