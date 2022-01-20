package com.baiiu.multiprocess;
import com.baiiu.multiprocess.Book;
import android.view.Surface;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}