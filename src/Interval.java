/**
 * Created by egto1016 on 05.02.2018.
 */
public class Interval
{
    final double gre;
    final double lt;
    int count;

    Interval(double gre, double lt)
    {
        this.gre = gre;
        this.lt = lt;
        count = 0;
    }

    boolean check(double n)
    {
        if (n >= gre && n < lt) {
            count++;
            return true;
        }
        return false;

    }

    double getCh(int size)
    {
        return count/(double)size;
    }
}