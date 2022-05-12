package simplilearn.farhadfaghihi;

import org.junit.Assert;
import org.junit.Test;
import simplilearn.farhadfaghihi.dao.QuickSorter;

import java.util.ArrayList;
import java.util.List;

public class QuickSortTest {

    @Test
    public void givenUnsortedList_whenCallingQuickSort_thenSortedList() {
        // Arrange
        List<String> items = new ArrayList<>();
        items.add("C");
        items.add("D");
        items.add("A");
        items.add("Z");
        items.add("W");
        items.add("O");

        // Act
        QuickSorter<String> quickSorter = new QuickSorter<>();
        List<String> sortedList = quickSorter.sort(items);

        // Assert
        Assert.assertEquals("A", sortedList.get(0));
        Assert.assertEquals("C", sortedList.get(1));
        Assert.assertEquals("D", sortedList.get(2));
        Assert.assertEquals("O", sortedList.get(3));
        Assert.assertEquals("W", sortedList.get(4));
        Assert.assertEquals("Z", sortedList.get(5));
    }

    @Test
    public void givenReverseSortedList_whenCallingQuickSort_thenSortedList() {
        // Arrange
        List<String> items = new ArrayList<>();
        items.add("Z");
        items.add("Y");
        items.add("X");
        items.add("W");
        items.add("V");
        items.add("U");

        // Act
        QuickSorter<String> quickSorter = new QuickSorter<>();
        List<String> sortedList = quickSorter.sort(items);

        // Assert
        Assert.assertEquals("U", sortedList.get(0));
        Assert.assertEquals("V", sortedList.get(1));
        Assert.assertEquals("W", sortedList.get(2));
        Assert.assertEquals("X", sortedList.get(3));
        Assert.assertEquals("Y", sortedList.get(4));
        Assert.assertEquals("Z", sortedList.get(5));
    }

    @Test
    public void givenAlreadySortedList_whenCallingQuickSort_thenSortedList() {
        // Arrange
        List<String> items = new ArrayList<>();
        items.add("A");
        items.add("B");
        items.add("C");
        items.add("D");
        items.add("E");
        items.add("F");

        // Act
        QuickSorter<String> quickSorter = new QuickSorter<>();
        List<String> sortedList = quickSorter.sort(items);

        // Assert
        Assert.assertEquals("A", sortedList.get(0));
        Assert.assertEquals("B", sortedList.get(1));
        Assert.assertEquals("C", sortedList.get(2));
        Assert.assertEquals("D", sortedList.get(3));
        Assert.assertEquals("E", sortedList.get(4));
        Assert.assertEquals("F", sortedList.get(5));
    }

    @Test
    public void givenEmptyList_whenCallingQuickSort_thenEmptyList() {
        // Arrange
        List<String> items = new ArrayList<>();

        // Act
        QuickSorter<String> quickSorter = new QuickSorter<>();
        List<String> sortedList = quickSorter.sort(items);

        // Assert
        Assert.assertEquals(sortedList.size(), 0);
    }

    @Test
    public void givenNullList_whenCallingQuickSort_thenEmptyList() {
        // Arrange + Act
        QuickSorter<String> quickSorter = new QuickSorter<>();
        List<String> sortedList = quickSorter.sort(null);

        // Assert
        Assert.assertEquals(sortedList.size(), 0);
    }
}
