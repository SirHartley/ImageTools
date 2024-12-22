import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Renamer {
    public static String folderURL =
            "C:\\Users\\Philipp\\Downloads\\imgs\\re-numbered, post correction, post rezise and filters";
    public static String moveToFolder = "C:\\Users\\Philipp\\Downloads\\imgs\\re-numbered, post correction, post rezise and filters\\complete";

    public static void main(String[] args) throws IOException {
       sortImagesByColorProfile(folderURL, moveToFolder);
    }

    public static void sort(){
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


    public static void sortImagesByColorProfile(String sourceFolder, String destinationFolder) throws IOException {
        File source = new File(sourceFolder);
        File destination = new File(destinationFolder);

        if (!destination.exists()) {
            boolean created = destination.mkdirs();
            if (!created) {
                System.out.println("Failed to create destination folder.");
                return;
            }
        }

        File[] imageFiles = source.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg"));
        if (imageFiles == null || imageFiles.length == 0) {
            System.out.println("No images found in the source folder.");
            return;
        }

        Map<File, Color> imageColors = new HashMap<>();

        // Extract average color for each image
        for (File imageFile : imageFiles) {
            BufferedImage image = ImageIO.read(imageFile);
            if (image != null) {
                Color avgColor = calculateAverageColor(image);
                imageColors.put(imageFile, avgColor);
            }
        }

        // Sort images by hue to follow a rainbow order
        List<Map.Entry<File, Color>> sortedEntries = imageColors.entrySet().stream()
                .sorted(Comparator.comparing(entry -> getHue(entry.getValue())))
                .collect(Collectors.toList());

        // Rename and move files sequentially
        int count = 1;
        for (Map.Entry<File, Color> entry : sortedEntries) {
            File imageFile = entry.getKey();
            File newFile = new File(destination + "\\" + count + ".png");
            boolean moved = imageFile.renameTo(newFile);
            System.out.println("Image: " + imageFile.getName() + " moved and renamed to: " + newFile.getName() + " successfully: " + moved);
            count++;
        }
    }

    private static Color calculateAverageColor(BufferedImage image) {
        long sumRed = 0, sumGreen = 0, sumBlue = 0;
        int count = 0;

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color pixelColor = new Color(image.getRGB(x, y));
                sumRed += pixelColor.getRed();
                sumGreen += pixelColor.getGreen();
                sumBlue += pixelColor.getBlue();
                count++;
            }
        }

        return new Color((int) (sumRed / count), (int) (sumGreen / count), (int) (sumBlue / count));
    }

    private static float getHue(Color color) {
        float[] hsbValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        return hsbValues[0]; // Hue value in HSB (Hue, Saturation, Brightness)
    }
}
