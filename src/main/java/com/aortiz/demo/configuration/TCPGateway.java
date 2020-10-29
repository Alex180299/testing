package com.aortiz.demo.configuration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface TCPGateway
{
    @Gateway(requestChannel = "gatewaySendChannel")
    String send(String message);
}
