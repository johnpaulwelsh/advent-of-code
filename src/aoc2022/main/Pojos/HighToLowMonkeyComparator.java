package src.aoc2022.main.Pojos;

import java.util.Comparator;

public class HighToLowMonkeyComparator implements Comparator<MonkeyHoldingItems> {
    @Override
    public int compare(MonkeyHoldingItems m1, MonkeyHoldingItems m2) {
        return m2.getItemInspections().compareTo(m1.getItemInspections());
    }
}
