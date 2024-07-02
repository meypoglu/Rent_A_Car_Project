package business;

import dao.BookDao;
import entity.Book;

import java.util.ArrayList;

public class BookManager {
    private final BookDao bookDao;

    public BookManager() {
        this.bookDao = new BookDao();
    }

    public boolean save(Book book) {
        return this.bookDao.save(book);
    }

    public ArrayList<Book> findByPlate(String plate) {
        return this.bookDao.findByPlate(plate);
    }

    public ArrayList<Book> findAll() {
       return this.bookDao.findAll();
    }

    public boolean delete(int bookingId) {
        return bookDao.delete(bookingId);
    }
}
