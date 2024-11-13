package com.eazyBytes.eazySchool.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int contactId;


    @NotBlank(message = "Name must not be blank")
    @Size(min = 3, message = "Name must be at least 3 character long")
    private String name;
    @NotBlank(message = "Mobile number must not be blank")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    private String mobileNum;
    @NotBlank(message = "Email no must not be blank")
    @Email(message = "Please provide a valid email")
    private String email;
    @NotBlank(message = "Subject must not be blank")
    private String subject;
    @NotBlank(message = "Message must be blank")
    @Size(min = 10,message ="Message must be at least 10 character long")
    private String message;

    private String status;


}
