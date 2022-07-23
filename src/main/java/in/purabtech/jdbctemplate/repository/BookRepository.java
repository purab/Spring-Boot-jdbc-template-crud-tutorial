package in.purabtech.jdbctemplate.repository;

import in.purabtech.jdbctemplate.model.Book;

import java.util.List;

public interface BookRepository {
    int save(Book book);
    int update(Book book);
    Book findById(Long id);
    int deleteById(Long id);
    List<Book> findAll();
    List<Book> findByPublished(boolean published);
    List<Book> findByTitle(String title);
    int deleteAll();
}
