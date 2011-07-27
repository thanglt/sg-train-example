package com.m3958.firstgwt.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTMLWriter;
import javax.swing.text.html.parser.ParserDelegator;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class DomTest {
	
	@Test
	public void testMe() throws ParserConfigurationException, SAXException, IOException, BadLocationException{

//	    URL url = new URL("http://www.google.com");
//	    URLConnection connection = url.openConnection();
//	    InputStream is = connection.getInputStream();
//	    InputStreamReader isr = new InputStreamReader(is);
		URL url = this.getClass().getResource("psalmscn.html");

	    BufferedReader br = new BufferedReader(new FileReader(url.getFile()));

	    HTMLEditorKit htmlKit = new HTMLEditorKit();
	    HTMLDocument htmlDoc = (HTMLDocument) htmlKit.createDefaultDocument();
	    HTMLEditorKit.Parser parser = new ParserDelegator();
	    HTMLEditorKit.ParserCallback callback = htmlDoc.getReader(0);
	    parser.parse(br, callback, true);
	    
//	    HTMLDocument hd = (HTMLDocument) htmlKit.createDefaultDocument();
	    Document d = (Document) htmlDoc;
	    Element e = htmlDoc.getElement("swing");
	    htmlDoc.setInnerHTML(e, "<p>hello inseted p!</p>");

	    FileWriter fw = new FileWriter("e:/htmlout.html");
	    
	    HTMLWriter hw = new HTMLWriter(fw,htmlDoc);
	    hw.write();
	    fw.close();
	    

	    // Parse
	    ElementIterator iterator = new ElementIterator(htmlDoc);
	    Element element;
	    while ((element = iterator.next()) != null) {
	    	System.out.println(element.getName());
	    	System.out.println(element.getAttributes());
	    }
//	    while ((element = iterator.next()) != null) {
//	      AttributeSet attributes = element.getAttributes();
//	      Object name = attributes.getAttribute(StyleConstants.NameAttribute);
//	      if ((name instanceof HTML.Tag)
//	          && ((name == HTML.Tag.H1) || (name == HTML.Tag.H2) || (name == HTML.Tag.H3))) {
//	        // Build up content text as it may be within multiple elements
//	        StringBuffer text = new StringBuffer();
//	        int count = element.getElementCount();
//	        for (int i = 0; i < count; i++) {
//	          Element child = element.getElement(i);
//	          AttributeSet childAttributes = child.getAttributes();
//	          if (childAttributes
//	              .getAttribute(StyleConstants.NameAttribute) == HTML.Tag.CONTENT) {
//	            int startOffset = child.getStartOffset();
//	            int endOffset = child.getEndOffset();
//	            int length = endOffset - startOffset;
//	            text.append(htmlDoc.getText(startOffset, length));
//	          }
//	        }
//	        System.out.println(name + ": " + text.toString());
//	      }
//	    }
		
	}

}
