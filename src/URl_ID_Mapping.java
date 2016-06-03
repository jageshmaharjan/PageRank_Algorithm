import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jagesh on 04/18/2016.
 */
public class URl_ID_Mapping
{
    public static void main(String[] args) throws IOException
    {
        URl_ID_Mapping mapping = new URl_ID_Mapping();
        mapping.readFile();
    }

    public void readFile() throws IOException
    {
        String pagerankFile = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\output\\PR_2015280258.txt";
        String mappingFile = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\URl-ID_mapping\\id-url_206302842.txt";
        File file = new File(mappingFile);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = null;
        Map<Integer,String> mapURLID = new HashMap<>();

       while ((line = br.readLine()) != null)
       {
           String[] strline = line.split("\t");
           mapURLID.put(Integer.valueOf(strline[0]),strline[1]);
       }
        System.out.println(mapURLID);
    }

}
