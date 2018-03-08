
package indexer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class indexer {
  private Map <String,PriorityQueue<word>> data;
  private Map <Integer,String> links;
  public indexer()
  {
      this.data=new HashMap<>();
      this.links=new HashMap<>();
  }
  
  public void add_word(String w,int page,int position)
  {
      PriorityQueue<word> new_row;
      if(this.data.get(w)!=null)
          new_row=this.data.get(w);
      else
          new_row=new PriorityQueue<>();
      
      new_row.add(new word(page,position));
      this.data.put(w, new_row);
  }

    public Map<String, PriorityQueue<word>> getData() {
        return data;
    }

    public Map<Integer, String> getLinks() {
        return links;
    }
  public void add_link(int id,String url)
  {
     this.links.put(id, url);
  }
  public ArrayList<String> retrieve(String s)
  {
      Queue<word> queue=data.get(s);
      ArrayList<String> urls=new ArrayList();
      if(queue==null) return null;
      for (word item : queue) {
          urls.add(links.get(item.page));
      }
      return urls;
  }
  
  
}
