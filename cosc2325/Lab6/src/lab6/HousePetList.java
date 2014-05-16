package lab6;
/* *
 * [HousePetList.java]
 * Author: Kristin Hamilton
 * Desc: interface implemented by HousePetListImpl.java
 * Date created: 04-Mar-2014 for Lab6
 * Date last modified: 17-Mar-2014
 */
import java.util.Scanner;

public interface HousePetList
{
    public static final int MAX_SIZE = 30;
    public void readFromScanner(Scanner inputSource);
    public void sortByChipIdentifier();
    public void sortByName();
    public boolean add(HousePet housepet);
    public HousePet remove(HousePet housepet);
    public void removeAll();
    public boolean contains(HousePet housepet);
    public int getSize();
    public String getByName(String housepetName);
    public boolean modifyAge(int hpChipID, double newAge);
    public boolean writeToFile(String filename);
    public boolean isSaved();  /* has not been modified */
    public boolean addVetVisit(int chipID, VetVisit visit);
    public boolean removeVetVisit(int chipID, VetVisit visit);
    
}//end of HousePetList.java