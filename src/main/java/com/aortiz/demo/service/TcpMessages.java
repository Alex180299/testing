package com.aortiz.demo.service;


import com.aortiz.demo.configuration.TCPGateway;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TcpMessages
{
    @Autowired
    TCPGateway tcpGateway;

    @Autowired
    MessageChannel outputChannel;

    @ServiceActivator(inputChannel = "gatewaySendChannel")
    public void sendMessage(Message<String> message)
    {
        Message<String> messageResponse = MessageBuilder.withPayload("MessageRecieved: " + message.getPayload()).copyHeadersIfAbsent(message.getHeaders()).build();
        log.info("Sending message: " + message.toString());
        outputChannel.send(messageResponse);
    }

    @ServiceActivator(inputChannel = "receiveChannel")
    public void receiveMessage(Message<String> message)
    {
        log.info("Message received: " + message.toString());
        tcpGateway.send("Message received: " + message.toString());
    }
}
