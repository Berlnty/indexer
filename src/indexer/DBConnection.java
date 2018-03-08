
package indexer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class DBConnection {
    private Connection conn;
    private Statement st;
    public DBConnection()
    {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/indexer?useUnicode=yes&characterEncoding=UTF-8","root","");
            st=conn.createStatement();
        }
        catch(ClassNotFoundException | SQLException ex)
        {
        }
    }
    
    public List execute(String query)
    {
        try{
            ResultSet result;
            ArrayList<ArrayList<String>> mysql_output = new ArrayList<>();
            if(query.contains("select"))
            {
                result=st.executeQuery(query);
            }
            else
            {
                st.executeUpdate(query,Statement.RETURN_GENERATED_KEYS);
                result= st.getGeneratedKeys();
            }
            int columns_number=result.getMetaData().getColumnCount();
            while (result.next())
            {
                ArrayList<String> row=new ArrayList<>();
                for(int i=1;i<=columns_number;i++)
                    row.add(result.getString(i));
                mysql_output.add(row);
            }
            return mysql_output;
        }
        catch(SQLException ex)
        {
            System.out.println(ex.getMessage());
            //System.out.println(query);
            return null;
        }
    }
}
