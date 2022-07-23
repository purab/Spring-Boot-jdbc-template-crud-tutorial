package in.purabtech.jdbctemplate.controller;

import in.purabtech.jdbctemplate.model.Book;
import in.purabtech.jdbctemplate.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
        try {
            List<Book> books = new ArrayList<Book>();
            if(title ==null) {
                bookRepository.findAll().forEach(books::add);
            } else {
                bookRepository.findByTitle(title).forEach(books::add);
            }

            if(books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable("id") long id) {
        Book book = bookRepository.findById(id);
        if(book!=null) {
            return new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<String> createBook(@RequestBody Book book) {
        try{
            bookRepository.save(new Book(book.getTitle(),book.getDescription(),book.isPublished()));
            return new ResponseEntity<>("Book is added successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<String> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        Book _book = bookRepository.findById(id);
        if(_book !=null) {
            _book.setId(id);
            _book.setTitle(book.getTitle());
            _book.setDescription(book.getDescription());
            _book.setPublished(book.isPublished());
            bookRepository.update(_book);
            return new ResponseEntity<>("Book updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found with id:"+id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
        try {
            int result = bookRepository.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Book with id=" + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Book was deleted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete Book.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/books")
    public ResponseEntity<String> deleteAllBooks() {
        try {
            int numRows = bookRepository.deleteAll();
            return new ResponseEntity<>("Deleted " + numRows + " Book(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete tutorials.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/books/published")
    public ResponseEntity<List<Book>> findByPublished() {
        try {
            List<Book> books = bookRepository.findByPublished(true);
            if (books.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
