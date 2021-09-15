import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

/**
 * Class ZEICHENFENSTER - Eine Klasse, die einfache grafische Zeichnungen 
 * in einem Programmfenster ermöglicht.
 * 
 * @author Michael Kolling (mik)
 * @author Bruce Quig
 * @author Christian Heidrich
 *
 * @version 2007.05.07
 */

public class ZEICHENFENSTER
{
    private JFrame frame;
    private CanvasPane canvas;
    private JPanel steuerungOst,steuerungSued;
    private Graphics2D graphic;
    private Color backgroundColor;
    private Image canvasImage;

    private static ZEICHENFENSTER singleton;

    /**
     * Erzeugt eine Zeichenfenster mit Standardmaßen 600*500 und Hintergrundfarbe weiß 
     * @param titel  Titel des Fensters     
     */
    public ZEICHENFENSTER(String titel)
    {
        this(titel, 1300, 750, Color.GRAY);        
    }

    /**
     * Erzeugt ein Zeichenfenster mit weißem Hintergrund.
     * @param titel  Fensterueberschirft
     * @param breite  Breite des Fensters
     * @param hoehe  Hoehe des Fensters
     */
    public ZEICHENFENSTER(String titel, int breite, int hoehe)
    {
        this(titel, breite, hoehe, Color.white);
    }

    /**
     * Erzeugt ein Zeichenfenster.
     * @param titel  Fensterueberschirft
     * @param breite  Breite des Fensters
     * @param hoehe  Hoehe des Fensters
     * @param hintergrundFarbe  Hintergrundfarbe des Zeichenfensters
     */
    private ZEICHENFENSTER(String titel, int breite, int hoehe, Color hintergrundFarbe)
    {
        frame = new JFrame();
        canvas = new CanvasPane();
        canvas.setPreferredSize(new Dimension(breite, hoehe));
        frame.getContentPane().add(canvas,BorderLayout.CENTER);
        JPanel p1=new JPanel();
        p1.setLayout(new BorderLayout());
        steuerungOst = new JPanel();
        steuerungSued = new JPanel();
        steuerungOst.setLayout(new BoxLayout(steuerungOst,BoxLayout.Y_AXIS));
        steuerungSued.setLayout(new BoxLayout(steuerungSued,BoxLayout.X_AXIS));
        p1.add(steuerungOst,BorderLayout.NORTH);
        frame.getContentPane().add(p1,BorderLayout.EAST);
        frame.getContentPane().add(steuerungSued,BorderLayout.SOUTH);
        frame.setTitle(titel);
        backgroundColor = hintergrundFarbe;
        frame.pack();
        zeige();
    }

    public static ZEICHENFENSTER gibFenster()
    {
        if (singleton==null){singleton=new ZEICHENFENSTER("Das Zeichenfenster");}
        singleton.zeige();
        return singleton;
    }

    /*
     * Macht das Zeichenfenster sichtbar bzw. setzt es in den Vordergrund,
     * falls es bereits sichtbar ist.
     */
    public void zeige()
    {
        if(graphic == null) {
            // nur beim ersten Aufruf wird der Hintergrund mit der Hintergrundfarbe 
            // gefuellt
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D)canvasImage.getGraphics();
            graphic.setColor(backgroundColor);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(true);
    }
 
    /**
     * Zeichnet einen Kreis (Siehe Graphics.drawOval)
     * @param x x-Koordinate des Mittelpunkts
     * @param y y-Koordinate des Mittelpunkts
     * @param radius Kreisradius
     */
    public void zeichneKreis(int x, int y, int radius)
    {
        graphic.drawOval(x-radius,y-radius,2*radius,2*radius);
        canvas.repaint();
    }

    /**
     * Löscht den Inhalt des Zeichenfensters.
     */
    public void loescheAlles()
    {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColor);
        Dimension size = canvas.getSize();
        graphic.fill(new Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
        canvas.repaint();
    }

    /**
     * Zeichnet ein Bild in das Zeichnenfenster .
     * @param  bild    das anzuzeigende Bild 
     * @param  x       x-Koordinate des linken Bildrands 
     * @param  y       y-Koordinate des oberen Bildrands 
     * @return  gibt eines booleschen Wert zurück, der angibt, ob das Bild vollständig geladen 
     *          werden konnte 
     */
    public boolean zeichneBild(Image bild, int x, int y)
    {
        boolean result = graphic.drawImage(bild, x, y, null);
        canvas.repaint();
        return result;
    }

    /**
     * Zeichnet einen Text.
     * @param  text    die anzuzeigende Zeichenkette 
     * @param  x       x-Koordinate des linken Rands 
     * @param  y       y-Koordinate des oberen Rands 
     */
    public void zeichneText(String text, int x, int y)
    {
        graphic.setStroke(new BasicStroke(30));
        graphic.setFont(new Font("Tahoma", Font.PLAIN, 50));
        graphic.setColor(Color.white);
        graphic.drawString(text, x, y);
        canvas.repaint();
    }


    /**
     * Zeichnet eine Strecke ins Zeichenfenster.
     * @param  x1   x-Koordinate des Anfangspunkts der Strecke 
     * @param  y1   y-Koordinate des Anfangspunkts der Strecke
     * @param  x2   x-Koordinate des Endpunkts der Strecke 
     * @param  y2   y-Koordinate des Endpunkts der Strecke  
     */
    public void zeichneStrecke(int x1, int y1, int x2, int y2)
    {
        graphic.setStroke(new BasicStroke(5));
        graphic.setColor(Color.white);
        graphic.drawLine(x1, y1, x2, y2);   
        canvas.repaint();
    }

    /**
     * Zeichnet eine Strecke ins Zeichenfenster.
     * @param  x1   x-Koordinate des Anfangspunkts der Strecke 
     * @param  y1   y-Koordinate des Anfangspunkts der Strecke
     * @param  x2   x-Koordinate des Endpunkts der Strecke 
     * @param  y2   y-Koordinate des Endpunkts der Strecke  
     */
    public void zeichneStrecke2(int x1, int y1, int x2, int y2)
    {
        graphic.setStroke(new BasicStroke(2));
        graphic.setColor(Color.white);
        graphic.drawLine(x1, y1, x2, y2);   
        canvas.repaint();
    }
    

    /**
     * Fügt ein weiteres Steuerungselement in die rechte Steuerungsleiste ein.
     * @param  element  Das einzufügende Steuerungselement muss aus JComponent abgeleitet
     * sein. z. B. JButton, JComboBox. 
     */
    public void komponenteHinzufuegen(JComponent element, String position)
    {
        if (position=="rechts") steuerungOst.add(element);
        else if (position=="unten") steuerungSued.add(element);
        frame.pack();
    }


    /************************************************************************
     * Nested class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel
    {
        private static final long serialVersionUID = 20060330L;

        public void paint(Graphics g)
        {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }
}
