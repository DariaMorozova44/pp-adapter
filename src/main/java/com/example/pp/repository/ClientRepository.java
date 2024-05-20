package com.example.pp.repository;

import com.example.pp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    @Query("SELECT c.phone FROM Client c WHERE c.phone = :phone")
    String findPhoneByPhone(@Param("phone") String phone);

    @Query("SELECT c FROM Client c WHERE c.messageSend = false")
    List<Client> findAllUnsentMessages();


}

