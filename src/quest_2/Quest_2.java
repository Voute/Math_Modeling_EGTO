package quest_2;

import quest_1.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Quest_2
{
    final EFrame rootFrame;
    final ArrayList<JButton> openTaskButtons;
//    final JTextField fieldM;
//    final JTextField fieldm;

    final int COUNT_CASHIER = 1;
    final int HALL_WORK_START_HOUR = 8;
    final int HALL_WORK_END_HOUR = 12;
    final double AVG_TIME_CLIENTS_ARR_MINUTE = 1d;  // 1d
    final double AVG_TIME_CLIENTS_SERV_MINUTE = 0.5d;   // 0.5d
    double workTimeMinutes;

    ArrayList<Double> clientsArrivals;
    ArrayList<Double> clientsServTimes;
    ArrayList<Event> events = new ArrayList<Event>();
    Queue<Client> clients;

    public Quest_2()
    {
        rootFrame = EFrame.createSimpleFrame("Practice 2", 250, 500);
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openTaskButtons = new ArrayList<>();
    }

    public void doTasks()
    {
        task_1();

        for (JButton button :openTaskButtons
                ) {
            rootFrame.coreComponent.add(button);
        }
    }

    public void task_1()
    {
        clients = new LinkedList<Client>();
        workTimeMinutes = (HALL_WORK_END_HOUR - HALL_WORK_START_HOUR) * 60;
        clientsArrivals = generateArrivals(workTimeMinutes, AVG_TIME_CLIENTS_ARR_MINUTE);
        clientsServTimes = new ArrayList<>();
        double clientsPerMinute = 1 / AVG_TIME_CLIENTS_SERV_MINUTE;
        ColorWarehouse colorHouse = new ColorWarehouse();

        for (int i = 0; i < clientsArrivals.size(); i++)
        {
            clientsServTimes.add(generateRandomDouble(clientsPerMinute));
//            System.out.println(clientsServTimes.get(i));
            Client client = new Client(clientsArrivals.get(i), clientsServTimes.get(i), colorHouse.getColor());
            clients.add(client);
            events.add(new Event(client.timeArrival, Event.TYPE_START, client));
            events.add(new Event(client.timeLeave, Event.TYPE_END, client));
        }
//        Client cl = new Client(1d, 5d, colorHouse.getColor());
//        clients.add(cl);
//            events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//            events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(2d, 8d, colorHouse.getColor());
//        clients.add(cl);
//            events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//            events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(4d, 1d, colorHouse.getColor());
//        clients.add(cl);
//        events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//        events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(9d, 11d, colorHouse.getColor());
//        clients.add(cl);
//        events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//        events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(8d, 5d, colorHouse.getColor());
//        clients.add(cl);
//            events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//            events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(14d, 5d, colorHouse.getColor());
//        clients.add(cl);
//            events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//            events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));
//
//        cl = new Client(16d, 6d, colorHouse.getColor());
//        clients.add(cl);
//            events.add(new Event(cl.timeArrival, Event.TYPE_START, cl));
//            events.add(new Event(cl.timeLeave, Event.TYPE_END, cl));


        events.sort((event, t1) -> (int)(event.eventTime - t1.eventTime));

        drawChart(clientsArrivals, clientsServTimes);

    }

    public void show()
    {
        rootFrame.setVisible(true);
    }

    void drawChart(ArrayList<Double> clientsArrivals, ArrayList<Double> clientsServTimes)
    {
        String title = "График";

//        ETable freqTable = ETable.getFrequencyInstance(generatedValues_1);
//        Interval[] intervals = freqTable.intervals;
//        Object[] chartValues = freqTable.getTableData(1);

        Chart2 chart = new Chart2(1200, 800);
        DrawAction chartPaintAction = new DrawAction() {
            @Override
            public void draw(Graphics gr) {

                // draw initial lines
                chart.drawInitialLines(gr, (int)workTimeMinutes);

                int queueSize = 0;
                ArrayList<Client> clientsQueue = new ArrayList<Client>();

                for (Event event : events)
                {
                    Client eventClient = event.client;

                    if (event.eventType == Event.TYPE_START)
                    {
                        // add eventClient to queue
                        queueSize++;
                        clientsQueue.add(eventClient);
                        eventClient.setGrade(queueSize);

                        // draw initial eventClient line
                        chart.drawServStart(gr, eventClient);
                        System.out.println("[Add] queue size is " + queueSize);
                    } else if (event.eventType == Event.TYPE_END)
                    {
                        // draw end eventClient line
                        chart.drawServEnd(gr, eventClient);

                        // draw downgrade lines for customers in queue that have grades greater than the the ended one
                        for (Client clientInQueue : clientsQueue)
                        {
                            if (clientInQueue.getGrade() > eventClient.getGrade())
                            {
                                clientInQueue.downGrade();
                                chart.drawServDowngraded(gr, clientInQueue, event.eventTime);
                            }
                        }

                        // remove eventClient from queue
                        clientsQueue.remove(eventClient);
                        queueSize--;
                        System.out.println("[Remove] queue size is " + queueSize);
                    }
                }

            }
        };

        EFrame frame = EFrame.createCanvasFrame("Графическое состояние очереди клиентов и занятости кассира",
                chartPaintAction, chart, 1600, 1000);

        JButton zoomInButton = new JButton();
        zoomInButton.setText("Zoom In");
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JButton paintButton = null;
                Chart2 chart = null;

                for (Component comp: frame.getContentPane().getComponents())
                {
                    if (comp instanceof JButton && ((JButton) comp).getText().matches("paint"))
                    {
                        paintButton = (JButton) comp;

                    } else if (comp instanceof Canvas)
                    {
                        chart = (Chart2)comp;
                    }
                }

                if (paintButton != null && chart != null)
                {
                    chart.zoomIn();
                    paintButton.doClick();
                }
            }
        });

        JButton zoomOutButton = new JButton();
        zoomOutButton.setText("Zoom Out");
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                JButton paintButton = null;
                Chart2 chart = null;

                for (Component comp: frame.getContentPane().getComponents())
                {
                    if (comp instanceof JButton && ((JButton) comp).getText().matches("paint"))
                    {
                        paintButton = (JButton) comp;
                    } else if (comp instanceof Canvas)
                    {
                        chart = (Chart2)comp;
                    }
                }

                if (paintButton != null && chart != null)
                {
                    chart.zoomOut();
                    paintButton.doClick();
                }
            }
        });

        frame.add(zoomInButton, BoxLayout.LINE_AXIS);
        frame.add(zoomOutButton, BoxLayout.LINE_AXIS);

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

    private void createOpenTaskButton(String title, EFrame frame) {
        JButton button = new JButton(title);
        openTaskButtons.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(true);
            }
        });
    }
}
