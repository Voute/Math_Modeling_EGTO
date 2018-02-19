package quest_1;

import javax.swing.*;

/**
 * Created by egto1016 on 05.02.2018.
 */
public class Calculator
{
    public static Double[] generateNumbers(double M, double m)
    {
        int size = 1000;    // количество чисел
//        double M = 7d;
//        double m = 7d;
        Double[] x = new Double[size];

        x[0] = Math.pow(2, -m);

        for (int i = 1; i < size; i++)
        {
            double newNum = M * x[i - 1];
            x[i] = newNum - (long)newNum;
        }

        return x;
    }

    public static Double[] generateNumbers(int size)
    {
        Double[] resultArray = new Double[size];

        for (int i = 0; i < size; i++)
        {
            int value = (int)(Math.random()*100);
            resultArray[i] = value / 100d;
//            System.out.println(value / 100d);
        }

        return resultArray;
    }

    public static Double[] generateNumbers3(int size)
    {
        Double[] resultArray = new Double[size];

        for (int i = 0; i < size; i++)
        {
            double r1;
            double r2;
            Double x = null;

            while (x == null)
            {
                r1 = Math.random();
                r2 = Math.random();
                x = resolveRandomValueTask3(r1, r2);
                System.out.println(x);
            }

            resultArray[i] = x;
            System.out.println("[" + i + "] " + x);
        }

        return resultArray;
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

    private static double increaseMo(double Mo, double n, double v)
    {
        return Mo + n * v;
    }

    private static double increaseD(double D, double n, double v)
    {
        return D + Math.pow(n, 2) * v;
    }

    private static Double resolveRandomValueTask3(double r1, double r2)
    {
        // variant 1
        // f(x) = 0,25 if (0 < x < 2)
        // f(x) = (2 - 0,5x) if (2 <= x < 4)
        // M1 = 0,25
        // M2 = 0,5

        // X0=a+r1∙(b-a), η=r2∙M.

        double a = 0d;
        double b = 4d;
        double M = 0d;
        double M1 = 0.25d;
        double M2 = 0.5d;
        Double fValue = null;
        double n;

        double x0 = a + r1 * (b - a);

        if (0 < x0 && x0 < 2)
        {
            M = M1;
            fValue = 0.25d;

        } else if (2 <= x0 && x0 < 4)
        {
            M = M2;
            fValue = 2 - 0.5d * x0;
        } else
        {
            return null;
        }

        n = r2 * M;

        if (fValue == null || n > fValue)
        {
            return null;
        } else
        {
            return x0;
        }

    }
}
