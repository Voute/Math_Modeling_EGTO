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

//        JLabel labelHeading = new JLabel("Generator parameters:");
//        JLabel labelM = new JLabel("M:");
//        JLabel labelm = new JLabel("m:");

//        fieldM = new JTextField();
//        fieldM.setText("7");
//        fieldM.setEditable(false);
//        fieldm = new JTextField();
//        fieldm.setText("10");
//        fieldm.setEditable(false);

//        rootFrame.coreComponent.add(labelHeading);
//        rootFrame.coreComponent.add(labelM);
//        rootFrame.coreComponent.add(fieldM);
//        rootFrame.coreComponent.add(labelm);
//        rootFrame.coreComponent.add(fieldm);
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

        for (Double d:clientsArrivals
             ) {
//            System.out.println(d);
        }

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

        Chart2 chart = new Chart2(1800, 900);

        DrawAction action = new DrawAction() {
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











//                int current_clients_count = 0;
//                Queue<Client> clientsInQueue = new LinkedList<>();
//
//                System.out.println("clients are arriving..");
//
//                // draw clients
//                for (Client addedClient : clients)
//                {
//                    Queue<Client> clientsInQueue_new = new LinkedList<>();
////                    clientsInQueue_new.add(addedClient);
//                    current_clients_count++;
//
//                    Client queueClient = clientsInQueue.poll();
//                    while (queueClient != null)
//                    {
//                        if (queueClient.timeLeave <= addedClient.timeArrival)
//                        {
//                            for (Client downGradeClient : clientsInQueue)
//                            {
//                                if (downGradeClient.timeLeave > queueClient.timeLeave)
//                                {
//                                    downGradeClient.downGrade();
//                                    chart.drawServDowngraded(gr, downGradeClient, queueClient.timeLeave);
//                                }
//                            }
//                            chart.drawServEnd(gr, queueClient);
//                            current_clients_count--;
//                        } else
//                        {
//                            clientsInQueue_new.add(queueClient);
//                        }
//
//                        queueClient = clientsInQueue.poll();
//                    }
//                    clientsInQueue_new.add(addedClient);
//                    clientsInQueue = clientsInQueue_new;
//
//                    addedClient.setGrade(current_clients_count);
//                    System.out.println("client grade initial is " + addedClient.getGrade());
//                    chart.drawServStart(gr, addedClient);
//                    System.out.println(addedClient.timeArrival);
//                }
            }
        };


        EFrame frame = EFrame.createCanvasFrame("Графическое состояние очереди клиентов и занятости кассира",
                action, chart, 1600, 1000);

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
