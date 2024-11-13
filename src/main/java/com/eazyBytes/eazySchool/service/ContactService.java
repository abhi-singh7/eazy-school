package com.eazyBytes.eazySchool.service;

import com.eazyBytes.eazySchool.constants.EazySchoolConstants;
import com.eazyBytes.eazySchool.model.Contact;
import com.eazyBytes.eazySchool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(EazySchoolConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if (null !=savedContact && savedContact.getContactId()>0){
            isSaved=true;
        }
        log.info(contact.toString());
        log.info("isSaved :{}", isSaved);
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus() {

        List<Contact> contactMsgs = contactRepository.findByStatus(EazySchoolConstants.OPEN);
        return contactMsgs;
    }

    public boolean updateMsgStatus(int contactId ) {

        boolean isUpdated = false;

       Optional< Contact> contact = contactRepository.findById(contactId);


       contact.ifPresent(contact1 ->{
           contact1.setStatus(EazySchoolConstants.CLOSE);

       });

       Contact updatedContact = contactRepository.save(contact.get());
       if (updatedContact.getUpdatedBy() != null){
           isUpdated= true;
       }



        return isUpdated;
    }
}
