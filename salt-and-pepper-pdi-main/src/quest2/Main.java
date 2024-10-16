package quest2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        File img = new File("src/quest2/Imagens-Ruido");

        for (File imagens : img.listFiles()) {
            BufferedImage outraImg = ImageIO.read(imagens);
            BufferedImage novaImg = new BufferedImage(outraImg.getWidth(), outraImg.getHeight(), BufferedImage.TYPE_INT_ARGB);

            int largura = outraImg.getWidth();
            int altura = outraImg.getHeight();

            FiltroGalsiano gaussiano = new FiltroGalsiano();

            int tamanhoKernel = gaussiano.getFiltroGaussiano9x9().length;
            int desvioVizinhanca = tamanhoKernel / 2;

            for (int linha = desvioVizinhanca; linha < altura - desvioVizinhanca; linha++) {
                for (int coluna = desvioVizinhanca; coluna < largura - desvioVizinhanca; coluna++) {
                    double valorR = 0;
                    double valorG = 0;
                    double valorB = 0;


                    for (int filtroL = 0; filtroL < tamanhoKernel; filtroL++) {
                        for (int filtroC = 0; filtroC < tamanhoKernel; filtroC++) {
                            int imgX = coluna + filtroC - desvioVizinhanca;
                            int imgY = linha + filtroL - desvioVizinhanca;

                            Color pixel = new Color(outraImg.getRGB(imgX, imgY));
                            double peso = gaussiano.getFiltroGaussiano9x9()[filtroL][filtroC];

                            valorR += peso * pixel.getRed();
                            valorG += peso * pixel.getGreen();
                            valorB += peso * pixel.getBlue();
                        }
                    }

                    if (valorR > 255) {
                        valorR = 255;
                    } else if (valorR < 0) {
                        valorR = 0;
                    }
                    if (valorG > 255) {
                        valorG = 255;
                    } else if (valorG < 0) {
                        valorG = 0;
                    }
                    if (valorB > 255) {
                        valorB = 255;
                    } else if (valorB < 0) {
                        valorB = 0;
                    }


                    Color newColor = new Color((int) valorR, (int) valorG, (int) valorB);
                    novaImg.setRGB(coluna, linha, newColor.getRGB());
                }
            }


            String ArquivoSaida = "src/quest2/limpas/l9x9/" + imagens.getName().replace(".jpg", "clean9x9.png");
            ImageIO.write(novaImg, "png", new File(ArquivoSaida));

        }
    }
}