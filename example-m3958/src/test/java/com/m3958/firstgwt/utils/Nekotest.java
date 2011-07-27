package com.m3958.firstgwt.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.cyberneko.html.parsers.DOMParser;
import org.junit.Test;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class Nekotest {
	
	private void print(Node node, String indent) {
        System.out.println(indent+node.getClass().getName());
        Node child = node.getFirstChild();
        while (child != null) {
            print(child, indent+" ");
            child = child.getNextSibling();
        }
    }
	
	@Test
	public void testMe() throws SAXException, IOException, ClassCastException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        DOMParser parser = new DOMParser();
        BufferedReader in = new BufferedReader(new FileReader("e:/psalmscn.html"));
        parser.parse(new InputSource(in));
        print(parser.getDocument(), "");
        
        Document d = parser.getDocument();
//        XMLInputSource source = new XMLInputSource(d);
//        XMLDocumentFilter writer = new Writer();
        
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        DOMImplementationLS impl = 
            (DOMImplementationLS)registry.getDOMImplementation("LS");
        File f = new File("e:/psalmscn.html");
        LSSerializer writer = impl.createLSSerializer();
        DOMConfiguration dcf = writer.getDomConfig();
        dcf.setParameter("xml-declaration", false);
        String str = writer.writeToString(d);
        writer.writeToURI(d, "/e:/psalmscn.html");
        System.out.println(str);
        System.out.println(f.getCanonicalPath());
	}

}
