package com.eazyBytes.eazySchool.service;

import com.eazyBytes.eazySchool.config.EazySchoolProps;
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

@Slf4j
@Service
public class ContactService {

    private final ContactRepository contactRepository;

    private final EazySchoolProps eazySchoolProps;

    @Autowired
    public ContactService(ContactRepository contactRepository, EazySchoolProps eazySchoolProps) {
        this.contactRepository = contactRepository;
        this.eazySchoolProps = eazySchoolProps;
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
     eazySchoolProps.getContact().forEach(
             (k,v)-> log.info("Contacts {} are {}", k,v)
     );

     log.info("-----------------");
     eazySchoolProps.getBranches().forEach(
             s->log.info("Branches are --->"+ s)
     );

        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatusWithPage(
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
