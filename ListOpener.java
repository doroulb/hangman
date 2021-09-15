import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;

// Öffnet die selbstgeschriebene Wörterliste 
public class ListOpener{
    public String[] exportList() throws Exception {
        FileReader fileReader = new FileReader("Wörterliste.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> lines = new ArrayList<String>();
        
        String line = null;
        
        while ((line = bufferedReader.readLine()) != null) {
            lines.add(line);
        }
        
        bufferedReader.close();      
        return lines.toArray(new String[lines.size()]);
    }
}