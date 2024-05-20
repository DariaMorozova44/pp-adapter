package com.example.pp.service;

import com.example.pp.http.FeignClients;
import com.example.pp.mapper.ClientMapper;
import com.example.pp.model.Client;
import com.example.pp.model.ClientsInfo;
import com.example.pp.model.Message;
import com.example.pp.repository.ClientRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
@Getter
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository repository;
    private final FeignClients feignClients;
    private final Month currentMonth = LocalDate.now().getMonth();
    private final KafkaTemplate<String, Message> kafkaTemplate;


    @Value("${client.myVariable}")
    private String myVariableValue;

    @Override
    public void processClientsAndSendMessages() {
        log.info("Starting processClientsAndSendMessages method...");

        try {
            List<ClientsInfo> clients = feignClients.getClients();
            for (ClientsInfo clientsInfo : clients) {
                if (clientsInfo != null &&
                        clientsInfo.getPhone().endsWith("7") &&
                        clientsInfo.getBirthday().getMonth() == currentMonth &&
                        repository.findPhoneByPhone(clientsInfo.getPhone()) == null) {
                    Client client = clientMapper.clientsInfoToClient(clientsInfo);
                    repository.save(client);
                    log.info("Saved client: " + clientsInfo.getName());
                }
            }

            if (LocalTime.now().isBefore(LocalTime.of(19, 0))) {
                List<Client> unsentMessages = repository.findAllUnsentMessages();
                for (Client client : unsentMessages) {
                    Message message = clientMapper.clientToMessage(client, getMyVariableValue());
                    kafkaTemplate.send("example-topic", message);
                    client.setMessageSend(true);
                    repository.save(client);
                    log.info("Sent message for client: " + client.getFullName());
                }
            }


            log.info("Finished processClientsAndSendMessages method.");
        } catch (Exception e) {
            log.error("An error occurred in processClientsAndSendMessages method", e);
        }
    }

    @Override
    public void getClientsById(String id) {
        log.info("Getting client with ID: " + id);

        try {
            Optional.ofNullable(feignClients.getClientsById(id))
                    .filter(clientsInfo -> clientsInfo.getPhone().endsWith("7") && clientsInfo.getBirthday().getMonth() == currentMonth)
                    .ifPresent(clientsInfo -> {
                        repository.save(clientMapper.clientsInfoToClient(clientsInfo));
                        log.info("Saved client with ID: " + id);
                    });

            log.info("Finished getClientsById method.");
        } catch (Exception e) {
            log.error("An error occurred in getClientsById method for client with ID: " + id, e);
        }
    }
}





