package com.tugas.deploy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "users")
public class User {
    @jakarta.persistence.Id
    private String nim;
    private String nama;
    private String jenisKelamin;
}
