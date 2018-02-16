package quest_2;

import quest_1.*;
import quest_1.TextArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.LinkedList;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Quest_2
{
    final EFrame rootFrame;
    final ArrayList<JButton> openTaskButtons;

    final int HALL_WORK_START_HOUR = 8;
    final int HALL_WORK_END_HOUR = 12;
    final double AVG_TIME_CLIENTS_ARR_MINUTE = 1d;  // 1d
    final double AVG_TIME_CLIENTS_SERV_MINUTE = 0.5d;   // 0.5d
    int clientsSize;
    int workTimeMinutes;
    int maxServTimeSeconds = 0;
    int minServTimeSeconds = 100;
    int maxQueueSize = 0;
    double servTimeAverage;
    int maxCashierWorkDurationSeconds = 0;
    int minCashierWorkDurationSeconds = 1000;
    double timeClientsInHallAverage;
    int lastEventTime;

    ArrayList<Double> clientsArrivals;
    ArrayList<Double> clientsServTimes;
    ArrayList<Event> events = new ArrayList<Event>();
    Queue<Client> clients;
    Chart2 chart = new Chart2(1200, 800);
    HashMap<Integer,Integer> queuesTotals = new HashMap<Integer,Integer>();

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
        int totalTimeClientsInHall = 0;
        ColorWarehouse colorHouse = new ColorWarehouse();

        for (int i = 0; i < clientsArrivals.size(); i++)
        {
            clientsServTimes.add(generateRandomDouble(clientsPerMinute));
            int arrivalSeconds = calculateSeconds(clientsArrivals.get(i));
            int servInSeconds = calculateSeconds(clientsServTimes.get(i));

            if (maxServTimeSeconds < servInSeconds)
            {
                maxServTimeSeconds = servInSeconds;
            }
            if (minServTimeSeconds > servInSeconds)
            {
                minServTimeSeconds = servInSeconds;
            }

            Client client = new Client(arrivalSeconds, servInSeconds, colorHouse.getColor());
            clients.add(client);
            totalTimeClientsInHall += client.timeInHall;

            events.add(new Event(client.timeArrival, Event.TYPE_START, client));
            events.add(new Event(client.timeLeave, Event.TYPE_END, client));
            System.out.println("event is added: [start] " + client.timeArrival + " [end] " + client.timeLeave);
        }

        events.sort((event, t1) -> {
            if (event.eventTime > t1.eventTime)
            {
                return 1;
            }
            return -1;
        } );
        countDownTime(events);
        clientsSize = clientsArrivals.size();
        timeClientsInHallAverage = totalTimeClientsInHall/clientsSize;

        renderChart(chart);
        drawChart(clientsArrivals, clientsServTimes);

        for (Integer key : queuesTotals.keySet()) {
            int queueSize = key;
            int duration = queuesTotals.get(key);
            System.out.println("[queue total duration]" + queueSize + " : " + duration);
        }
    }

    public void task_1_1()
    {
        String title = "Show Calculations";
        EFrame frame = EFrame.createTextFrame(title, 650, 350);

        TextArea area = (TextArea)frame.coreComponent;

        int downtime = queuesTotals.get(-1);
        System.out.println("downtime: " + downtime  );
        double v1 =  downtime / workTimeMinutes / 60;

        area.appendln("Коэффициент занятости устройства обслуживания - " + v1);
        area.appendln("Среднее число требований в очереди (среднее число клиентов банка, стоящих в очереди) - " + calculateVariable2(queuesTotals, lastEventTime));
        area.appendln("Количество клиентов, посетивших кассовый зал за время его работы - " + clientsSize);
        area.appendln("Максимальное время нахождения клиента в очереди (час:мин:сек) - " + resolveDurationString(maxServTimeSeconds));
        area.appendln("Минимальное время нахождения клиента в очереди (час:мин:сек) - " + resolveDurationString(minServTimeSeconds));
        area.appendln("Максимальная длина очереди (кол-во человек) - " + maxQueueSize);
        area.appendln("Среднее время нахождения клиента в очереди (от общего количества) (час:мин:сек) - " + resolveDurationString((int)servTimeAverage));
        area.appendln("Минимальное время работы устройства обслуживания (кассира) без перерыва (час:мин:сек) - " +
                resolveDurationString(minCashierWorkDurationSeconds));
        area.appendln("Максимальное время работы устройства обслуживания (кассира) без перерыва (час:мин:сек) - " +
                resolveDurationString(maxCashierWorkDurationSeconds));
        area.appendln("Среднее время пребывания клиента в зале (час:мин:сек) - " + resolveDurationString((int)timeClientsInHallAverage));

        createOpenTaskButton(title, frame);
    }

    public void show()
    {
        rootFrame.setVisible(true);
    }

    void drawChart(ArrayList<Double> clientsArrivals, ArrayList<Double> clientsServTimes)
    {
        String title = "График";

        DrawAction chartPaintAction = new DrawAction() {
            @Override
            public void draw(Graphics gr) {

                // draw initial lines
                chart.drawInitialLines(gr, (int)workTimeMinutes);

                for (Client client : clients)
                {
                    for (ChartLine line: client.stayLine)
                    {
                        chart.drawChartLine(gr, line, client.color);
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
                    renderChart(chart);
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
                    renderChart(chart);
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
        double result = 0;
        while (result < 0.1d)
        {
            result = Math.log(1d - Math.random()) / -La;
        }

        return result;
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

    void countDownTime(ArrayList<Event> events)
    {
        boolean isDowntime = false;
        int queueSize = 0;
        int cashierStartWorkTime = 0;
        int previousEventTime = 0;

        for (Event event : events)
        {
            System.out.println("event time " + event.eventTime);
            addToQueueTotal(queueSize-1, event.eventTime - previousEventTime);

            if (event.eventType == Event.TYPE_START)
            {
                if (queueSize == 0)
                {
                    cashierStartWorkTime = event.eventTime;
                }
                queueSize++;
                if (queueSize > maxQueueSize) maxQueueSize = queueSize;
            } else
            {
                queueSize--;
                if (queueSize == 0)
                {
                    int cashierWorkDuration = event.eventTime - cashierStartWorkTime;
                    if (cashierWorkDuration > maxCashierWorkDurationSeconds) maxCashierWorkDurationSeconds = cashierWorkDuration;
                    if (cashierWorkDuration < minCashierWorkDurationSeconds) minCashierWorkDurationSeconds = cashierWorkDuration;
                }
            }

            previousEventTime = event.eventTime;
        }

        lastEventTime = previousEventTime;
    }

    private int calculateSeconds(double minutes)
    {
        int totalSeconds;
        int min = (int)minutes;
        int seconds = (int)( (double)(minutes - min) * 60d );
        totalSeconds = seconds + (min * 60);
        return totalSeconds;
    }

    private String resolveDurationString(int durationSeconds)
    {
        int minuteSeconds = 60;
        int hourSeconds = minuteSeconds * 60;
        int hours = (int)(durationSeconds / hourSeconds);
        int minutes = (int)( durationSeconds % hourSeconds / minuteSeconds );
        int seconds = (int)( durationSeconds % hourSeconds % minuteSeconds );

        String duration = hours + ":" + minutes + ":" + seconds;
        return duration;
    }

    void renderChart(Chart2 chart)
    {
        int queueSize = 0;
        ArrayList<Client> clientsQueue = new ArrayList<Client>();
        int totalClientsTimeInQueue = 0;

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
                chart.populateServStart(eventClient);
                System.out.println("[Add] queue size is " + queueSize);

                if (queueSize == 1)     // client will be served immediately
                {
                    totalClientsTimeInQueue += event.client.setServStartTime(event.eventTime);
                }

            } else if (event.eventType == Event.TYPE_END)
            {
                // draw end eventClient line
                chart.populateServEndLines(eventClient);

                // draw downgrade lines for customers in queue that have grades greater than the the ended one
                for (Client clientInQueue : clientsQueue)
                {
                    if (clientInQueue.getGrade() > eventClient.getGrade())
                    {
                        if (clientInQueue.downGrade() == 1)
                        {
                            totalClientsTimeInQueue += event.client.setServStartTime(event.eventTime);
                        }
                        chart.populateSrvDowngradeLines(clientInQueue, event.eventTime);
                    }
                }

                // remove eventClient from queue
                clientsQueue.remove(eventClient);
                queueSize--;
                System.out.println("[Remove] queue size is " + queueSize);
            }

        }

        servTimeAverage = totalClientsTimeInQueue / clientsSize;
    }

    void addToQueueTotal(int queueSize, int duration)
    {
        if (!queuesTotals.containsKey(queueSize))
        {
            queuesTotals.put(queueSize, duration);
        } else {
            int updatedTotal = queuesTotals.get(queueSize) + duration;
            queuesTotals.replace(queueSize, updatedTotal);
        }
    }

    double calculateVariable2(HashMap<Integer, Integer> queueKeys, int lastEventTime)
    {
        double result = 0d;
        for (Integer key : queueKeys.keySet())
        {
            if (key >= 0) result += key * queueKeys.get(key);
        }
        System.out.println("iT: " + result);
        System.out.println(lastEventTime);
        result /= lastEventTime;
        return result;
    }
}
