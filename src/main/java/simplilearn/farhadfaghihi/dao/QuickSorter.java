package simplilearn.farhadfaghihi.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic class for sorting a list of items using QuickSort algorithm
 *
 * @param
 */
public class QuickSorter<T extends Comparable> {

    public List<T> sort(List<T> items) {
        if (items == null) {
            return new ArrayList<>();
        }

        if (items.size() <= 1) {
            return items; // Already sorted
        }

        List<T> left = new ArrayList<>();
        List<T> right = new ArrayList<>();
        T pivot = items.get(items.size() - 1); // Use last item as pivot

        for (int i = 0; i < items.size() - 1; i++) {
            if (items.get(i).compareTo(pivot) < 0)
                left.add(items.get(i));
            else
                right.add(items.get(i));
        }

        left = sort(left);
        right = sort(right);

        List<T> sorted = new ArrayList<>(left);
        sorted.add(pivot);
        sorted.addAll(right);

        return sorted;
    }

}
