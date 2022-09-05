import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.util.Arrays;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    /**
     * On closing of the tomcat server all user files including the files holding the wining numbers are deleted for cleanup.
     * @param event event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        File folder = new File("../tomcat/");
        File[] listOfFiles = folder.listFiles();
        String[] existing = new String[]{"LICENSE", "README.md", "RELEASE-NOTES", "RUNNING.txt", "BUILDING.txt", "NOTICE", "NOTICE", "CONTRIBUTING.md"};
        String current;
        // Goes through all files in the tomcat directory.
        // If there are any files that aren't apart of the base files they are deleted for clean up.
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                current = listOfFiles[i].getName();
                if (!Arrays.stream(existing).anyMatch(current::contains)){
                    File myObj = new File(current);
                    myObj.delete();
                }
            }
        }
    }


}