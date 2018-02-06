import javax.swing.*;

/**
 * Created by egto1016 on 05.02.2018.
 */
public class Calculator
{
    public static Double[] generateNumbers()
    {
        int size = 1000;    // количество чисел
        double M = 7d;
        double m = 7d;
        Double[] x = new Double[size];

        x[0] = Math.pow(2, -m);

        for (int i = 1; i < size; i++)
        {
            double newNum = M * x[i - 1];
            x[i] = newNum - (long)newNum;
        }

        return x;
    }

    public static double calculateMathExpectation(Double[] array)
    {
        double expec = 0;  // математическое ожидание
        double v = 1d / (double)array.length;

        expec = increaseMo(expec, array[0], v);

        for (int i = 1; i < array.length; i++)
        {
            expec = increaseMo(expec, array[i], v);
        }

        return expec;
    }

    public static double calculateDispersion(Double[] array)
    {
        double disp = 0;   // дисперсия
        double v = 1d / (double)array.length;

        disp = increaseD(disp, array[0], v);

        for (int i = 1; i < array.length; i++)
        {
            disp = increaseD(disp, array[i], v);
        }

        double expec =  calculateMathExpectation(array);
        disp -= Math.pow(expec, 2);

        return disp;
    }

    public static double calculateSelectiveAverage(Double[] array)
    {
        double average = 0; // выборочная средняя
        int size = array.length;

        for (int i = 0; i < size; i++) {
            average += array[i];
        }

        average = average / size;

        return average;
    }


    public static double calculateSelectiveDispersion(Double[] array)
    {
        double Vdisp = 0;   // выборочная дисперсия
        int size = array.length;
        double selectiveAverage = calculateSelectiveAverage(array);

        for (int i = 0; i < size; i++) {
            Vdisp += Math.pow( (array[i] - selectiveAverage), 2 );
        }
        Vdisp = Vdisp / size;

        return Vdisp;
    }

    public void generateFrequencyTable()
    {

    }

    private static double increaseMo(double Mo, double n, double v)
    {
        return Mo + n * v;
    }

    private static double increaseD(double D, double n, double v)
    {
        return D + Math.pow(n, 2) * v;
    }
}
