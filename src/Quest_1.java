import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by egto1016 on 06.02.2018.
 */
public class Quest_1
{
    final EFrame rootFrame;
    final ArrayList<JButton> openTaskButtons;
    final JTextField fieldM;
    final JTextField fieldm;

    Double[] generatedValues_1;

    public Quest_1()
    {
        rootFrame = EFrame.createSimpleFrame("Practice 1. Tasks", 250, 400);

        JLabel labelHeading = new JLabel("Generator parameters:");
        JLabel labelM = new JLabel("M:");
        JLabel labelm = new JLabel("m:");

        fieldM = new JTextField();
        fieldM.setText("7");
        fieldM.setEditable(false);
        fieldm = new JTextField();
        fieldm.setText("10");
        fieldm.setEditable(false);

        rootFrame.coreComponent.add(labelHeading);
        rootFrame.coreComponent.add(labelM);
        rootFrame.coreComponent.add(fieldM);
        rootFrame.coreComponent.add(labelm);
        rootFrame.coreComponent.add(fieldm);
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openTaskButtons = new ArrayList<>();
    }

    public void doTasks()
    {
        task_1_0();
        task_1_1();
        task_1_2();
        task_1_3();
        task_1_4();

        task_2_0();

        for (JButton button :openTaskButtons
             ) {
            rootFrame.coreComponent.add(button);
        }
    }

    public void show()
    {
        rootFrame.setVisible(true);
    }

    private void task_1_0()
    {
        String title = "1. Show generated values";

        double M = Double.parseDouble(fieldM.getText());
        double m = Double.parseDouble(fieldm.getText());
        generatedValues_1 = Calculator.generateNumbers(M, m);
        EFrame frame = EFrame.createListFrame(title, generatedValues_1, 100, 500);

        createOpenTaskButton(title, frame);
    }

    private void task_1_1()
    {
        String title = "Task 1.1";
        EFrame frame = EFrame.createTextFrame(title, 250, 100);

        TextArea area = (TextArea)frame.coreComponent;

        double mathExpectation = Calculator.calculateMathExpectation(generatedValues_1);
        double selectiveAverage = Calculator.calculateSelectiveAverage(generatedValues_1);

        area.appendln("M{X} = " + mathExpectation);
        area.append("V srednyaya = " + selectiveAverage);

        createOpenTaskButton(title, frame);
    }

    private void task_1_2()
    {
        String title = "Task 1.2";
        EFrame frame = EFrame.createTextFrame(title, 250, 100);

        TextArea area = (TextArea)frame.coreComponent;

        double dispersion = Calculator.calculateDispersion(generatedValues_1);
        double selectiveDispersion = Calculator.calculateSelectiveDispersion(generatedValues_1);

        area.appendln("D{X} = " + dispersion);
        area.append("V dispersiya = " + selectiveDispersion);

        createOpenTaskButton(title, frame);
    }

    private void task_1_3()
    {
        String title = "Task 1.3";

        ETable freqTable = ETable.getInstance(generatedValues_1);
        EFrame frame = EFrame.createTableFrame(title, freqTable, 300, 300);

        createOpenTaskButton(title, frame);
    }

    private void task_1_4()
    {
        String title = "Task 1.4";

        ETable freqTable = ETable.getInstance(generatedValues_1);
        Interval[] intervals = freqTable.intervals;
        Object[] chartValues = freqTable.getTableData(1);

        Chart chart = new Chart(500, 500);

        DrawAction action = new DrawAction() {
            @Override
            public void draw(Graphics gr) {
//                gr.setColor(Color.WHITE);
//                gr.fillRect(50, 50, 100, 100);
//                gr.drawRect(50,50,50,50);

                // draw initial lines
                chart.drawInitialLines(gr, 10);
                for (int n = 0; n < chartValues.length; n++) {
                    chart.drawBar(gr, (int)chartValues[n], intervals[0].gr);
                }
//                chart.drawBar();
            }
        };


        EFrame frame = EFrame.createCanvasFrame(title, action, chart, 600, 600);

        createOpenTaskButton(title, frame);
    }

    private void task_2_0()
    {
        Object[][] distrArray = Calculator.generateDistrArray();

        Object[][] distrArrayFrame = new Object[distrArray.length][distrArray[0].length + 1];
        distrArrayFrame[0][0] = "xi";
        distrArrayFrame[1][0] = "pi";
        for (int i = 0; i < distrArrayFrame.length; i++ )
        {
            for (int n = 1; n < distrArrayFrame[0].length; n++ ) {
                distrArrayFrame[i][n] =
                        distrArray[i][n-1];
            }
        }

        String title = "2. Таблица распределений";
        String[] columns = {"key", "1", "2", "3", "4", "5", "6", "7"};

        JTable distrTableFrame = new JTable(distrArrayFrame, columns);
        EFrame frame = EFrame.createTableFrame(title, distrTableFrame, 500, 100);
        createOpenTaskButton(title, frame);
    }

    private void createOpenTaskButton(String title, ActionListener action)
    {
        JButton button = new JButton(title);
        button.addActionListener(action);
        openTaskButtons.add(button);
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
