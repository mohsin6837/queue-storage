package com.example.queue_storage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;

@Service
public class AzureQueueService {

    private final QueueClient queueClient;

    public AzureQueueService(
            @Value("${azure.storage.account-name}") String accountName,
            @Value("${azure.storage.queue-name}") String queueName
    ) {
        TokenCredential credential = new ManagedIdentityCredentialBuilder().build();

        String endpoint = String.format("https://%s.queue.core.windows.net", accountName);

        this.queueClient = new QueueClientBuilder()
                .endpoint(endpoint)
                .credential(credential)
                .queueName(queueName)
                .buildClient();

        this.queueClient.createIfNotExists();
    }

    public void sendMessage(String message) {
        queueClient.sendMessage(message);
    }

    public String receiveMessage() {
        return queueClient.receiveMessage().getBody().toString();
    }
}
