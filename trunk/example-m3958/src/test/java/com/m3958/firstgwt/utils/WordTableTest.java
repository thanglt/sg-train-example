package com.m3958.firstgwt.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.m3958.firstgwt.server.types.CharactSetName;

public class WordTableTest {
	
	@Test
	public void tt(){
        File file = new File("E:/TDDOWNLOAD/eclipse-jee-helios-SR1-win32/xyz-cms/test/com/m3958/firstgwt/utils/wordtable.txt");
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;

        try {
            reader =new BufferedReader(new InputStreamReader(new FileInputStream(file),CharactSetName.UTF_8));
            String text = null;

            // repeat until all lines is read
            while ((text = reader.readLine()) != null) {
                contents.append(text)
                    .append(System.getProperty(
                        "line.separator"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        // show file contents here
        //java.util.regex.Pattern.compile(regex).matcher(str).replaceAll(repl)
        Pattern p = Pattern.compile("<tr\\s+.*?>", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
        String c = contents.toString();
        Matcher m = p.matcher(c);
        c = m.replaceAll("<tr>");
        p = Pattern.compile("<td\\s+.*?>", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
        m = p.matcher(c);
        c = m.replaceAll("<td>");
        
        p = Pattern.compile("<table\\s+.*?>", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
        m = p.matcher(c);
        c = m.replaceAll("<table>");
        System.out.println(c);
        
        Pattern p1 = Pattern.compile(".*[&<>].*", Pattern.MULTILINE|Pattern.DOTALL|Pattern.CASE_INSENSITIVE);
        Matcher mm = p1.matcher("abc<\nuuyyii&");
        Assert.assertTrue(mm.matches());
	}
}
