package indexer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
public class main {
    static void set_Data(DBConnection conn)
    {
        Document doc;
        try{
         doc= (Document) Jsoup.connect("https://www.google.com.eg/").get();
         String phrases=Jsoup.parse(doc.html()).text().toLowerCase();
         String[] splitArray = phrases.split(" ");
         for(int i=0;i<splitArray.length;i++)
         {
             String word_id;
             ArrayList<ArrayList<String>> mysql_output = (ArrayList<ArrayList<String>>) conn.execute("select id from words where word='"+splitArray[i]+"';");
            // System.out.println(mysql_output);
            if(mysql_output.isEmpty())
               mysql_output= (ArrayList<ArrayList<String>>) conn.execute("insert into words (word) values('"+splitArray[i]+"');");
            
            conn.execute("insert into word_page (page_id,word_id,position) values ('1','"+mysql_output.get(0).get(0)+"','"+i+"');");
             
         }
        }
        catch(IOException err)
        {
            System.out.println(err.getMessage());
        }
        
    }
    static void get_Data(DBConnection conn,indexer ind)
    {
        ArrayList<ArrayList<String>> mysql_output=(ArrayList<ArrayList<String>>) conn.execute("select words.word,word_page.page_id,word_page.position,pages.link from word_page inner join words on words.id=word_page.word_id inner join pages on pages.id=word_page.page_id;");
        for(int i=0;i<mysql_output.size();i++)
        {
            ind.add_word(mysql_output.get(i).get(0),Integer.parseInt(mysql_output.get(i).get(1)),Integer.parseInt(mysql_output.get(i).get(2)));
            ind.add_link(Integer.parseInt(mysql_output.get(i).get(1)),mysql_output.get(i).get(3));
        }
    }
    public static void main(String[] args) {
        
        DBConnection conn= new DBConnection();
        set_Data(conn);
        indexer ind=new indexer();
        get_Data(conn,ind);
        
    }
    
}
