
package indexer;

import java.util.Comparator;

public class word implements Comparable<word>  {
    public int page,postition;
    
    public word(int page,int postition)
    {
     this.page=page;
     this.postition=postition;
    }

    @Override
    public int compareTo(word word2) {
        if(word2.page>=this.page)
            return 1;
        else 
            return -1;
    }
    
 
}
