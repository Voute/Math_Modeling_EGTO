package quest_2;

import quest_1.*;
import quest_1.TextArea;

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
    double downtime = 0;
    int clientsSize;
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
        task_1_1();

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
            int arrivalSeconds = calculateSeconds(clientsArrivals.get(i));
            int servInSeconds = calculateSeconds(clientsServTimes.get(i));
            Client client = new Client(arrivalSeconds, servInSeconds, colorHouse.getColor());
            clients.add(client);
            events.add(new Event(client.timeArrival, Event.TYPE_START, client));
            events.add(new Event(client.timeLeave, Event.TYPE_END, client));
            System.out.println("event is added: [start] " + client.timeArrival + " [end] " + client.timeLeave);
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

        events.sort((event, t1) -> {
            if (event.eventTime > t1.eventTime)
            {
                return 1;
            }
            return -1;
        } );
        countDownTime(events);
        clientsSize = clientsArrivals.size();

        drawChart(clientsArrivals, clientsServTimes);

    }

    public void task_1_1()
    {
        String title = "Show Calculations";
        EFrame frame = EFrame.createTextFrame(title, 450, 100);

        TextArea area = (TextArea)frame.coreComponent;

        double v1 =  downtime / workTimeMinutes;

        area.appendln("Коэффициент занятости устройства обслуживания - " + v1);
        area.appendln("Количество клиентов, посетивших кассовый зал за время его работы - " + clientsSize);

        createOpenTaskButton(title, frame);
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
//                boolean isDowntime = true;
//                double lastClientLeave = 0d;
                ArrayList<Client> clientsQueue = new ArrayList<Client>();

                for (Event event : events)
                {
                    Client eventClient = event.client;

                    if (event.eventType == Event.TYPE_START)
                    {
//                        if (isDowntime)
//                        {
////                            addToDownTime(lastClientLeave, event.eventTime);
//                            isDowntime = false;
//                        }
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
//                        lastClientLeave = event.eventTime;

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
                        if (queueSize == 0)
                        {
//                            isDowntime = true;
                            System.out.println("downtime is set to true");
                        }
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

    void addToDownTime(double t1, double t2)
    {
        double addition = (t2 - t1);
        downtime += addition;
        System.out.println("+ downtime " + addition);
    }

    void countDownTime(ArrayList<Event> events)
    {
        boolean isDowntime = false;
        int queueSize = 0;
        double downtimeStart = 0d;

        for (Event event : events)
        {
            System.out.println("event time " + event.eventTime);

            if (event.eventType == Event.TYPE_START)
            {
                if (queueSize == 0)
                {
                    addToDownTime(downtimeStart, event.eventTime);
                }
                queueSize++;
            } else
            {
                queueSize--;
                if (queueSize == 0)
                {
                    downtimeStart = event.eventTime;
                }
            }
        }
    }

    private int calculateSeconds(double minutes)
    {
        int totalSeconds;
        int min = (int)minutes;
        int seconds = (int)( (double)(minutes - min) * 60d );
        totalSeconds = seconds + (min * 60);
        return totalSeconds;
    }
}
