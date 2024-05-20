package com.example.pp.controller;

import com.example.pp.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ClientsControllerTest {

    @Mock
    private ClientServiceImpl clientServiceImpl;

    @InjectMocks
    private ClientsController clientsController;

    @Test
    void testGetClientsById() {
        // Arrange
        String clientId = "1";

        // Act
        clientsController.getClientsById(clientId);

        // Assert
        verify(clientServiceImpl, times(1)).getClientsById(clientId);
    }

    @Test
    void testGetClients() {
        // Act
        clientsController.getClients();

        // Assert
        verify(clientServiceImpl, times(1)).processClientsAndSendMessages();
    }
}