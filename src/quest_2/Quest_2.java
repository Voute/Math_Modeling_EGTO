package quest_2;

import quest_1.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.LinkedList;
import java.util.Queue;

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
    final double AVG_TIME_CLIENTS_ARR_MINUTE = 1d;
    final double AVG_TIME_CLIENTS_SERV_MINUTE = 0.5d;
    double workTimeMinutes;

    ArrayList<Double> clientsArrivals;
    ArrayList<Double> clientsServTimes;
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
            clients.add(new Client(clientsArrivals.get(i), clientsServTimes.get(i), colorHouse.getColor()));
        }

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

                int current_clients_count = 0;
                Queue<Client> currentClients = new LinkedList<>();

                System.out.println("clients are arriving..");

                // draw clients
                for (Client client : clients)
                {
                    currentClients.add(client);
                    current_clients_count++;

                    Queue<Client> currentClients_new = new LinkedList<>();
                    Client cur_client = currentClients.poll();
                    while (cur_client != null)
                    {
                        if (cur_client.timeLeave < client.timeArrival)
                        {
                            for (Client downGradeClient : currentClients)
                            {
                                downGradeClient.downGrade();
                                chart.drawServDowngrade(gr, );
                            }
//                            chart.drawServEnd(gr, client, current_clients_count);
                            current_clients_count--;
                        } else
                        {
                            currentClients_new.add(cur_client);
                        }

                        cur_client = currentClients.poll();
                    }
                    currentClients = currentClients_new;

                    client.setGrade(current_clients_count);
                    chart.drawServStart(gr, client);
                    System.out.println(client.timeArrival);
                }
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
