package com.ftp.main;

import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;

import com.ftp.commons.FTPUtils;

public class Main {
	
	private static final String HOST = "";
	private static final String USER = "";
	private static final String PASSWORD = "";

	public static void main(String[] args) {
		Date yesterday = new Date();
		yesterday.setTime(yesterday.getTime() - 24 * 60 * 60 * 1000);

		FTPFile[] files = FTPUtils.readFTP(HOST, USER, PASSWORD);
		List<FTPFile> filtrados = FTPUtils.filterByDate(files, yesterday);

		for (FTPFile item : filtrados) {
			FTPUtils.downloadFile(HOST, USER, PASSWORD, item.getName());
		}
	}

}
