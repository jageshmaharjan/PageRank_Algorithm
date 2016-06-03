import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by jagesh on 04/12/2016.
 */
public class Testing2
{
    Pattern delimiter = Pattern.compile("\t");
    String path1 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex1\\linkgraph.temp.4";
    String path2 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex2\\linkgraph.temp.30";
    String path3 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex3\\linkgraph.temp.100";
    String path4 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\linkgraph.txt";

    public static void main(String[] args)
    {
        Testing2 ts2 = new Testing2();
        try
        {
            ts2.readFile();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void readFile() throws IOException
    {
        File file = new File(path1);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String str;
        Map<Integer,List<Integer> > map = new HashMap<>();
        Set<Integer> nodes = new HashSet<>();
        Set<Integer> inlinks = new HashSet<>();
        Set<Integer> outlinks = new HashSet<>();
        while ((str = br.readLine()) != null)
        {
            String[] split = delimiter.split(str);
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            inlinks.add(key);
            outlinks.add(value);
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

        Set<Integer> sinkNodes = new HashSet<>();
        boolean found = false;
        for (Integer outlink : outlinks)
        {
            for (Map.Entry<Integer, List<Integer> > entry : map.entrySet() )    //for (Integer inlink : inlinks)
            {
                if (outlink == entry.getKey())
                {
                    found = true;
                    //System.out.println(outlink);
                }
            }
            if (found == false)
                sinkNodes.add(outlink);
            found = false;
        }

        pageRankCalculation(map,inlinks,outlinks,nodes,sinkNodes);
    }

    public void pageRankCalculation(Map<Integer, List<Integer>> map, Set<Integer> inlinks, Set<Integer> outlinks, Set<Integer> nodes, Set<Integer> sinkNodes)
    {
        System.out.println(map);
        int iterate = 1;
        int N = nodes.size();
        float d = 0.85f;
        Map<Integer,Float> newPR = new HashMap<>();
        Map<Integer, Float> PR = new HashMap<>();
        for (Integer node : nodes)
        {
            PR.put(node, (float) (1.0/N));
        }
        while (iterate > 0 )
        {
            float sinkPR =0;
            for (Integer sinkNode : sinkNodes)
            {
                sinkPR += PR.get(sinkNode);
            }

            float var = 0;
            for (Integer sinknode : sinkNodes)
            {
                newPR.put(sinknode,(1-d)/N);
                newPR.put(sinknode,newPR.get(sinknode)+ d*(sinkPR/N));
//                var += (1-d)/N;
//                var += d*(sinkPR/N);
//                newPR.put(sinknode,var);
            }

            for (Map.Entry<Integer,List<Integer> > entry : map.entrySet())
            {
                for (Integer lst : entry.getValue())
                {
                    float temp=0;
                    if ( newPR.get(lst) != null)
                    {
                        temp += newPR.get(lst);
                        newPR.put(entry.getKey(), temp + d*PR.get(lst)/entry.getValue().size());
                    }
                    else
                    {
                        newPR.put(entry.getKey(), temp + d*PR.get(lst)/entry.getValue().size());
                    }
                }
            }
            for (Integer node : nodes)
            {
                PR.put(node,newPR.get(node));
            }
            float sum =0;
            for (Integer node :nodes)
            {
                sum += PR.get(node);
            }

            System.out.println(PR);
            System.out.println("sum: "+sum);
            iterate--;
        }
    }

}
