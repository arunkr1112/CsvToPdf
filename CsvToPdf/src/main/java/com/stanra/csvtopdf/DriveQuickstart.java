package com.stanra.csvtopdf;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Export;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.*;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DriveQuickstart {

	static int row;
	static int colm;
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";

	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	// Directory to store user credentials for this application.
	private static final java.io.File CREDENTIALS_FOLDER //
			= new java.io.File(System.getProperty("user.home"), "credentials");

	private static final String CLIENT_SECRET_FILE_NAME = "client_secret.json";

	//
	// Global instance of the scopes required by this quickstart. If modifying these
	// scopes, delete your previously saved credentials/ folder.
	//
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

		java.io.File clientSecretFilePath = new java.io.File(CREDENTIALS_FOLDER, CLIENT_SECRET_FILE_NAME);

		if (!clientSecretFilePath.exists()) {
			throw new FileNotFoundException("Please copy " + CLIENT_SECRET_FILE_NAME //
					+ " to folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
		}

		// Load client secrets.
		InputStream in = new FileInputStream(clientSecretFilePath);

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(new FileDataStoreFactory(CREDENTIALS_FOLDER))
						.setAccessType("offline").build();

		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	}

	public static void main(String... args) throws IOException, GeneralSecurityException {

		System.out.println("CREDENTIALS_FOLDER: " + CREDENTIALS_FOLDER.getAbsolutePath());

		// 1: Create CREDENTIALS_FOLDER
		if (!CREDENTIALS_FOLDER.exists()) {
			CREDENTIALS_FOLDER.mkdirs();

			System.out.println("Created Folder: " + CREDENTIALS_FOLDER.getAbsolutePath());
			System.out.println("Copy file " + CLIENT_SECRET_FILE_NAME + " into folder above.. and rerun this class!!");
			return;
		}

		// 2: Build a new authorized API client service.
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		// 3: Read client_secret.json file & create Credential object.
		Credential credential = getCredentials(HTTP_TRANSPORT);

		// 5: Create Google Drive Service.
		Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential) //
				.setApplicationName(APPLICATION_NAME).build();

		/**
		 * THIS SECTION WILL CREATE FOLDER WITH GIVEN NAME AND UPLOAD FILE WITH GIVEN
		 * NAME
		 *
		 * Above this quickStart fields are available
		 */

		DataVariables dataVariables = new DataVariables();
		ArrayList<String> strlistdata = new ArrayList<>();
		double total = 0.0;

//			THIS IS CODE TO GET FILE FROM GOOGLE DRIVE   ////////////////////////
		strlistdata = MyClass.printFile(service, "1aHJwgaY6hAQsf2CxMGX0Ig_l9kw7tqtV");

		int j = 0;
		String[] str1 = new String[strlistdata.size()];
		
		//System.out.println(strlistdata.size());
		
		for (int i = 1; i < strlistdata.size(); i++) {
			str1[i - 1] = strlistdata.get(i);
		}
		
		for (int k = 0; k < str1.length - 1; k++) {
			row++;
			colm=1;
			try {

			String[] str2 = str1[k].split(","); 						
			dataVariables.setEmpid(str2[0].trim()); 					colm++;
			dataVariables.setName(str2[1].trim());						colm++;
			dataVariables.setDate(str2[2].trim());						colm++;
			dataVariables.setNo_of_working_day(Integer.parseInt(str2[3].trim()));	colm++;
			dataVariables.setTotal_working_days(Integer.parseInt(str2[4].trim()));	colm++;
			dataVariables.setPayment_mode(str2[5].trim());							colm++;			
			dataVariables.setBasic(new Double(str2[6].trim()).doubleValue());		colm++;		
			dataVariables.setHra(new Double(str2[7].trim()).doubleValue());			colm++;
			dataVariables.setTravel(new Double(str2[8].trim()).doubleValue());		colm++;
			dataVariables.setMedical(new Double(str2[9].trim()).doubleValue());		colm++;
			dataVariables.setEmail(str2[10].trim());								colm++;				

			total = dataVariables.getBasic() + dataVariables.getHra() + dataVariables.getTravel()
					+ dataVariables.getMedical();
			dataVariables.setTotal(total);
			dataVariables.setTemplateName("Templete.html");
			try {

				HtmlToPdf.getPdfFile(dataVariables); // Call to HtmlToPdf class for getPdfFile() function

			} catch (Exception e) {

				e.printStackTrace();
			}
			
			
			//System.out.println("Drive data:" + dataVariables);
			

			System.out.println();

			/**
			 * code to upload pdf on google drive of given
			 */
			System.out.print("" + dataVariables.getName() + ".pdf uploading..."); 
			String folderId = "1lZiihKZF3MjqLgU6tLpDFDGPdR1SBMar";
			File fileMetadata = new File();
			fileMetadata.setName("" + dataVariables.getName() + ".pdf");
			fileMetadata.setParents(Collections.singletonList(folderId));
			java.io.File filePath = new java.io.File("src/main/resources/"+dataVariables.name+".pdf");
			FileContent mediaContent = new FileContent("application/pdf", filePath);
			File file = service.files().create(fileMetadata, mediaContent).setFields("id, parents").execute();		
//			System.out.println("File ID: " + file.getId());
			System.out.println("  Done!");
			
			String pdffilename = "src/main/resources/" +dataVariables.getName() + ".pdf";
			String email = dataVariables.getEmail();
			SendAttachment.Mailmain(pdffilename, email, dataVariables.getName());
			HtmlToPdf.deletePdf(pdffilename);
			}
			catch(Exception e)
			{
				System.err.println("Unappropriate data at Row no " + row + " and column no " + colm);
			}
			
		}

		System.out.println("Every thing done!");
		

	}

}