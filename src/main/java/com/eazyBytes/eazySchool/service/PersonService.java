package com.eazyBytes.eazySchool.service;

import com.eazyBytes.eazySchool.constants.EazySchoolConstants;
import com.eazyBytes.eazySchool.model.Person;
import com.eazyBytes.eazySchool.model.Roles;
import com.eazyBytes.eazySchool.repository.PersonRepository;
import com.eazyBytes.eazySchool.repository.RolesRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Base64;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository personRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean createNewPerson( Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EazySchoolConstants.STUDENT_ROLE);
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if (null != person && person.getPersonId() >0)
        {
            isSaved = true;
        }

       return isSaved ;
    }
}
