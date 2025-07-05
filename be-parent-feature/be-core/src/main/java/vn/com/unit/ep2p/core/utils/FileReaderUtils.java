/**
 * @author TaiTM
 */
package vn.com.unit.ep2p.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.imageio.ImageIO;

/**
 * @author TaiTM
 *
 */
public class FileReaderUtils {
    public static boolean isImgFile(String filePath) {
        boolean check = true;
        try {
            File file = new File(filePath);

            if (file.exists()) {
                if (ImageIO.read(file) == null) {
                    return false;
                }
            } else {
                check = false;
            }

        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }

        return check;
    }
    
    public static StringBuffer readFile(String filePath) {
        StringBuffer buffer = new StringBuffer();
        try {
            File file = new File(filePath);

            if (file.exists()) {
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    buffer.append(data);
                }
                myReader.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        return buffer;
    }
}
