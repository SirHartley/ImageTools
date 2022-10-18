import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Cutter {

    public static String folderURL = "E:\\Onedrive\\Desktop\\asd\\output2\\";
    public static String outputFolderURL = "E:\\Onedrive\\Desktop\\asd\\output2\\output\\";

    public static void main(String[] args) throws IOException {
        cutter();
    }

    private static void cutter() throws IOException {
        int num = 0;

        while (true) {
            num++;
            System.out.println("Image: " + num);

            String pre = " " + num;
            String imageURL = folderURL + pre + ".png";
            if (!Files.isReadable(Paths.get(imageURL))) continue;

            BufferedImage image = ImageIO.read(new File(imageURL));

            // initalizing rows and columns
            int rows = 2;
            int columns = 2;

            // initializing array to hold subimages
            BufferedImage imgs[] = new BufferedImage[4];

            // Equally dividing original image into subimages
            int subimage_Width = image.getWidth() / columns;
            int subimage_Height = image.getHeight() / rows;

            int current_img = 0;

            // iterating over rows and columns for each sub-image
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    // Creating sub image
                    imgs[current_img] = new BufferedImage(subimage_Width, subimage_Height, image.getType());
                    Graphics2D img_creator = imgs[current_img].createGraphics();

                    // coordinates of source image
                    int src_first_x = subimage_Width * j;
                    int src_first_y = subimage_Height * i;

                    // coordinates of sub-image
                    int dst_corner_x = subimage_Width * j + subimage_Width;
                    int dst_corner_y = subimage_Height * i + subimage_Height;

                    img_creator.drawImage(image, 0, 0, subimage_Width, subimage_Height, src_first_x, src_first_y, dst_corner_x, dst_corner_y, null);
                    current_img++;
                }
            }

            //writing sub-images into image files
            for (int i = 0; i < 4; i++) {
                File outputFile = new File(outputFolderURL + num + "_" + i + ".png");
                ImageIO.write(imgs[i], "png", outputFile);
            }
        }
    }
}
