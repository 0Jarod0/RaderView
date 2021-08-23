package com.example.haha.customview.interfaces;

import com.example.haha.customview.model.Book;

import java.util.List;

/**
 * Created by haha on 2020/3/1.
 */

public interface IBookManager {
    List<Book> getBookList();
    void addBook(Book book);
}
