package lab6;
/* *
 * [VetVisitList.java]
 * Author: Kristin Hamilton
 * Desc: interface; implemented by VetVisitListImpl objects (lists of VetVisits)
 *       followed outline for TransList.java on 2125 website.
 *       "Should work properly with any kind of VetVisit instance used, assuming the
 *        instance is one that is from a class that extends VetVisit, which both the
 *        urgent and standard should."
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.io.PrintWriter;
import java.util.GregorianCalendar;

public interface VetVisitList
{
    public final static int MAX_VISITS = 50;  /* find out actual # */
    public int size();
    public String getVetVisitListByDate(GregorianCalendar date);
    public boolean contains(VetVisit visit);
    public boolean add(VetVisit visit);
    public VetVisit remove(VetVisit visit);
    public void removeAll();
    public void writeToFile(PrintWriter ofile1);

}//end of VetVisitList.java