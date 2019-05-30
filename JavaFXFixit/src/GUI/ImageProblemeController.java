/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;

/**
 * FXML Controller class
 *
 * @author hphqlim
 */
public class ImageProblemeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ImageView imagezoom;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData(String imageprobleme) {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] imageByte;
        try {
            imageByte = decoder.decodeBuffer(imageprobleme);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            BufferedImage bufferedImage = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imagezoom.setImage(image);
            
        } catch (IOException ex) {
            Logger.getLogger(ImageProblemeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
