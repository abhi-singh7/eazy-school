package com.eazyBytes.eazySchool.repository;

import com.eazyBytes.eazySchool.rowmappers.ContactRowMapper;
import com.eazyBytes.eazySchool.model.Contact;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);


    //@Query("SELECT c FROM Contact c WHERE c.status = :status")
    @Query(value = "select * from contact_msg c where c.status = :status", nativeQuery = true)
    Page<Contact>findByStatus(String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("Update Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

}
