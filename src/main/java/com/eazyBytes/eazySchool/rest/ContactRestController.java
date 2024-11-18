package com.eazyBytes.eazySchool.rest;


import com.eazyBytes.eazySchool.constants.EazySchoolConstants;
import com.eazyBytes.eazySchool.model.Contact;
import com.eazyBytes.eazySchool.model.Response;
import com.eazyBytes.eazySchool.repository.ContactRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "/api/contact"
        ,produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
public class ContactRestController {


    private final ContactRepository contactRepository;

    public ContactRestController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessagesByStatus(@RequestParam(name="status") String status){
        return contactRepository.findByStatus(status);
    }

    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestHeader ("invocationFrom") String invocationFrom,
                                            @Valid @RequestBody Contact contact){
        log.info("Header invocation from - {} ", invocationFrom);
        contactRepository.save(contact);
        Response response = new Response();
        response.setStatusMsg("Message saved Successfully");
        response.setStatusCode("200");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("isMsgSaved", "true")
                .body(response);



    }


    @DeleteMapping("/deleteMsg")
    public ResponseEntity<Response> deleteMsg(RequestEntity<Contact> requestEntity){
        HttpHeaders headers = requestEntity.getHeaders();
        headers.forEach((key, value) -> {
            log.info("Header '{}' = {}", key, value.stream().collect(Collectors.joining("|")));
        });
        Contact contact = requestEntity.getBody();
        contactRepository.deleteById(Objects.requireNonNull(contact).getContactId());
        Response response = new Response();
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully deleted");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @PatchMapping("/closeMsg")
    public ResponseEntity<Response> closeMsg(@RequestBody Contact contactReq){
        Response response = new Response();
        Optional<Contact> contact = contactRepository.findById(contactReq.getContactId());
        if(contact.isPresent()){
            contact.get().setStatus(EazySchoolConstants.CLOSE);
            contactRepository.save(contact.get());
        }else{
            response.setStatusCode("400");
            response.setStatusMsg("Invalid Contact ID received");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }
        response.setStatusCode("200");
        response.setStatusMsg("Message successfully closed");
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

}
