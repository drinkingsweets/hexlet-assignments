package exercise.service;

import exercise.dto.*;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.model.Author;
import exercise.model.Book;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bm;

    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDTO> getAll() {
        return bookRepository.findAll()
                .stream()
                .map(bm::map)
                .toList();
    }

    public BookDTO create(BookCreateDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ConstraintViolationException(
                "Invalid author ID: " + dto.getAuthorId(), null));
        Book book = bm.map(dto);
        bookRepository.save(book);
        return bm.map(book);
    }

    public BookDTO findById(Long id) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));

        return bm.map(book);
    }

    public BookDTO update(BookUpdateDTO dto, Long id) {
        Author author = authorRepository.findById(dto.getAuthorId().get())
                .orElseThrow(() -> new ConstraintViolationException(
                "Invalid author ID: " + dto.getAuthorId().get(), null));

        var found = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));


        bm.update(dto, found);
        bookRepository.save(found);
        return bm.map(found);
    }

    public void destroy(long id) {
        bookRepository.deleteById(id);
    }
    // END
}
