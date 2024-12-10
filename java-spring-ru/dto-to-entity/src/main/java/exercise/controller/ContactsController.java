package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ContactDTO create(@RequestBody ContactCreateDTO createDTO) {
        var contact = toEntity(createDTO);
        contactRepository.save(contact);
        var contactDto = toDTO(contact);
        return contactDto;
    }

    private ContactDTO toDTO(Contact contact) {
        var dto = new ContactDTO();
        dto.setId(contact.getId());
        dto.setPhone(contact.getPhone());
        dto.setCreatedAt(contact.getCreatedAt());
        dto.setUpdatedAt(contact.getUpdatedAt());
        dto.setLastName(contact.getLastName());
        dto.setFirstName(dto.getFirstName());

        return dto;
    }

    private Contact toEntity(ContactCreateDTO createDTO) {
        Contact contact = new Contact();
        contact.setFirstName(createDTO.getFirstName());
        contact.setPhone(createDTO.getPhone());
        contact.setLastName(createDTO.getLastName());

        return contact;
    }
}
