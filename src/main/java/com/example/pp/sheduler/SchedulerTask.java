package com.example.pp.sheduler;

import com.example.pp.service.ClientServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Data
@Component
public class SchedulerTask {
    private final ClientServiceImpl clientServiceImpl;


    @Scheduled(cron = "${scheduler.cron-expression}")
    public void getClientAndSendMessage() {
        clientServiceImpl.processClientsAndSendMessages();
    }
}
