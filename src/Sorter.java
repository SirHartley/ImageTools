import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Sorter {

    public static Image image;
    public static String
    folderURL = "C:\\Users\\Philipp\\Downloads\\imgs\\new\\",
    wFolderURL = folderURL + "good\\",
    aFolderURL = folderURL + "fix\\",
    sFolderURL = folderURL + "trash\\",
    dFolderURL = folderURL + "medium\\";

    public static void main(String[] args) throws IOException {
        sorter();
        //cutter();
    }

    private static void sorter() {
        Scanner scan = new Scanner(System.in);
        int i = 0;
        int frameWidth = 800;
        int frameHeight = 500;

        while (true) {
            i++;
            System.out.println("Image: " + i);

            String pre = "" + i;
            String imageURL = folderURL + pre + ".png";
            if (!Files.isReadable(Paths.get(imageURL))) continue;
            image = Toolkit.getDefaultToolkit().getImage(imageURL).getScaledInstance(frameWidth, frameHeight, 4);

            Frame frame = new Frame();
            frame.add(new Sorter.CustomPaintComponent());

            frame.setSize(frameWidth + 100, frameHeight + 100);
            frame.setVisible(true);

            String command = Character.toString(scan.nextLine().charAt(0));
            frame.dispose();

            switch (command) {
                case "w":
                    moveFile(folderURL, wFolderURL, i);
                    break;
                case "a":
                    moveFile(folderURL, aFolderURL, i);
                    break;
                case "s":
                    moveFile(folderURL, sFolderURL, i);
                    break;
                case "d":
                    moveFile(folderURL, dFolderURL, i);
                    break;
            }
        }
    }

    private static void moveFile(String pathFrom, String pathTo, int imageNo) {
        try {
            String pre = "" + imageNo;
            Files.move(Paths.get(pathFrom + pre + ".png"), Paths.get(pathTo + imageNo + ".png"));
            System.out.println("Moved " + imageNo + " to " + pathTo);

        } catch (IOException e) {
            System.out.println("Failed to move the image " + imageNo);
        }
    }

    static class CustomPaintComponent extends Component {

        public void paint(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            int x = 0;
            int y = 0;
            g2d.drawImage(image, x, y, this);
        }
    }
}
