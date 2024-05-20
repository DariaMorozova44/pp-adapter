package com.example.pp.controller;

import com.example.pp.service.ClientServiceImpl;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@OpenAPIDefinition(info = @Info(title = "Clients API", version = "1.0", description = "API for managing clients"))
@RestController
@RequiredArgsConstructor
public class ClientsController {

    private final ClientServiceImpl clientServiceImpl;

    @PostMapping("/pp/v1/getClient/{clientId}")
    @Operation(summary = "Get clients by ID", description = "Retrieve a specific client by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved client by ID"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public void getClientsById(@Parameter(description = "The ID of the client to retrieve") @PathVariable String clientId) {
        clientServiceImpl.getClientsById(clientId);
    }

    @PostMapping("/pp/v1/getClient")
    @Operation(summary = "Get all clients", description = "Process clients and send messages to eligible clients.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully processed clients and sent messages"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public void getClients() {
        clientServiceImpl.processClientsAndSendMessages();
    }

}