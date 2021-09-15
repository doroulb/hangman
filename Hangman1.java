import java.util.Scanner;
import java.util.Random;

public class Hangman1{
    ListOpener open_file = new ListOpener();
    Random r = new Random();
    private String[] stored_words_array;
    private int index;
    private char[] end_result_array;
    private char[] random_word_in_array;
    private String wort;
    
    /* Erzeugt ein String Array stored_words_array mit allen Wörtern aus der 
     * Wörterliste und wählt ein random Wort aus, dessen einzelne Buchstaben 
     * in einem Char Array gespeichert werden
     */
    public void set_array(){
        try{
            stored_words_array = new String[open_file.exportList().length];
            stored_words_array = open_file.exportList();
            index = r.nextInt(stored_words_array.length);
            end_result_array = new char[stored_words_array[index].length() + 1];
            wort = stored_words_array[index];
            random_word_in_array = new char[wort.length()];
            for (int i = 0; i < wort.length(); i++)
            {
                random_word_in_array[i] = wort.charAt(i);
            }
        }catch(Throwable e){
            System.out.println("Привет" + e);
            e.printStackTrace();
        }  
    }
    
    //gibt Länge vom Wort (da random_word_in_array private)
    public int getrandom_word_in_array_Length()
    {
        return random_word_in_array.length;
    }
    
    //gibt Wort als Array (da random_word_in_array private)
    public char[] getrandom_word_in_array()
    {
        return random_word_in_array;
    }
}