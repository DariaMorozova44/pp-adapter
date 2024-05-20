package com.example.pp.mapper;

import com.example.pp.model.Client;
import com.example.pp.model.ClientsInfo;
import com.example.pp.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "fullName", expression = "java(clientsInfo.getName() + ' ' + clientsInfo.getMiddleName() + ' ' + clientsInfo.getSurname())")
    @Mapping(target = "phone", source = "clientsInfo.phone")
    @Mapping(target = "birthday", source = "clientsInfo.birthday")
    @Mapping(target = "messageSend", ignore = true)
    Client clientsInfoToClient(ClientsInfo clientsInfo);


    @Mapping(target = "message", expression = "java(concat1(client,string))")
    @Mapping(target = "phone", source = "client.phone")
    Message clientToMessage(Client client, String string);

    default String concat1(Client client, String string) {
        String[] words = client.getFullName().split(" ", 3);
        return String.format("%s %s , в этом месяце для вас действует скидка %s", words[0], words[1], string);

    }
}
