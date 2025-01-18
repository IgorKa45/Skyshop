package org.skypro.skyshop.model.search;
import java.util.Comparator;

public class SearchableComparator implements Comparator<Searchable> {
    @Override
    public  int compare(Searchable s1,Searchable s2){
        int lengthCompare = Integer.compare(s2.getName().length(), s1.getName().length());
        if (lengthCompare != 0) {
            return lengthCompare;
        }
        return s1.getName().compareTo(s2.getName());
    }
}
