package quest_2;

import quest_1.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Quest_2
{
    final int COUNT_CASHIER = 1;
    final int HALL_WORK_START_HOUR = 8;
    final int HALL_WORK_END_HOUR = 12;
    final double AVG_TIME_CLIENTS_ARR_MINUTE = 1d;
    final double AVG_TIME_CLIENTS_SERV_MINUTE = 0.5d;

    ArrayList<Double> clientsArrivals;
    ArrayList<Double> clientsServTimes;

    public void task_1()
    {
        double workTimeMinutes = (HALL_WORK_END_HOUR - HALL_WORK_START_HOUR) * 60;
        clientsArrivals = generateArrivals(workTimeMinutes, AVG_TIME_CLIENTS_ARR_MINUTE);
        clientsServTimes = new ArrayList<>();
        double clientsPerMinute = 1 / AVG_TIME_CLIENTS_SERV_MINUTE;

        for (int i = 0; i < clientsArrivals.size(); i++)
        {
            clientsServTimes.add(generateRandomDouble(clientsPerMinute));
//            System.out.println(clientsServTimes.get(i));
        }

        for (Double d:clientsArrivals
             ) {
//            System.out.println(d);
        }
    }

    void drawChart()
    {
        String title = "Task 1.4";

        ETable freqTable = ETable.getFrequencyInstance(generatedValues_1);
        Interval[] intervals = freqTable.intervals;
        Object[] chartValues = freqTable.getTableData(1);

        Chart chart = new Chart(1000, 1000);

        DrawAction action = new DrawAction() {
            @Override
            public void draw(Graphics gr) {

                // draw initial lines
                chart.drawInitialLines(gr, 10);
                for (int n = 0; n < chartValues.length; n++) {
                    chart.drawBar(gr, (int)chartValues[n], intervals[0].gre, n);
                }
            }
        };


        EFrame frame = EFrame.createCanvasFrame(title, action, chart, 600, 600);

        createOpenTaskButton(title, frame);
    }

    ArrayList<Double> generateArrivals(double minutes, double clientsPerMinute)
    {
        ArrayList<Double> resultArray = new ArrayList<>();
        double totalTime = 0d;
        double newArrival;

        newArrival = generateRandomDouble(AVG_TIME_CLIENTS_ARR_MINUTE);

        while(totalTime < minutes)
        {
            resultArray.add(totalTime);
            newArrival = generateRandomDouble(AVG_TIME_CLIENTS_ARR_MINUTE);
            totalTime += newArrival;
        }

        return resultArray;
    }

    double generateRandomDouble(double La)
    {
        return Math.log(1d - Math.random()) / -La;
    }
}
