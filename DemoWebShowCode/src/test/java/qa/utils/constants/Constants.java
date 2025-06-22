package qa.utils.constants;
import java.io.File;
public class Constants {
    public static String configPath = File.separator + "src" + File.separator + "test" + File.separator + "resources";

    public static String testArtifactsPath = configPath+File.separator+"testArtifacts"+File.separator;

    public static String ObjectRepositoryPath=File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"properties"+File.separator+"ObjectRepository.properties";

    public static String resultDocumentsPath = System.getProperty("user.dir") + testArtifactsPath + "TestResults" + File.separator;

    public static String screenshotsFolderPath = System.getProperty("user.dir") + testArtifactsPath + "Screenshots" + File.separator;
}