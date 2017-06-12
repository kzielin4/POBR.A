package Zielinski.Kamil.Model;

import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import Zielinski.Kamil.View.DisplayImage;

import java.io.File;       
public class test
{

	public static void main(final String[] args)
	{
		final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(0, 1));
        frame.add(new JButton(new AbstractAction("Load") {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(frame, "Test", FileDialog.LOAD);
                fd.setVisible(true);
                System.out.println(fd.getFile());
                if(fd.getFile()==null)
                {
                	return;
                }
                final String imageName = fd.getFile();
        		final BufferedImage image = new ImageReader().getImage(imageName);
        		final Pixel[][] pixelMatrix = PixelMatrix.mapImage(image);
        		final BufferedImage resultImage = new Finder().find(image);
        		final File output = new File("detect/source.png");
        		try
        		{
        			ImageIO.write(resultImage, "png", output);
        			System.out.println("done");
        			DisplayImage dis = new DisplayImage(output);
        		} catch (final IOException e1)
        		{
        			//e1.printStackTrace();
        		}
            }
        }));
       /* frame.add(new JButton(new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                FileDialog fd = new FileDialog(frame, "Test", FileDialog.SAVE);
                fd.setVisible(true);
                System.out.println(fd.getFile());
            }
        }));*/
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

	}

}
