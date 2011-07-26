package com.spms.test.common;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLEncoder;
import java.util.Arrays;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestFtp {

	private Logger log = LoggerFactory.getLogger(TestFtp.class);

	private FTPClient ftp = new FTPClient();

	private String server = "localhost";

	@Before
	public void init() throws SocketException, IOException {

		ftp.setControlEncoding("GBK");
		
		ftp.connect(server);
		log.info("Connected to " + server + ".");
		log.info(ftp.getReplyString());

		// anonymous
		ftp.login("super", "SkyNet2010");

		int reply = ftp.getReplyCode();

		if (!FTPReply.isPositiveCompletion(reply)) {
			ftp.disconnect();
			log.error("FTP server refused connection.");
			throw new FTPConnectionClosedException();
		}

		ftp.enterLocalPassiveMode();
	}

	@After
	public void close() {
		try {
			ftp.logout();
		} catch (IOException e) {
			log.error("io oper error:", e);
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
					// do nothing
				}
			}
		}
	}

	@Test
	public void list() throws IOException {

		String[] list = ftp.listNames();

		log.debug("start path:" + Arrays.asList(list));
		ftp.changeWorkingDirectory("/demo");

		FTPFile[] files = ftp.listFiles();

		for (FTPFile file : files) {
			log.debug("name:" + file.getName());
			log.debug("prop:" + file.getSize());
			log.debug("type:" + file.getType());
		}
	}

	@Test
	public void put() throws FileNotFoundException, IOException {

		ftp.changeWorkingDirectory("/demo/test");

		ftp.setFileType(FTP.BINARY_FILE_TYPE);

		InputStream input = new FileInputStream("D:/other/���Կ���.txt");

		boolean sign = ftp.storeFile("foo.txt", input);

		input.close();

		int reply = ftp.getReplyCode();
		log.info("reply:" + reply);
		if (!FTPReply.isPositiveCompletion(reply)) {
			fail();
		}

	}

	@Test
	public void get() throws IOException {
		ftp.changeWorkingDirectory("/demo/test");

		ftp.setFileType(FTP.BINARY_FILE_TYPE);

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		String fileName=URLEncoder.encode("����.txt","UTF-8");
		fileName="����.txt";
		ftp.retrieveFile(fileName, output);

		String ctx = output.toString("GBK");
		log.debug(ctx);
		output.close();

		int reply = ftp.getReplyCode();
		log.info("reply:" + reply);
		if (!FTPReply.isPositiveCompletion(reply)) {
			fail();
		}

	}
}
