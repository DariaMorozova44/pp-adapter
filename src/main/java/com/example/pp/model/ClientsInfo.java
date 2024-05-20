package com.example.pp.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientsInfo {
    private String clientId;
    private String name;
    private String middleName;
    private String surname;
    private long age;
    private LocalDate birthday;
    private String phone;
}
