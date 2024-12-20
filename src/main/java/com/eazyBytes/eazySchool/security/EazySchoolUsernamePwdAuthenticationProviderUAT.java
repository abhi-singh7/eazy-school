package com.eazyBytes.eazySchool.security;


import com.eazyBytes.eazySchool.model.Person;
import com.eazyBytes.eazySchool.model.Roles;
import com.eazyBytes.eazySchool.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Profile("!prod")
@Component
public class EazySchoolUsernamePwdAuthenticationProviderUAT implements AuthenticationProvider {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EazySchoolUsernamePwdAuthenticationProviderUAT(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String email = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = personRepository.readByEmail(email);

        if (
                null != person
                        &&
                        person.getPersonId()>0
                      ){
            return new UsernamePasswordAuthenticationToken(
                    email, null, getGrantedAuthorities(person.getRoles()));
        }else {
            throw new BadCredentialsException("Invalid credentials!");
        }
    }

    private Collection<? extends GrantedAuthority> getGrantedAuthorities(Roles roles) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+ roles.getRoleName()));
        return grantedAuthorities;
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
