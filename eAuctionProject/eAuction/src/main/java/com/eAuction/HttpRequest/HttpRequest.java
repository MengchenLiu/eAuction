package com.eAuction.HttpRequest;

import java.io.PrintWriter;
import java.util.Scanner;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class HttpRequest {
	private PrintWriter printWriter;
	
	
	public PrintWriter getPrintWriter() {
		return printWriter;
	}

	public void setPrintWriter(PrintWriter printWriter) {
		this.printWriter = printWriter;
	}

	public void sendRequest(String data, String host, String url) {
		printWriter.write("POST " + url + " HTTP/1.1\r\n");
		printWriter.write("HOST: " + host + "\r\n");
		printWriter.write("Content-Length: " + data.length() + "\r\n");
		printWriter.write("Content-Type: application/x-www-form-urlencoded\r\n");
		printWriter.write("\r\n");
		printWriter.write(data);
		printWriter.flush();
		printWriter.write("\r\n");
		printWriter.flush();
	}

}
