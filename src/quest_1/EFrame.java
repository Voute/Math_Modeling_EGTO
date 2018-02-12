package quest_1;

import javax.swing.*;
import java.awt.*;
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

        EFrame frame = new EFrame(title, width, height, area);

        return frame;
    }

    public static EFrame createTableFrame(String title, JTable table, int width, int height)
    {
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        EFrame frame = new EFrame(title, width, height, scrollPane);
        return frame;
    }

    public static EFrame createListFrame(String title, Object[] data, int width, int height)
    {

        JList list = new JList(data);
        list.setVisible(true);
        Dimension size = new Dimension(100, 600);
        list.setSize(size);
        list.setPreferredSize(size);
        JScrollPane pane = new JScrollPane(list);
        pane.getVerticalScrollBar().setBlockIncrement(500);

        EFrame frame = new EFrame(title, width, height, pane);

        return frame;
    }

    public static EFrame createCanvasFrame(String title, DrawAction action, Chart chart, int width, int height)
    {
        EFrame frame = new EFrame(title, width, height);

        frame.add(chart, BorderLayout.CENTER);

        frame.setVisible(true);

        JButton but = new JButton();
        but.setText("paint");
        but.setVisible(true);

        but.addActionListener( event ->
        {
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
        });

        frame.add(but, BorderLayout.EAST);
        frame.repaint();
        frame.setVisible(false);

        return frame;
    }

}
