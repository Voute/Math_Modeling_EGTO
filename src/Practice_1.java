import java.util.HashMap;
import java.util.Map;

public class Practice_1
{
//    Используя метод вычетов, сгенерировать последовательность из 1 000 псевдослучайных чисел, результат вывести на экран.
//    xi+1 ={M ∙ xi}, x0 = 2−m,
//    где M − достаточно большое целое число, фигурные скобки обозначают дробную часть, а m − число двоичных разрядов в мантиссе чисел в ЭВМ.
//    Методы выбора значений M, x0 и m разнятся для разных вариантов реализаций данного метода (это своя собственная "наука") и определяют основные свойства датчика случайных чисел (соответствие статистическим критериям, длину периода повторения последовательности и т.п.).

    public static void task_1()
    {
        int size = 1000;
        double M = 7d;
        double m = 7d;
        double v = 1d / (double)size;
        double Mo = 0;
        double D = 0;
        Map<Double, Integer> frequency = new HashMap<Double, Integer>();

        double[] x = new double[size];
        x[0] = Math.pow(2, -m);
        Mo = increaseMo(Mo, x[0], v);
        D = increaseD(D, x[0], v);
        countFreq(frequency, x[0]);
        printNumber(String.valueOf(x[0]), 1);

        for (int i = 1; i < size; i++)
        {
            double newNum = M * x[i - 1];
            x[i] = newNum - (long)newNum;
            Mo = increaseMo(Mo, x[i], v);
            D = increaseD(D, x[i], v);
            countFreq(frequency, x[i]);
            printNumber(String.valueOf(x[i]), i + 1);
        }

        D -= Math.pow(Mo, 2);
        System.out.println("M{X} = " + Mo);
        System.out.println("D{X} = " + D);

        for (int i=0; i < frequency.size(); i++)
        {
            System.out.print("key " + frequency.keySet().toArray()[i] + " ");
            System.out.println("value " + frequency.values().toArray()[i]);
        }
    }

    static double increaseMo(double Mo, double n, double v)
    {
        return Mo + n * v;
    }

    static double increaseD(double D, double n, double v)
    {
        return D + Math.pow(n, 2) * v;
    }

    static void countFreq(Map<Double, Integer> f, double n)
    {
        if (f.containsKey(n))
        {
            f.put(n, f.get(n).intValue() + 1);
        } else {
            f.put(n, 1);
        }
    }

    public static void printNumber(String n, int id)
    {
        System.out.println("number #" + id + ": " + n);
    }
}
