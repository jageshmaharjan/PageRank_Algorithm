import java.util.LinkedList;
import java.util.List;

/**
 * Created by jagesh on 04/13/2016.
 * DataStructure to hold the Graph with in-links list, Page-Rank and page-outlink size
 */
public class MyDataStructure
{
    private float pageRank = 0.0f;
    private int outLinkCount;
    private List<Integer> inLinks      = new LinkedList<>();

    MyDataStructure(int source)
    {
        outLinkCount = 0;
        inLinks.add(source);
    }

    MyDataStructure()
    {
        outLinkCount = 1;
    }

    float getPageRank()
    {
        return pageRank;
    }

    void setPageRank(float pageRank)
    {
        this.pageRank = pageRank;
    }

    int getOutlinkCount()
    {
        return outLinkCount;
    }

    void incrementOutlink()
    {
        this.outLinkCount++;
    }

    List<Integer> getInLinks()
    {
        return inLinks;
    }

    void addInLink(int node)
    {
        inLinks.add(node);
    }

    public String toString()
    {
        return inLinks + ", pageRank = " + pageRank + ", outlinkCount = " + outLinkCount;
    }

}
