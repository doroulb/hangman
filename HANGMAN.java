import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HANGMAN 
{
    private JButton spielen;
    private JTextField eingabeFeld;
    private JLabel eingabe;
    private String buchstabe;
    private int AnzahlFehler;
    private Hangman1 hangman1;
    private String[] word;
    private int zaehler;
    private int AnzahlRichtig;

    public HANGMAN() {
        spielen = new JButton("    Neues Spiel    ");
        eingabe = new JLabel("      Eingabe:    ");
        eingabeFeld = new JTextField("");
        eingabeFeld.setFont(new Font("Tahoma", Font.PLAIN, 28));
        AnzahlFehler = 1;
        hangman1 = new Hangman1();
        hangman1.set_array();
        word = new String[hangman1.getrandom_word_in_array_Length()];
        AnzahlRichtig = 0;


        //Das passiert, wenn auf den Spielebutton gedrückt wird:
        spielen.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {

                    hangman1.set_array();
                    word = new String[hangman1.getrandom_word_in_array_Length()];

                    spiele();
                }
            } );
            
        /* Das passiert, wenn man einen Buchstaben in das Eingabefeld schreibt:
         * Vergleich zwischen eingegebenen Buchstaben und jedem Buchstaben im 
         * Lösungswort -> Wenn der Buchstabe im Wort enthalten ist, wird er auf
         * dem Zeichenfenster hingeschrieben, wenn nicht, wird ein Teil des 
         * Hangman gezeichnet (maximale Fehleranzahl = 7)
         * Nach 7 Fehlern hat man verloren und es erscheint ein "You lost"-
         * Schriftzug
         * Wenn das Wort richtig erkannt wurde, erscheint ein "You won"-
         * Schriftzug
         */
        eingabeFeld.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent evt) {
                    buchstabe = eingabeFeld.getText();
                    zaehler = 0;
                    for (int i = 0; i < hangman1.getrandom_word_in_array_Length(); i++)
                    {
                        if (Character.toString(hangman1.getrandom_word_in_array()[i]).toLowerCase().matches(buchstabe.toLowerCase()))
                        {
                            word[i] = buchstabe;
                            int positionx = 550+(40*i);
                            ZEICHENFENSTER.gibFenster().zeichneText(Character.toString(hangman1.getrandom_word_in_array()[i]), positionx, 480);
                        }
                        else
                        {
                            zaehler++;
                        }
                        if (zaehler == hangman1.getrandom_word_in_array_Length())
                        {
                            zeichnen7(AnzahlFehler);
                            AnzahlFehler++;
                        }
                        else 
                        {
                            //es passiert nichts, weil ein richtiger Buchstabe gefunden wurde
                        }
                        if(AnzahlFehler == 8)
                        {
                            ZEICHENFENSTER.gibFenster().zeichneBild(ladeLostBild(), 500, 500);
                            for (int a = 0; a < hangman1.getrandom_word_in_array_Length(); a++)
                            {
                                word[a] = Character.toString(hangman1.getrandom_word_in_array()[a]);
                                int positionx = 550+(40*a);
                                ZEICHENFENSTER.gibFenster().zeichneText(word[a], positionx, 480);
                            }
                            break;
                        }
                        for(int b = 0; b < hangman1.getrandom_word_in_array_Length(); b++)
                        {
                            
                            if(word[b] != "_")
                            {
                                AnzahlRichtig++;
                            }
                        
                        
                        }
                        if (AnzahlRichtig == hangman1.getrandom_word_in_array_Length())
                        {
                            ZEICHENFENSTER.gibFenster().zeichneBild(ladeWonBild(), 500, 500);
                            break;
                        }
                        else 
                        {
                            AnzahlRichtig = 0;
                        }
                    }
                    eingabeFeld.selectAll();
                }
            });

        ZEICHENFENSTER.gibFenster().komponenteHinzufuegen(spielen,"unten");
        ZEICHENFENSTER.gibFenster().komponenteHinzufuegen(eingabe, "unten");
        ZEICHENFENSTER.gibFenster().komponenteHinzufuegen(eingabeFeld,"unten"); 

        ZEICHENFENSTER.gibFenster().zeichneBild(ladeSchriftzug(), 500, 0);
    }
    
    /* Für jeden Buchstaben des Lösungswort wird ein Strich ("_") gezeichnet
     * Zähler werden auf Ausgangsposition zurückgesetzt
     */
    public void spiele()
    {
        ZEICHENFENSTER.gibFenster().loescheAlles();
        ZEICHENFENSTER.gibFenster().zeichneBild(ladeSchriftzug(), 500, 0);

        for (int i = 0; i < hangman1.getrandom_word_in_array_Length(); i++)
        {
            word[i] = "_";
            int positionx = 550+(40*i);
            ZEICHENFENSTER.gibFenster().zeichneText(word[i], positionx, 480);
        }
        zaehler = 0;
        AnzahlFehler = 1;
        AnzahlRichtig = 0;
    }
    
    
    //Schriftzüge (als Bilder) werden geladen
    public Image ladeSchriftzug() {
        String imagePath = "Schriftzug2.PNG";
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return myPicture;
    }
    public Image ladeLostBild() {
        String imagePath = "YouLost2.PNG";
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return myPicture;
    }
    public Image ladeWonBild() {
        String imagePath = "YouWon.PNG";
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            //e.printStackTrace();
        }

        return myPicture;
    }
    
    // Hangman wird in 7 Schritten (abhängig von den Fehlern) gezeichnet
    public void zeichnen7(int AnzahlFehler)
    {
        int i = AnzahlFehler;

        switch(i)
        {
            case 1:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(110,530,110,460);//Podest Teil 1
            ZEICHENFENSTER.gibFenster().zeichneStrecke(250,530,250,460);//Podest Teil 2
            ZEICHENFENSTER.gibFenster().zeichneStrecke(110,460,250,460);//Podest Teil 3
            break;
            case 2:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(180,460,180,230);//vertikale, lange Stange
            ZEICHENFENSTER.gibFenster().zeichneStrecke(180,230,375,230);//oberste Stange
            break;
            case 3:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(181,300,250,231);//schräge Stange
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,230,375,270);//vertikale, kurze Stange
            break;
            case 4:
            ZEICHENFENSTER.gibFenster().zeichneKreis(375,290,20);//Kopf
            break;
            case 5:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,310,375,380);//Körper
            break;
            case 6:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,320,350,360);//Arm 1
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,320,400,360);//Arm 2
            break;
            case 7:
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,380,350,410);//Bein 1
            ZEICHENFENSTER.gibFenster().zeichneStrecke(375,380,400,410);//Bein 2
            ZEICHENFENSTER.gibFenster().zeichneStrecke2(368,282,373,292);//totes Auge 1 Teil 1
            ZEICHENFENSTER.gibFenster().zeichneStrecke2(368,292,373,282);//totes Auge 1 Teil 2
            ZEICHENFENSTER.gibFenster().zeichneStrecke2(378,282,383,292);//totes Auge 2 Teil 1
            ZEICHENFENSTER.gibFenster().zeichneStrecke2(378,292,383,282);//totes Auge 2 Teil 2
            break;
        }
    }

}
