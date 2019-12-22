package diary.test;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.io.*;
import javax.imageio.ImageIO;

class FontAntialiasingInSwingPLAFs {

    private JPanel gui = new JPanel(new BorderLayout(2, 2));
    String[] types = {"Graphics2D", "JLabel", "SmoothLabel"};
    private JLabel[] labels = {
        new JLabel(""), new JLabel(""), new SmoothLabel("")
    };
    private JLabel[] scaleLabels = {
        new JLabel(""), new JLabel(""), new JLabel("")
    };
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED};
    private JComboBox<String> plaf = null;
    private UIManager.LookAndFeelInfo[] plafs;

    FontAntialiasingInSwingPLAFs() {
        initComponents();
    }

    private final void initComponents() {
        // here we design the layout and controls..
        JToolBar tb = new JToolBar("Controls");
        tb.setFloatable(false);
        gui.add(tb, BorderLayout.PAGE_START);

        Action save = new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                saveImages();
            }
        };
        tb.add(save);
        tb.addSeparator();

        plafs = UIManager.getInstalledLookAndFeels();
        plaf = new JComboBox<String>() {

            @Override
            public Dimension getMaximumSize() {
                return super.getPreferredSize();
            }
        };
        for (UIManager.LookAndFeelInfo info : plafs) {
            plaf.addItem(info.getName());
        }
        tb.add(plaf);
        ActionListener plafListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                changePLAF();
            }
        };
        plaf.addActionListener(plafListener);


        JPanel normalSize = new JPanel(new GridLayout(0, 3, 2, 2));
        gui.add(normalSize);
        for (int ii = 0; ii < labels.length; ii++) {
            labels[ii].setBorder(new LineBorder(colors[ii]));
            normalSize.add(labels[ii]);
        }

        JPanel scaledSize = new JPanel(new GridLayout(0, 3, 2, 2));
        gui.add(scaledSize, BorderLayout.PAGE_END);
        for (int ii = 0; ii < scaleLabels.length; ii++) {
            scaledSize.add(scaleLabels[ii]);
        }

        setText();
    }

    private void setText() {
        String plafName = (String) plaf.getSelectedItem();
        for (int ii = 1; ii < labels.length; ii++) {
            labels[ii].setText(plafName + " " + types[ii]);
        }
        labels[0].setIcon(new ImageIcon(getImage(plafName + " " + types[0])));
    }

    private void setScaledImages() {
        Dimension d = labels[0].getSize();
        for (int ii = 0; ii < labels.length; ii++) {
            BufferedImage scaledImage = getScaledImage(ii, 4, 200);
            scaleLabels[ii].setIcon(new ImageIcon(scaledImage));
        }
    }

    private BufferedImage getScaledImage(
            int index, int scale, int max) {
        BufferedImage input = getImage(index);
        int w = input.getWidth() * scale;
        int h = input.getHeight() * scale;
        w = w > max ? max : w;
        h = h > max ? max : h;
        BufferedImage temp = new BufferedImage(
                w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = temp.createGraphics();

        // do the drawing..
        g.scale(scale, scale);
        g.drawImage(input, 0, 0, gui);

        g.dispose();
        return temp;
    }

    private BufferedImage getImage(int index) {
        JLabel input = labels[index];
        int w = input.getWidth();
        int h = input.getHeight();
        BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = temp.createGraphics();

        // do the drawing..
        input.paintAll(g);

        g.dispose();
        return temp;
    }

    private BufferedImage getImage(String text) {
        int w = 5;
        int h = 5;
        BufferedImage temp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = temp.createGraphics();

        FontRenderContext frc = g.getFontRenderContext();
        Font f = labels[1].getFont();
        GlyphVector gv = f.createGlyphVector(frc, text);
        Shape s1 = gv.getOutline();
        Shape s = gv.getOutline(-s1.getBounds().x, -s1.getBounds().y);
        Rectangle textBounds = s.getBounds();
        // recreate the image at the required size
        temp = new BufferedImage(
                textBounds.width,
                textBounds.height,
                BufferedImage.TYPE_INT_ARGB);
        // get the graphics object of the new image
        g = temp.createGraphics();
        g.setFont(f);
        // set a RenderingHints.KEY_TEXT_ANTIALIASING
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.BLACK);

        // do the drawing..
        g.drawString(text, 0, textBounds.height);

        g.dispose();
        return temp;
    }

    private final void changePLAF() {
        int selected = plaf.getSelectedIndex();
        try {
            UIManager.setLookAndFeel(plafs[selected].getClassName());
            Container c = gui.getTopLevelAncestor();
            SwingUtilities.updateComponentTreeUI(c);
            setText();
            setScaledImages();
            if (c instanceof Window) {
                Window w = (Window) c;
                w.pack();
            } else {
                System.err.println(c.getClass().getSimpleName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private final String getImageName(int index, boolean scaled) {
        String name = "";
        String sText = scaled ?  " Scaled" : "";
        if (index == 0) {
            name = (String)plaf.getSelectedItem() + " " + types[0];
        } else {
            String text = labels[index].getText();
            name = text;
        }
        String sPattern = "\\/";
        name = name.replaceAll(sPattern, "-");
        return name + sText + ".png";
    }

    private final void saveImages() {
        File f = new File(System.getProperty("user.home"));
        f = new File(f, "zundry");
        f = new File(f, "font-antialias");
        boolean success = true;
        if (!f.exists()) {
            success = f.mkdirs();
        }
        if (success) {
            // save the images!
            for (int ii = 0; ii < labels.length; ii++) {
                try {
                    File tempSmall = new File(f, getImageName(ii, false));
                    ImageIO.write(getImage(ii), "png", tempSmall);
                    File tempScaled = new File(f, getImageName(ii, true));
                    ImageIO.write(getScaledImage(ii, 4, 200), "png", tempScaled);
                    Desktop.getDesktop().open(f);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.err.println(
                    "The directory could not be created!  "
                    + f.getAbsolutePath());
        }
    }

    public JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                FontAntialiasingInSwingPLAFs fawp =
                        new FontAntialiasingInSwingPLAFs();

                JOptionPane.showMessageDialog(null, fawp.getGui());
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
    }
}

class SmoothLabel extends JLabel {

    public SmoothLabel(String text) {
        super(text);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        super.paintComponent(g2d);
    }
}
