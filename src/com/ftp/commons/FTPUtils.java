package com.ftp.commons;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPUtils {

	public static FTPFile[] readFTP(String address, String user, String password) {
		FTPClient client = new FTPClient();
		FTPFile[] files = new FTPFile[0];

		try {
			client.connect(address);
			client.enterLocalPassiveMode();
			client.login(user, password);
			client.changeWorkingDirectory("hrratings");

			files = client.listFiles();

			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return files;

	}

	public static List<FTPFile> filterByDate(FTPFile[] files, Date date) {
		List<FTPFile> res = new ArrayList<>();

		for (FTPFile item : files) {

			if (date2String(date, "d-MMM-yyyy").equals(date2String(item.getTimestamp().getTime(), "d-MMM-yyyy"))) {
				res.add(item);
			}
		}

		return res;
	}

	private static String date2String(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static void downloadFile(String address, String user, String password, String nombre) {
		System.out.println("Empezando a descargar el archivo " + nombre);
		FTPClient client = new FTPClient();
		String nameFileOut="";
		
		if(nombre.contains("Calificaciones")) {
			System.out.println("Este es el archivo de calificaciones");
			nameFileOut = "infosel_instrumentos_temporal.csv";
		}
		else {
			nameFileOut = nombre.toString();
		}

		try {
			client.connect(address);
			client.enterLocalPassiveMode();
			client.login(user, password);
			client.changeWorkingDirectory("hrratings");

			//OutputStream out = new FileOutputStream("/home/despinosa/FTPImport/Files/" +nameFileOut);
			OutputStream out = new FileOutputStream("/opt/FTPImport/Files/" +nameFileOut);
			//OutputStream out = new FileOutputStream("/Users/alanramirez/Documents/FTPImport/Files/" + nameFileOut);

			client.retrieveFile(nombre, out);

			out.close();
			client.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
