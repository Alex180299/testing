package com.aortiz.demo.configuration;

import lombok.extern.log4j.Log4j2;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import java.io.File;

@Log4j2
@Component
public class FileLoader
{
    private void existFile(String url) throws Exception
    {
        if (url == null || url.trim().isEmpty() || !new File(url).exists() || new File(url).isDirectory())
        {
            throw new Exception("Validar que la propiedad sea correcta o exista el archivo : " + url);
        }
    }

    public <T> T readFile(String urlFile, Class tClass) throws Exception
    {
        existFile(urlFile);

        if (urlFile.endsWith(".xml"))
        {
            SAXReader reader = new SAXReader();
            Document document = reader.read(urlFile);
            Element root = document.getRootElement();
            treeWalk(root);
        }
        else
        {
            throw new Exception("Extensión del archivo \"" + urlFile + "\" no soportada");
        }

        return null;
    }

    public void treeWalk(Element element)
    {
        for (int i = 0, size = element.nodeCount(); i < size; i++)
        {
            Node node = element.node(i);
            if (node instanceof Element)
            {
                treeWalk((Element) node);
                log.info(node.getName() + ": " + ((Element) node).attributeValue("id") + " - Padre; " + node.getParent().getName() + ": " + node.getParent().attributeValue("id"));
            }
        }
    }
}
