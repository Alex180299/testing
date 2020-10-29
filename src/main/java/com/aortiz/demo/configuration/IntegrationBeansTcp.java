package com.aortiz.demo.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.tcp.TcpOutboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractClientConnectionFactory;
import org.springframework.integration.ip.tcp.connection.DefaultTcpNetSocketFactorySupport;
import org.springframework.integration.ip.tcp.connection.DefaultTcpSocketSupport;
import org.springframework.integration.ip.tcp.connection.TcpNetClientConnectionFactory;
import org.springframework.messaging.MessageChannel;

@EnableIntegration
@IntegrationComponentScan
@Configuration
public class IntegrationBeansTcp
{
    @Bean
    public DefaultTcpNetSocketFactorySupport socketFactorySupport()
    {
        return new DefaultTcpNetSocketFactorySupport();
    }

    @Bean
    public AbstractClientConnectionFactory connectionFactory()
    {
        TcpNetClientConnectionFactory connectionFactory = new TcpNetClientConnectionFactory("localhost", 1234);
        connectionFactory.setTcpSocketFactorySupport(socketFactorySupport());
        connectionFactory.setTcpSocketSupport(new DefaultTcpSocketSupport(false));
        return connectionFactory;
    }

    @Bean
    public MessageChannel inputChannel()
    {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel outputChannel()
    {
        return new DirectChannel();
    }

    @ServiceActivator(inputChannel = "outputChannel")
    public TcpOutboundGateway tcpOut()
    {
        TcpOutboundGateway tcpOut = new TcpOutboundGateway();
        tcpOut.setConnectionFactory(connectionFactory());
        tcpOut.setOutputChannelName("receiveChannel");
        tcpOut.setRequestTimeout(10000);
        return tcpOut;
    }
}
