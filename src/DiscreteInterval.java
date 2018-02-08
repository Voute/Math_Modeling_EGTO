/**
 * Created by egto1016 on 08.02.2018.
 */
public class DiscreteInterval extends Interval
{
    final double distrValue;

    public DiscreteInterval(double gr, double lte, double distrValue)
    {
        super(gr, lte);
        this.distrValue = distrValue;
    }

    public static DiscreteInterval[] convertFromDistrArray(Object[][] distrArray)
    {
        int size = distrArray[0].length - 1;
        DiscreteInterval[] returnArray = new DiscreteInterval[size];

        double prevLte = 0d;

        for (int i = 1; i < distrArray[0].length; i++) {
            double distrValue = (double)distrArray[0][i];
            double gr = prevLte;
            double lte = (double)distrArray[1][i] + prevLte;
            prevLte = lte;

            DiscreteInterval interval = new DiscreteInterval(gr, lte, distrValue);
            returnArray[i-1] = interval;

            System.out.println("[" + i + "]" + " from " + gr + " to " + lte);
        }

        return returnArray;
    }
}
