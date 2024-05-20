package com.example.pp.service;

import com.example.pp.http.FeignClients;
import com.example.pp.mapper.ClientMapper;
import com.example.pp.model.Client;
import com.example.pp.model.ClientsInfo;
import com.example.pp.model.Message;
import com.example.pp.repository.ClientRepository;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Data
@ExtendWith(MockitoExtension.class)
public class ClientServiceImplTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientRepository repository;

    @Mock
    private FeignClients feignClients;

    @Mock
    private KafkaTemplate<String, Message> kafkaTemplate;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void testProcessClientsAndSendMessages() {
        // Arrange

        ClientsInfo client1 = new ClientsInfo("1", "John", "Doe", "Smith", 30L, LocalDate.now(), "89111234567");
        ClientsInfo client2 = new ClientsInfo("2", "Jane", "Doe", "Smith", 28L, LocalDate.of(2022, Month.JANUARY, 1), "89112345677");
        ClientsInfo client3 = new ClientsInfo("3", "Bob", "Smith", "Johnson", 35L, LocalDate.now(), "89113456787");
        List<ClientsInfo> clients = Arrays.asList(client1, client2, client3);

        Client mappedClient1 = new Client("John Doe Smith", "89111234567", LocalDate.now(), false);
        Client mappedClient3 = new Client("Bob Smith Johnson", "89113456787", LocalDate.now(), false);

        when(feignClients.getClients()).thenReturn(clients);
        when(clientMapper.clientsInfoToClient(client1)).thenReturn(mappedClient1);
        when(clientMapper.clientsInfoToClient(client3)).thenReturn(mappedClient3);
        when(repository.findPhoneByPhone("89111234567")).thenReturn(null);
        when(repository.findPhoneByPhone("89113456787")).thenReturn(null);

        // Act
        clientService.processClientsAndSendMessages();

        // Assert
        verify(feignClients, times(1)).getClients();
        verify(clientMapper, times(1)).clientsInfoToClient(client1);
        verify(clientMapper, times(1)).clientsInfoToClient(client3);
        verify(repository, times(1)).save(mappedClient1);
        verify(repository, times(1)).save(mappedClient3);
        verify(repository, times(1)).findPhoneByPhone("89111234567");
        verify(repository, times(1)).findPhoneByPhone("89113456787");
    }


    @Test
    public void testGetClientsById() {
        String clientId = "2";
        ClientsInfo clientsInfo = new ClientsInfo();
        clientsInfo.setClientId(clientId);
        clientsInfo.setName("John");
        clientsInfo.setMiddleName("Doe");
        clientsInfo.setSurname("Smith");
        clientsInfo.setAge(30);
        clientsInfo.setPhone("1234567897");
        clientsInfo.setBirthday(LocalDate.now());

        when(feignClients.getClientsById(clientId)).thenReturn(clientsInfo);
        when(repository.save(any())).thenReturn(new Client());

        clientService.getClientsById(clientId);

        verify(repository, times(1)).save(any());
        verify(feignClients, times(1)).getClientsById(clientId);
    }
}