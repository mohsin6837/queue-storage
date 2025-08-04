package com.example.queue_storage.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.queue_storage.service.AzureQueueService;

@RestController
@RequestMapping("/queue")
public class QueueController {

    private final AzureQueueService queueService;

    public QueueController(AzureQueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping("/send")
    public String send(@RequestBody String message) {
        queueService.sendMessage(message);
        return "Message sent!";
    }

    @GetMapping("/receive")
    public String receive() {
        return queueService.receiveMessage();
    }
}
