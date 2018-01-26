import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Practice_1
{
//    Используя метод вычетов, сгенерировать последовательность из 1 000 псевдослучайных чисел, результат вывести на экран.
//    xi+1 ={M ∙ xi}, x0 = 2−m,
//    где M − достаточно большое целое число, фигурные скобки обозначают дробную часть, а m − число двоичных разрядов в мантиссе чисел в ЭВМ.
//    Методы выбора значений M, x0 и m разнятся для разных вариантов реализаций данного метода (это своя собственная "наука") и определяют основные свойства датчика случайных чисел (соответствие статистическим критериям, длину периода повторения последовательности и т.п.).

    JFrame rootFrame;
    JPanel panel;
    JTextArea textArea;

    int size = 1000;    // количество чисел
    double M = 7d;
    double m = 7d;
    double v = 1d / (double)size;
    double Mo = 0;  // математическое ожидание
    double D = 0;   // дисперсия
    double Vsr = 0; // выборочная средняя
    double Vdisp = 0;   // выборочная дисперсия
    Double[] x = new Double[size];
    int sizeIntervals = 10;
    String[][] tableValues_13 = new String[11][3];
    double[] chartValues = new double[sizeIntervals];
    Interval[] intervals;
    Map<Double, Integer> frequency = new HashMap<Double, Integer>();
    ArrayList<JFrame> frames = new ArrayList<JFrame>();

    Practice_1()
    {
        intervals = new Interval[sizeIntervals];

        intervals[0] = new Interval(0d, 0.1d);
        intervals[1] = new Interval(0.1d, 0.2d);
        intervals[2] = new Interval(0.2d, 0.3d);
        intervals[3] = new Interval(0.3d, 0.4d);
        intervals[4] = new Interval(0.4d, 0.5d);
        intervals[5] = new Interval(0.5d, 0.6d);
        intervals[6] = new Interval(0.6d, 0.7d);
        intervals[7] = new Interval(0.7d, 0.8d);
        intervals[8] = new Interval(0.8d, 0.9d);
        intervals[9] = new Interval(0.9d, 1d);

        rootFrame = new JFrame();
        rootFrame.setSize(800, 600);

        textArea = new JTextArea();
        textArea.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        panel = new JPanel(new FlowLayout());
//        panel.add(textArea);

        rootFrame.setContentPane(panel);
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        rootFrame.setVisible(true);
    }

    public void task_1()
    {
        x[0] = Math.pow(2, -m);
        Mo = increaseMo(Mo, x[0], v);
        D = increaseD(D, x[0], v);
        printNumber(String.valueOf(x[0]), 1);

        for (int i = 1; i < size; i++)
        {
            double newNum = M * x[i - 1];
            x[i] = newNum - (long)newNum;
            Mo = increaseMo(Mo, x[i], v);
            D = increaseD(D, x[i], v);
            printNumber(String.valueOf(x[i]), i + 1);
        }

        D -= Math.pow(Mo, 2);

        JList list = createListFrame("Show generated values", x);
    }

    double increaseMo(double Mo, double n, double v)
    {
        return Mo + n * v;
    }

    double increaseD(double D, double n, double v)
    {
        return D + Math.pow(n, 2) * v;
    }

    void countFreq(Map<Double, Integer> f, double n)
    {
        if (f.containsKey(n))
        {
            f.replace(n, f.get(n).intValue() + 1);
        } else {
            f.put(n, 1);
        }
    }

    public void printNumber(String n, int id)
    {
        System.out.println("number #" + id + ": " + n);
    }

    public void task_1_1()
    {
        for (int i = 0; i < size; i++) {
            Vsr += x[i];
        }

        Vsr = Vsr / size;

        TextArea area = createTextFrame("Task 1.1");

        area.appendln("M{X} = " + Mo);
        area.append("V srednyaya = " + Vsr);

    }

    public JFrame createFrame(String title)
    {
        JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();

        frame.setContentPane(panel);

        return frame;
    }

    public TextArea createTextFrame(String title)
    {
        JFrame frame = createFrame(title);

        TextArea area = new TextArea();
        frame.getContentPane().add(area);

        addTask(title, frame);

        return area;
    }

    public JTable createTableFrame(String title, Object[][] data, Object[] columns)
    {
        JFrame frame = createFrame(title);

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        frame.getContentPane().add(scrollPane);

        addTask(title, frame);

        return table;
    }

    public JList createListFrame(String title, Object[] data)
    {
        JFrame frame = createFrame(title);

        JList list = new JList(data);
        list.setVisible(true);
        Dimension size = new Dimension(100, 600);
        list.setSize(size);
        list.setPreferredSize(size);
        JScrollPane pane = new JScrollPane(list);
        pane.setSize(size);
        pane.setPreferredSize(size);
        frame.getContentPane().add(pane);

        addTask(title, frame);

        return list;
    }

    public JFrame createCanvasFrame(String title, DrawAction action, Chart chart)
    {
        JFrame frame = createFrame(title);

//        final Canvas canvas = new Canvas();
//        canvas.setSize(700, 700);
//        canvas.setBackground(Color.BLACK);
//        canvas.createBufferStrategy(1);

        frame.add(chart, BorderLayout.CENTER);

        addTask(title, frame);

        frame.setVisible(true);

        JButton but = new JButton();
        but.setText("paint");
        but.setVisible(true);

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BufferStrategy bs = chart.getBufferStrategy();
                if (bs == null)
                {
                    chart.createBufferStrategy(1);
                }
                bs = chart.getBufferStrategy();
                Graphics gr = bs.getDrawGraphics();
                action.draw(gr);
                gr.dispose();
                bs.show();
                frame.repaint();
            }
        });

        frame.add(but);
        frame.repaint();
        frame.setVisible(false);

        return frame;
    }

    public void addTask(String buttonName, JFrame frame)
    {
        JButton but = new JButton();
        but.setText(buttonName);
        but.setVisible(true);
        panel.add(but);
        panel.updateUI();

        but.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(true);
            }
        });
    }

    public void task_1_2()
    {
        for (int i = 0; i < size; i++) {
            Vdisp += Math.pow( (x[i] - Vsr), 2 );
        }
        Vdisp = Vdisp / size;

        TextArea area = createTextFrame("Task 1.2");
        area.appendln("D{X} = " + D);
        area.append("V dispersiya = " + Vdisp);

    }

    public void task_1_3()
    {

        for (int i = 0; i < size; i++) {
            for (int n = 0; n < sizeIntervals; n++) {
                intervals[n].check(x[i]);
            }
        }

        int sum = 0;
        double sum2 = 0;
         for (int n = 0; n < sizeIntervals; n++) {
             tableValues_13[n][0] = intervals[n].gr + " to " + intervals[n].lte;
             tableValues_13[n][1] = Integer.toString(intervals[n].count);
             tableValues_13[n][2] = Double.toString(intervals[n].getCh(size));
             sum += intervals[n].count;
             sum2 += intervals[n].getCh(size);
         }

        tableValues_13[10][1] = "total";
        tableValues_13[10][1] = Integer.toString(sum);
        tableValues_13[10][2] = Double.toString(sum2);

        String[] tableColumns = {"Interval", "Count", "Frequency"};

        JTable table = createTableFrame("Task 1.3", tableValues_13, tableColumns);

    }

    public void task_1_4()
    {
        for (int i = 0; i < sizeIntervals; i++)
        {
            chartValues[i] = Double.parseDouble(tableValues_13[i][2]) / 0.1d;
            System.out.println(" chart value " + i + " = " + chartValues[i]);
        }

        Chart chart = new Chart(500, 500);

        DrawAction action = new DrawAction() {
            @Override
            public void draw(Graphics gr) {
//                gr.setColor(Color.WHITE);
//                gr.fillRect(50, 50, 100, 100);
//                gr.drawRect(50,50,50,50);

                // draw initial lines
                chart.drawInitialLines(gr, sizeIntervals);
                chart.drawBar(gr, chartValues[0],intervals[0].gr);
//                chart.drawBar();
            }
        };
        JFrame frame = createCanvasFrame("Task 1.4", action, chart);

//        Canvas canvas = (Canvas)frame.getContentPane().getComponent(0);
//        BufferStrategy bs = canvas.getBufferStrategy();
//        Graphics gr = bs.getDrawGraphics();
//        gr.setColor(Color.BLACK);
//        gr.fillRect(50, 50, 100, 100);
//        gr.drawRect(50,50,50,50);
//        bs.show();

//        frame.paint(gr);
//        Interval[] intervals
    }

    private class Interval
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

        void check(double n)
        {
            if (n > gr && n <= lte) count++;
        }

        double getCh(int size)
        {
            return count/(double)size;
        }
    }
}
