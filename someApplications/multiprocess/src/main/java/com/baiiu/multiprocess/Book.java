package com.baiiu.multiprocess;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author: baiiu
 * date: on 17/12/27 16:32
 * description:
 */
public class Book implements Parcelable {

    public int bookId;
    public String bookName;

    public Book() {
    }

    public Book(int bookId, String bookName) {
        this.bookId = bookId;
        this.bookName = bookName;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(bookId);
        out.writeString(bookName);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
        bookId = in.readInt();
        bookName = in.readString();
    }

    @Override public String toString() {
        return String.format("[bookId:%s, bookName:%s]", bookId, bookName);
    }
}
