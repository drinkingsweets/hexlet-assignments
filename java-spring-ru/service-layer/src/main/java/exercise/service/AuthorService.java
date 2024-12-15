package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.model.Author;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper am;

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(am::map)
                .toList();
    }

    public AuthorDTO create(AuthorCreateDTO dto) {
        Author author = am.map(dto);
        authorRepository.save(author);
        return am.map(author);
    }

    public AuthorDTO findById(Long id) {
        var author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));

        return am.map(author);
    }

    public AuthorDTO update(AuthorUpdateDTO dto, Long id) {
        var found = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));

        am.update(dto, found);
        authorRepository.save(found);
        return am.map(found);
    }

    public void destroy(long id) {
        authorRepository.deleteById(id);
    }
    // END
}
