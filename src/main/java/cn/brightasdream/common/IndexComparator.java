package cn.brightasdream.common;

import java.util.Comparator;

public class IndexComparator implements Comparator<IndexStructure>{

    @Override
    public int compare(IndexStructure o1, IndexStructure o2) {
        if(o1.getPersonID().compareTo(o2.getPersonID()) < 0)
            return -1;
        else if(o1.getPersonID().compareTo(o2.getPersonID()) == 0)
            return o1.getAttrID().compareTo(o2.getAttrID());
        else return 1;
    }
}