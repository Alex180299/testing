package com.aortiz.demo.configuration;

import com.aortiz.demo.model.Layout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class LoadConfiguration
{
    @Autowired
    private FileLoader fileLoader;

    @PostConstruct
    public void load() throws Exception
    {
        String path = "./config/layout.xml";
        fileLoader.readFile(path, Layout.class);
    }
}
