package qa.utils;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.io.Files;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import static java.nio.charset.StandardCharsets.UTF_8;
import static qa.utils.DriverUtils.driver;
import static qa.utils.constants.Constants.resultDocumentsPath;
import static qa.utils.constants.Constants.screenshotsFolderPath;
import static qa.steps.MyDemoWebSteps.scenarioName;

public class FileUtil {
    private static final String jsonfilePath=System.getProperty("user.dir")+File.separator+"src"+
            File.separator+ "test"+ File.separator+ "resources"+File.separator+ "Jsontestdata" + File.separator;
    static Properties props = new Properties();
    static FileInputStream ip=null;

    private static String propertyfilepath = System.getProperty("user.dir")+ File.separator+"src"+
            File.separator+"test"+File.separator+"resources"+File.separator+ "properties"+
            File.separator+"environment.properties";

//    public static String getProperty(String property)
//    {
//        try{
//            System.out.println("Selected Env is:" + System.getProperty("testEnv"));
//            String env=System.getProperty("testEnv");
//            if(env.length() >0)
//            {
//                switch(env)
//                {
//                    case "int":
//                        propertyfilepath=System.getProperty("user.dir")+ File.separator+"src"+
//                                File.separator+"test"+File.separator+"resources"+File.separator+ "properties"+
//                                File.separator+"int.properties";
//                        break;
//                    case "Regression":
//                        propertyfilepath=System.getProperty("user.dir")+ File.separator+"src"+
//                                File.separator+"test"+File.separator+"resources"+File.separator+ "properties"+
//                                File.separator+"regression.properties";
//                        break;
//                    case "test":
//                        propertyfilepath=System.getProperty("user.dir")+ File.separator+"src"+
//                                File.separator+"test"+File.separator+"resources"+File.separator+ "properties"+
//                                File.separator+"test.properties";
//                        break;
//                }
//            }
//            ip=new FileInputStream(propertyfilepath);
//            props.load(ip);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//        return props.getProperty(property);
//    }

    public static String readJSONFile(String fileName)
    {
        String fileData="";
        try{
            File file=new File(jsonfilePath+fileName);
            fileData= Files.toString(file,UTF_8);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return fileData;
    }

    public static JSONObject getDataFromJsonFile(String FileName, String Scenario)
    {
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj=(JSONObject) parser.parse(new FileReader(System.getProperty("user.dir")+
                    File.separator+"src"+File.separator+"test"+File.separator+ "resources"+File.separator+"Jsontestdata"+File.separator+FileName+".json"));
            JSONArray arr;
            if(Scenario.equals("TC001")) {
                arr = (JSONArray) obj.get("Scenarios1");
            } else if (Scenario.equals("TC002")) {
                arr = (JSONArray) obj.get("Scenarios2");
            }
            else
            {
                arr=null;
                System.out.println("Error: please provide correct Sceanario name");
                System.out.println("Scenario"+Scenario);
            }

            // System.out.println("Array :" + arr.size());
                String dataId;
            for (int i=0;i<arr.size();i++)
            {
                JSONObject data=(JSONObject) arr.get(i);
                dataId=(String)data.get("sc_id");
                if (dataId.equalsIgnoreCase(Scenario))
                {
                    //System.out.println((String)data.get("loginID"));
                    //System.out.println((String)data.get("password"));
                    //System.out.println((String) data.get("url"));
                    return data;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /* Method Name : updateDoc
     * Description : In this function we will consolidate all screenshots in a word document. This will be execute after each scenario.
     * Parametes   : scenarioStatus
     * Author      : Aasta Uppal
     */
    public static void updateDoc(String scenarioStatus) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        File ssFolder = new File(screenshotsFolderPath);
        if(ssFolder.exists()) {
            System.out.println("Updating Word Document Now");

            File resultsDir = new File(resultDocumentsPath);
            if (!resultsDir.exists()) {
                resultsDir.mkdir();
            }

            String timestamp = String.valueOf(System.currentTimeMillis());
            String fileName = timestamp + "_" + scenarioName + "_" + scenarioStatus + ".docx";
            File destfile = new File(resultDocumentsPath + fileName);
            destfile.getParentFile().mkdirs();
            destfile.createNewFile();

            XWPFDocument doc = new XWPFDocument();

            File srcfile = new File(screenshotsFolderPath + scenarioName + "\\");
            File[] list = srcfile.listFiles();

            System.out.println("Source folder item list " + list.length);

            for (int k = 0; k < list.length; k++) {
                if (list[k].isFile()) {
                    String ssName = list[k].getName().replaceAll("\\.\\w+$", "");
                    System.out.println("File - " + ssName);
                    String[] split_ssName = ssName.split("_");
                    String description = split_ssName[1];
                    System.out.println("Description - " + description);
                    String status = split_ssName[2];
                    System.out.println("Status - " + status);

                    XWPFParagraph p = doc.createParagraph();
                    XWPFRun r = p.createRun();

                    r.setText("Step: " + description);
                    r.addBreak();
                    r.setText("Status: " + status);
                    r.addBreak();

                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(list[k].getPath());
                        r.addPicture(fis, XWPFDocument.PICTURE_TYPE_PNG,
                                list[k].getPath(), Units.toEMU(500), Units.toEMU(250));
                    } catch (IOException e) {
                        System.out.println("Failed to add picture: " + e.getMessage());
                    } finally {
                        if (fis != null) {
                            fis.close();
                        }
                    }

                    list[k].delete();

                } else if (list[k].isDirectory()) {
                    System.out.println("Directory - " + list[k].getName());
                }
            }

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(destfile);
                doc.write(fos);
            } catch (IOException e) {
                System.out.println("Failed to write document: " + e.getMessage());
            } finally {
                if (fos != null) {
                    fos.close();
                }
            }

            doc.close();
        } else {
            System.out.println("No Screenshot folder available");
        }
    }
    /* Method Name  : captureScreenShot
     * Description  : In this function we will capture screenshots and store them in the test case folder under screenshots
     * Parameters    : description , status
     * Author       : Aastha Uppal
     */

    public static void captureScreenShot(String description, String status){
        try {
            File ssDir = new File(screenshotsFolderPath);
            if(!ssDir.exists()){
                ssDir.mkdirs(); // Create parent directories if they don't exist
            }

            String screenshot_name = System.currentTimeMillis() + "_" + description + "_" + status + ".png";

            // Saving the screenshot in the desired location
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destinationDir = new File(screenshotsFolderPath, scenarioName);
            if (!destinationDir.exists()) {
                destinationDir.mkdirs(); // Create scenario directory if it doesn't exist
            }

            File destination = new File(destinationDir, screenshot_name);
            FileUtils.copyFile(source, destination);
            System.out.println(screenshot_name + " - Screenshot is captured");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
