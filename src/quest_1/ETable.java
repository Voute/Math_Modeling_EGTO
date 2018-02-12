package quest_1;

import javax.swing.*;

/**
 * Created by egto1016 on 05.02.2018.
 */
public class ETable extends JTable
{
    static final int sizeIntervals = 10;
    Interval[] intervals;
    Object[][] tableValues; // 0 - String, 1 - Integer, 2 - Double

    private ETable(Object[][] tableValues, Object[] columns, Interval[] intervals)
    {
        super(tableValues, columns);
        this.intervals = intervals;
        this.tableValues = tableValues;
    }

    public static ETable getFrequencyInstance(Double[] array)
    {
        int size = array.length;
        Interval[] intervals = generateIntervals(array);
        String[] tableColumns = {"quest_1.Interval", "Count", "Frequency"};
        Object[][] tableValues = fillTableValues(intervals, size);

        ETable table = new ETable(tableValues, tableColumns, intervals);

        return table;
    }

    public static ETable getDistributionInstance()
    {
        String[] tableColumns = {"key", "1", "2", "3", "4", "5", "6", "7"};
        Object[][] tableValues = generateDistrArray();

        Object[][] distrArrayTable = new Object[tableValues.length][tableValues[0].length + 1];
        distrArrayTable[0][0] = "xi";
        distrArrayTable[1][0] = "pi";
        for (int i = 0; i < distrArrayTable.length; i++ )
        {
            for (int n = 1; n < distrArrayTable[0].length; n++ ) {
                distrArrayTable[i][n] = tableValues[i][n-1];
            }
        }

        ETable table = new ETable(distrArrayTable, tableColumns, null);

        return table;
    }

    public static ETable getDistributionValuesInstance(Object[] values1, Double[] values2)
    {
        String[] tableColumns = {"genValue", "distrValue"};
        int size = values1.length;
        Object[][] tableValues = new Object[size][2];

        for (int i = 0; i < size; i++)
        {
            tableValues[i][0] = values1[i];
            tableValues[i][1] = values2[i];
        }

        ETable table = new ETable(tableValues, tableColumns, null);

        return table;
    }

    private static Object[][] generateDistrArray()
    {
        Object[][] array = new Object[2][7];

        array[0][0] = 5d;
        array[1][0] = 0.01d;
        array[0][1] = 7d;
        array[1][1] = 0.05d;
        array[0][2] = 17d;
        array[1][2] = 0.3d;
        array[0][3] = 19d;
        array[1][3] = 0.3d;
        array[0][4] = 21d;
        array[1][4] = 0.3d;
        array[0][5] = 25d;
        array[1][5] = 0.02d;
        array[0][6] = 55d;
        array[1][6] = 0.02d;

        return array;
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
            tableValues[n][0] = new String(intervals[n].gre + " to " + intervals[n].lt);
            tableValues[n][1] = intervals[n].count;
            tableValues[n][2] = intervals[n].getCh(arraySize);
            sum += intervals[n].count;
            sum2 += intervals[n].getCh(arraySize);
        }

        tableValues[10][0] = "total";
        tableValues[10][1] = sum;
        tableValues[10][2] = sum2;

        return tableValues;
    }

    public Object[] getTableData(int column)
    {
        int size  = tableValues.length - 1; //- 1 due to total row
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
