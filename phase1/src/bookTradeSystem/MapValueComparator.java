package bookTradeSystem;
import java.util.*;
import java.util.Comparator;

public class MapValueComparator implements Comparator<Integer> {
    Map<Integer,Integer> order;
    public MapValueComparator(Map<Integer, Integer> order){
        this.order = order;
    }
    public int compare(Integer a, Integer b ){
        if(order.get(a) >= order.get(b)){
            return -1;
        }else{
            return 1;
        }
    }
}
