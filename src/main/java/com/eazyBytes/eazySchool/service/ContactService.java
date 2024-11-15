package com.eazyBytes.eazySchool.service;

import com.eazyBytes.eazySchool.constants.EazySchoolConstants;
import com.eazyBytes.eazySchool.model.Contact;
import com.eazyBytes.eazySchool.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatus(
                EazySchoolConstants.OPEN,pageable);
        return msgPage;
    }

    public boolean updateMsgStatus(int contactId ) {

        boolean isUpdated = false;
       int rows = contactRepository.updateStatusById(EazySchoolConstants.CLOSE, contactId);
       if (rows > 0){
           isUpdated= true;
       }



        return isUpdated;
    }


}
