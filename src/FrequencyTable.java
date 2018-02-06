import javax.swing.*;

/**
 * Created by egto1016 on 05.02.2018.
 */
public class FrequencyTable extends JTable
{
    static final int sizeIntervals = 10;
    Interval[] intervals;
    Object[][] tableValues; // 0 - String, 1 - Integer, 2 - Double

    public FrequencyTable getInstance(Double[] array)
    {
        int size = array.length;
        Interval[] intervals = generateIntervals(array);
        String[] tableColumns = {"Interval", "Count", "Frequency"};
        Object[][] tableValues = fillTableValues(intervals, size);

        FrequencyTable table = new FrequencyTable(tableValues, tableColumns, intervals);

        return table;
    }

    private FrequencyTable(Object[][] tableValues, Object[] columns, Interval[] intervals)
    {
        super(tableValues, columns);
        this.intervals = intervals;
        this.tableValues = tableValues;
    }

    static private Interval[] generateIntervals(Double[] array)
    {
        Interval[] intervals;

        intervals = generateEmptyIntervals();
        int size = array.length;

        // filling intervals with frequency
        for (int i = 0; i < size; i++) {
            for (int n = 0; n < sizeIntervals; n++) {
                intervals[n].check(array[i]);
            }
        }

        return intervals;
    }

    static private Object[][] fillTableValues(Interval[] intervals, int arraySize)
    {
        Object[][] tableValues; // 0 - String, 1 - Integer, 2 - Double

        // filling table values
        tableValues = new Object[sizeIntervals + 1][3];
        int sum = 0;
        double sum2 = 0;
        for (int n = 0; n < sizeIntervals; n++) {
            tableValues[n][0] = new String(intervals[n].gr + " to " + intervals[n].lte);
            tableValues[n][1] = intervals[n].count;
            tableValues[n][2] = intervals[n].getCh(arraySize);
            sum += intervals[n].count;
            sum2 += intervals[n].getCh(arraySize);
        }

        tableValues[10][1] = "total";
        tableValues[10][1] = sum;
        tableValues[10][2] = sum2;

        return tableValues;
    }

    public Object[] getTableData(int column)
    {
        int size = tableValues.length;
        Object[] returnArray = new Object[size];

        for (int i = 0; i < size; i++)
        {
            returnArray[i] = tableValues[i][column];
        }

        return returnArray;
    }

    private static Interval[] generateEmptyIntervals()
    {
        Interval[] returnArray = new Interval[10];

        returnArray[0] = new Interval(0d, 0.1d);
        returnArray[1] = new Interval(0.1d, 0.2d);
        returnArray[2] = new Interval(0.2d, 0.3d);
        returnArray[3] = new Interval(0.3d, 0.4d);
        returnArray[4] = new Interval(0.4d, 0.5d);
        returnArray[5] = new Interval(0.5d, 0.6d);
        returnArray[6] = new Interval(0.6d, 0.7d);
        returnArray[7] = new Interval(0.7d, 0.8d);
        returnArray[8] = new Interval(0.8d, 0.9d);
        returnArray[9] = new Interval(0.9d, 1d);

        return returnArray;
    }
}
