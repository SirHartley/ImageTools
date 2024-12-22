import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Renamer {
    public static String folderURL =
            "C:\\Users\\Philipp\\Downloads\\imgs\\re-numbered, post correction, post rezise and filters";
    public static String moveToFolder = "C:\\Users\\Philipp\\Downloads\\imgs\\re-numbered, post correction, post rezise and filters\\complete";

    public static void main(String[] args) throws IOException {
        int num = 0;

        final File folder = new File(folderURL);
        final File destinationFolder = new File(moveToFolder);

        if (!destinationFolder.exists()) {
            boolean created = destinationFolder.mkdirs();
            if (!created) {
                System.out.println("Failed to create destination folder.");
                return;
            }
        }

        for (File fileEntry : new ArrayList<>(List.of(folder.listFiles()))) {
            if (fileEntry.isFile()) {
                num++;
                File newFile = new File(moveToFolder + "\\" + num + ".png");
                boolean moved = fileEntry.renameTo(newFile);
                System.out.println("Image: " + num + " moved and renamed successfully: " + moved);
            }
        }
    }
}