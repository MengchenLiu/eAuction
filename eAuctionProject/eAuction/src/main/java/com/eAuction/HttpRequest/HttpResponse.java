package com.eAuction.HttpRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class HttpResponse {
	private Scanner scanner;
	private List<String> respnse;
	
	public HttpResponse(Scanner scanner) {
		respnse = new ArrayList<>();
		int count = 0;
		while (scanner.hasNextLine()) {
			count++;
			String temp = scanner.nextLine();
			respnse.add(temp);
			System.out.println(temp);
			if(temp.contains("{") || temp.contains("[") || temp.equals("true") || temp.equals("false")) break;
		};
	}
	
	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public List<String> getRespnse() {
		return respnse;
	}

	public void setRespnse(List<String> respnse) {
		this.respnse = respnse;
	}

	
	
}
