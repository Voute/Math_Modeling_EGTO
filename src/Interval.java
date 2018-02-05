/**
 * Created by egto1016 on 05.02.2018.
 */
public class Interval
{
    double gr;
    double lte;
    int count;

    Interval(double gr, double lte)
    {
        this.gr = gr;
        this.lte = lte;
        count = 0;
    }

    boolean check(double n)
    {
        if (n > gr && n <= lte) {
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