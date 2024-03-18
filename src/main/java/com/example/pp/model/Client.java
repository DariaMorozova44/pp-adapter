package com.example.pp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients")
public class Client {
    @Column(name = "full_name")
    private String fullName;
    @Id
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "message_send")
    private boolean messageSend;


}
