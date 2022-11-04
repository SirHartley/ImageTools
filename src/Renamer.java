import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Renamer {
    public static String folderURL = "C:\\Users\\philipp\\Desktop\\DataAnalytics\\graphics\\illustrated_entities\\png";

    public static void main(String[] args) throws IOException {
        int num = 0;

        final File folder = new File(folderURL);

        for (final File fileEntry : folder.listFiles()) {
            num++;
            System.out.println("Image: " + num);
            fileEntry.renameTo(new File(folderURL +"\\"+ num + ".png"));
        }
    }
}
