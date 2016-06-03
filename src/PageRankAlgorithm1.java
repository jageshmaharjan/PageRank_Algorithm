import java.io.*;
import java.util.*;

/**
 * Created by jagesh on 04/13/2016.
 * Implementation PageRank Algorithm
 */
public class PageRankAlgorithm1
{
     public static void main(String[] args)
    {
        Long sttime = System.currentTimeMillis();

        String path1 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex1\\linkgraph.temp.4";
        String path2 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex2\\linkgraph.temp.30";
        String path3 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\example\\ex3\\linkgraph.temp.100";
        String path4 = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\input\\linkgraph.txt";

        Map<Integer, MyDataStructure> adjacencyList = new HashMap<>();
        try (BufferedReader in = new BufferedReader(new FileReader(path4)))
        {
            StreamTokenizer tokenizer = new StreamTokenizer(in);

            while (tokenizer.nextToken() != StreamTokenizer.TT_EOF)
            {
                int source = (int) tokenizer.nval;
                tokenizer.nextToken();
                int destination = (int) tokenizer.nval;

                if (!adjacencyList.containsKey(destination))
                {
                    adjacencyList.put(destination,new MyDataStructure(source));
                }
                else
                {
                    adjacencyList.get(destination).addInLink(source);
                }
                if (!adjacencyList.containsKey(source))
                {
                    adjacencyList.put(source,new MyDataStructure());
                }
                else
                {
                    adjacencyList.get(source).incrementOutlink();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> sinkNodes = new LinkedList<>();

        for (Map.Entry<Integer, MyDataStructure> entry : adjacencyList.entrySet())
        {
            if (entry.getValue().getOutlinkCount() == 0)
            {
                sinkNodes.add(entry.getKey());
            }
        }
//        System.out.println(adjacencyList.toString());

        long end = System.currentTimeMillis();
        System.out.println("loading tim: " + (end - sttime));
        calculatePageRank(adjacencyList,sinkNodes);

    }

    public static void calculatePageRank(Map<Integer, MyDataStructure> adjacencyList, List<Integer> sinkNodes)
    {
        int N = adjacencyList.size();
        int iterations = 15;
        float d = 0.85f;
        for (Map.Entry<Integer,MyDataStructure> entry : adjacencyList.entrySet())
        {
            entry.getValue().setPageRank(1.0f/N);
        }
//        System.out.println(adjacencyList.toString());

        for (int iter =0 ; iter < iterations ; iter++)
        {
            long starttime = System.currentTimeMillis();    //start time of loop
            float sinkPR = 0;
            Map<Integer,Float> newPageRank = new HashMap<>();
            for (Integer sinknode : sinkNodes)
            {
                sinkPR += adjacencyList.get(sinknode).getPageRank();
            }

            for (Map.Entry<Integer,MyDataStructure> entry : adjacencyList.entrySet())
            {
                float npagegRank = (1-d)/N;
                npagegRank += d * sinkPR/N;
                for (Integer inlink : entry.getValue().getInLinks())
                {
                    MyDataStructure myDataStructure = adjacencyList.get(inlink);

                    npagegRank += d * myDataStructure.getPageRank()/myDataStructure.getOutlinkCount();
                }
                newPageRank.put(entry.getKey(),npagegRank);
            }
            for (Map.Entry<Integer,MyDataStructure> entry : adjacencyList.entrySet())
            {
                entry.getValue().setPageRank(newPageRank.get(entry.getKey()));
            }

            System.out.println("Loop " + (iter+1) + " time: " + (System.currentTimeMillis() - starttime));
        }
        float sum =0;
        for (Map.Entry<Integer,MyDataStructure> entry : adjacencyList.entrySet())
        {
            if (entry.getKey() == 11 || entry.getKey() == 3)
            System.out.println(entry.getKey() + "\t" + entry.getValue().getOutlinkCount() );
            sum += entry.getValue().getPageRank();
        }
        writeToFile(adjacencyList);
    }
    public static void writeToFile(Map<Integer,MyDataStructure> adjacencyList)
    {
        String outputpath = "C:\\Users\\jagesh\\IdeaProjects\\Assignment5\\output\\PR_2015280258.txt";
        File file = new File(outputpath);
        if (file.exists())
            file.delete();

        try (FileWriter fw = new FileWriter(outputpath,true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);)
        {
            for (Map.Entry<Integer,MyDataStructure> entry : adjacencyList.entrySet())
            {
                out.println(entry.getKey() +"\t"+ entry.getValue().getPageRank());
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
