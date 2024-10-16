package quest1;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class LerImagem {
    public static void main(String[] args) throws IOException {

        File img = new File("src/quest1.limpas");

        for (File imagens : img.listFiles()) {
            BufferedImage outraImg = ImageIO.read(imagens);
            BufferedImage newImg = new BufferedImage(outraImg.getWidth(), outraImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

            int largura = outraImg.getWidth();
            int altura = outraImg.getHeight();

            Random random = new Random();
            double noiseLevel = 0.05;

            for (int linha = 0; linha < altura; linha++) {
                for (int coluna = 0; coluna < largura; coluna++) {
                    int rd = random.nextInt(2);
                    if (rd < noiseLevel) {
                        boolean blackorwhite = random.nextBoolean();
                        Color cor;
                        if (blackorwhite) {
                            cor = new Color(0, 0, 0);
                        } else {
                            cor = new Color(255, 255, 255);
                        }
                        newImg.setRGB(coluna, linha, cor.getRGB());
                    } else {
                        newImg.setRGB(coluna, linha, outraImg.getRGB(coluna, linha));
                    }
                }
            }
            ImageIO.write(newImg, "png", new File("src/quest1.sujas/ruido_" + imagens.getName()));
        }
    }
}
