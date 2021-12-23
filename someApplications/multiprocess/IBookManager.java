/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /Users/baiiu/Desktop/MutiProcessTest/app/src/main/aidl/com/baiiu/mutiprocess/IBookManager.aidl
 */
package com.baiiu.mutiprocess;

public interface IBookManager extends android.os.IInterface {
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements com.baiiu.mutiprocess.IBookManager {
        // Binder的唯一标识
        private static final java.lang.String DESCRIPTOR = "com.baiiu.mutiprocess.IBookManager";

        /** Construct the stub at attach it to the interface. */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.baiiu.mutiprocess.IBookManager interface,
         * generating a proxy if needed.
         */
        // 将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象
        // 这种转换是区分进程的
        public static com.baiiu.mutiprocess.IBookManager asInterface(android.os.IBinder obj) { //BinderProxy
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.baiiu.mutiprocess.IBookManager))) {
                // 同一进程，返回的就是服务端的Stub对象本身
                return ((com.baiiu.mutiprocess.IBookManager) iin);
            }

            // 不同进程，返回系统封装后的Proxy对象
            return new com.baiiu.mutiprocess.IBookManager.Stub.Proxy(obj);
        }

        // 返回当前Binder对象
        @Override public android.os.IBinder asBinder() {
            return this;
        }

        // 运行在服务端中的Binder线程池中，当客户端发起跨进程请求时，远程请求会通过这个系统底层封装后交由此方法处理。
        // 1. 服务端从code可以确定客户端所请求的目标方法是什么
        // 2. 接着从data中去除目标方法所需的参数(如果有)，然后执行目标方法
        // 3. 执行完毕后，就像reply中写入返回值(如果有)，
        // 如果此方法返回false，表示客户端请求失败。可以使用这个特性来做权限验证，以使随便一个进程不能调用我们的服务。
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply,
                int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_getBookList: {
                    data.enforceInterface(DESCRIPTOR);
                    java.util.List<com.baiiu.mutiprocess.Book> _result = this.getBookList();
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    return true;
                }
                case TRANSACTION_addBook: {
                    data.enforceInterface(DESCRIPTOR);
                    com.baiiu.mutiprocess.Book _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.baiiu.mutiprocess.Book.CREATOR.createFromParcel(data);
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

        private static class Proxy implements com.baiiu.mutiprocess.IBookManager {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
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
            @Override public java.util.List<com.baiiu.mutiprocess.Book> getBookList() throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                java.util.List<com.baiiu.mutiprocess.Book> _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    mRemote.transact(Stub.TRANSACTION_getBookList, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.createTypedArrayList(com.baiiu.mutiprocess.Book.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            /*
                addBook没有返回值，所以不需要从_reply中取出返回值
             */
            @Override public void addBook(com.baiiu.mutiprocess.Book book) throws android.os.RemoteException {
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
                    mRemote.transact(Stub.TRANSACTION_addBook, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_addBook = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    }

    public java.util.List<com.baiiu.mutiprocess.Book> getBookList() throws android.os.RemoteException;

    public void addBook(com.baiiu.mutiprocess.Book book) throws android.os.RemoteException;
}
