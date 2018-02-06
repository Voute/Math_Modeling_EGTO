import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;

/**
 * Created by egto1016 on 23.01.2018.
 */
public class EFrame extends JFrame
{
    public final JComponent coreComponent;

    private EFrame(String title, int width, int height, JComponent component)
    {
        super(title);
        setSize(width, height);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        setContentPane(panel);
        getContentPane().add(component);
        coreComponent = component;
    }

    private EFrame(String title, int width, int height)
    {
        super(title);
        setSize(width, height);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        setContentPane(panel);
        coreComponent = panel;
    }

    public static EFrame createSimpleFrame(String title, int width, int height)
    {
        EFrame frame = new EFrame(title, width, height);

        return frame;
    }

    public static EFrame createTextFrame(String title, int width, int height)
    {
        TextArea area = new TextArea();
//        frame.getContentPane().add(area);

//        addTask(title, frame);

        EFrame frame = new EFrame(title, width, height, area);

        return frame;
    }

    public static EFrame createTableFrame(String title, FrequencyTable table, int width, int height)
    {
//        JFrame frame = createFrame(title, width, height);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
//        frame.getContentPane().add(scrollPane);

//        addTask(title, frame);

        EFrame frame = new EFrame(title, width, height, scrollPane);
        return frame;
    }

    public static EFrame createListFrame(String title, Object[] data, int width, int height)
    {
//        JFrame frame = createFrame(title, width, height);

        JList list = new JList(data);
        list.setVisible(true);
        Dimension size = new Dimension(100, 600);
        list.setSize(size);
        list.setPreferredSize(size);
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.setSelectedIndex(0);
//        list.addListSelectionListener(this);
//        list.setVisibleRowCount(5);
        JScrollPane pane = new JScrollPane(list);
        pane.getVerticalScrollBar().setBlockIncrement(500);
//        pane.setSize(size);
//        pane.setPreferredSize(size);
//        frame.getContentPane().add(pane);

//        addTask(title, frame);

        EFrame frame = new EFrame(title, width, height, pane);

        return frame;
    }

    public static EFrame createCanvasFrame(String title, DrawAction action, Chart chart, int width, int height)
    {
//        JFrame frame = createFrame(title, width, height);
        EFrame frame = new EFrame(title, width, height);

//        final Canvas canvas = new Canvas();
//        canvas.setSize(700, 700);
//        canvas.setBackground(Color.BLACK);
//        canvas.createBufferStrategy(1);
        frame.add(chart, BorderLayout.CENTER);

//        addTask(title, frame);

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

        frame.add(but, BorderLayout.EAST);
        frame.repaint();
        frame.setVisible(false);

        return frame;
    }

}
