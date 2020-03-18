package com.stanra.csvtopdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.*;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class HtmlToPdf {

	
	public static void mainClass() throws Exception {

		//DataVariables dataVariables = new DataVariables();

		/*
		 * // read from csv Scanner sc = new Scanner(new File("D:\\details.csv")); int
		 * count = 0; String line; sc.useDelimiter(","); while (sc.hasNextLine()) {
		 * dataVariables.setEmpid(sc.next()); dataVariables.setName(sc.next());
		 * 
		 * 
		 * // find and returns the next complete token from this scanner } sc.close();
		 */
		/*
		 * dataVariables.setTemplateName("Templete.html");
		 * 
		 * System.out.println(dataVariables.empid);
		 * System.out.println(dataVariables.name);
		 * System.out.println(dataVariables.templateName);
		 * System.out.println(dataVariables.getName());
		 */
		//getPdfFile(dataVariables);

	}

	public static void getPdfFile(DataVariables dataVariables) throws Exception { 

		VelocityEngine velocityEngine = new VelocityEngine();

		Properties propss = new Properties();

		propss.setProperty("resource.loader", "class");
		propss.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
		propss.setProperty("runtime.log.logsystem.log4j.category", "velocity");
		propss.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
		propss.setProperty("class.resource.loader.class",
				"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

		velocityEngine.init(propss);
		
		Map model = new HashMap();
		model.put("customTemplate", dataVariables);
		
		//System.out.println(model.get("customTemplate"));

		String notificationMsg = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				dataVariables.getTemplateName(),"UTF-8", model);

		String dataForHtml = notificationMsg;
		File file =  new File("src/main/resources/" +dataVariables.getName() + ".html");		// This templet will generate after execution
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		bw.write(dataForHtml);
		bw.close();
		
		
		//dataVariables.setTemplateName("src/main/resources/" +dataVariables.getName() + ".html");
		// System.out.println(notificationMsg);
		
		file = new File("src/main/resources/" + dataVariables.getName() + ".pdf");
		
		generatePDF("src/main/resources/" +dataVariables.getName() + ".html","src/main/resources/"+dataVariables.name+".pdf" , dataVariables); // This is templete from where we read html data
		
		System.out.print("\n\nTemplete generation for  " + dataVariables.getName() + " Done!");

	}

	

	public static void generatePDF(String inputHtmlPath, String outputPdfPath, DataVariables dataVariables) {
		try {
			String url = new File(inputHtmlPath).toURI().toURL().toString();
			
//			System.out.println("URL: " + url);

			OutputStream out = new FileOutputStream(outputPdfPath);

			// Flying Saucer part
			ITextRenderer renderer = new ITextRenderer();

			renderer.setDocument(url);

			renderer.layout();
			renderer.createPDF(out);

			out.close();
			File file = new File("src/main/resources/" +dataVariables.getName() + ".html");
			file.delete();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void deletePdf(String name)
	{
		try
		{
		File file = new File(name);
		file.delete();
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

}
