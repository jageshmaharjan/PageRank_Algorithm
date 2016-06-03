import java.io.*;
import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Pattern;


/**
 * Created by jagesh on 04/07/2016.
 */
public class Testing1
{
    Pattern delimiter = Pattern.compile("\t");
    String path1 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex1\\linkgraph.temp.4";
    String path2 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex2\\linkgraph.temp.30";
    String path3 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex3\\linkgraph.temp.100";
    String path4 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\linkgraph.txt";

    public static void main(String[] args) throws IOException
    {
        Testing1 ts1 = new Testing1();
        ts1.readFile();
    }

    public void readFile() throws IOException
    {
        Long st = System.currentTimeMillis();
        File file = new File(path1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str = null;
        Map<Integer,List<Integer>> map = new HashMap<>();
        Set<Integer> nodes = new HashSet<>();
        while ((str = br.readLine()) != null)
        {
            String[] split = delimiter.split(str);
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            nodes.add(key);
            nodes.add(value);

            List<Integer> old = map.get(key);
            if (old == null)
            {
                old = new LinkedList<>();
            }
            old.add(value);
            map.put(key, old);
        }
        br.close();
        System.out.println(System.currentTimeMillis() - st);
        pageRankCalculation(map,nodes);
    }


    public void pageRankCalculation(Map<Integer, List<Integer>> map, Set<Integer> nodes)
    {
//        System.out.println(nodes.size());
//        System.out.println(map.size());
        int i=0;
        int N = nodes.size();
        float d = (float)0.15;
        float sink = 0;
        Map<Integer,Float> PR = new HashMap<>();

        for (Integer node : nodes)
        {
            PR.put(node,(1.0f/N));
        }
        while (i< 15)
        {
            Map<Integer,Float> I = new HashMap<>();
            for (Integer node : nodes)
            {
                I.put(node,(d/N));
            }

            for (Map.Entry<Integer,List<Integer> > entry : map.entrySet())
            {
//            System.out.println(entry.getKey() + " : " + entry.getValue().size());
                for (Integer lst : entry.getValue())
                {
                    if (map.get(lst) != null)
                    {
                        float calc = 0;

                        calc += sink;
                        calc += I.get(lst) + (1-d)*(PR.get(entry.getKey())/entry.getValue().size());
                        I.put(lst,calc);
                    }
                    else if (map.get(lst) == null)
                    {
                        I.put(lst,I.get(lst) + (1-d)*(PR.get(entry.getKey())/N));
                    }
//                System.out.println(entry.getValue().size());
                }
            }
            //update PR from I
            for (Integer node : nodes)
            {
                PR.put(node,I.get(node));
            }
            System.out.println(PR);
            i++;
        }
        float Sum = 0;
        for (Map.Entry<Integer, Float> pr : PR.entrySet() )
        {
            Sum += pr.getValue();
        }
        System.out.println(PR);
        System.out.println(Sum);
    }


    public String listKey(String str)
    {
        List<String> lstkey = new ArrayList<String>();
        String[] splitstr = str.split("\t");

        return splitstr[0];
    }
    public String listValue(String str)
    {
        List<String> lstvalue = new ArrayList<String>();
        String[] splitstr = str.split("\t");

        return splitstr[1];
    }
    public void dataStructure(List<String> lstkey, String str)
    {
        Map<String,ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();

        String[] splitstr = str.split("\t");

        for (String s: lstkey)
        {
            ArrayList<String> alist = new ArrayList<String>();
            String n = String.valueOf(map.get(s));
            if (n == null)
            {
                alist.add(splitstr[1]);
            }
            else
            {
                alist.add(splitstr[1]);
            }
            map.put(s,alist);
        }
        System.out.println(map);
    }
    public void dataStructure(List<String> lstkey, List<String> lstValue)
    {
        Map<Integer,ArrayList<String>> map = new HashMap<>();

        for (String key : lstkey)
        {
            String k = String.valueOf(map.get(key));
            System.out.println(k);
        }
    }

}
