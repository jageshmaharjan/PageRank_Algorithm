/**
 * Created by jagesh on 04/17/2016.
 */
public class CheckMemoryHeap
{
    public static void main(String[] args)
    {
        long heap = Runtime.getRuntime().totalMemory();
        System.out.println("Heap Size : " + heap);
    }
}
