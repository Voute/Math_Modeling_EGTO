package quest_2;

import quest_1.Chart;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Chart2 extends Chart
{
    int TRAIT_LENGTH = 6;

    Chart2(int width, int height)
    {
        super(width, height);
        X_FACTOR = 10d;
        Y_FACTOR = 20;

        x0 = 50;
        y0 = getHeight() - 50;
    }

    public void zoomIn()
    {
        // 1 5 10 20 40 80 160
        if (X_FACTOR > 1)
        {
            X_FACTOR /= 2;
            Y_FACTOR *= 2;
        }

    }

    public void zoomOut()
    {
        // 1 5 10 20 40 80 160
        if (X_FACTOR < 160)
        {
            X_FACTOR *= 2;
            Y_FACTOR /= 2;
        }
    }

    public void drawInitialLines(Graphics gr, int minutes)
    {
        gr.setColor(Color.black);
        gr.fillRect(0,0,getWidth(), getHeight());
        gr.setColor(Color.WHITE);
        gr.drawLine(0, y0, getWidth(), y0);  // x axis
        gr.drawLine(x0, 0, x0, getHeight());  // y axis

        int half_length = TRAIT_LENGTH / 2;

        for (int i = 1; i <= minutes; i++)
        {
            int x = x0 + resizeXvalue(i*60);

            int mod_hour = i % 60;
            int trim_hour = (int)(i / 60);
            int xshift_label = 6;

            if (mod_hour == 0)
            {
                gr.drawString(Integer.toString(trim_hour) + "h", x-xshift_label, y0 + 40);
                gr.drawLine(x, y0 - half_length, x, y0 + half_length + 20);
            }
            else if (mod_hour % 5 == 0)
            {
                if (mod_hour == 5)
                {
                    gr.drawString(Integer.toString(mod_hour), x-xshift_label+3, y0 + 20);
                } else
                {
                    gr.drawString(Integer.toString(mod_hour), x-xshift_label, y0 + 20);
                }
                gr.drawLine(x, y0 - half_length, x, y0 + half_length);
            }

        }

        for (int i = 1; i <= 10; i++)
        {
            int y = y0 - resizeYvalue(i);
            gr.drawLine(x0 - half_length, y, x0 + half_length, y);
            gr.drawString(Integer.toString(i), x0 - half_length - 20, y + 6);
        }

        gr.drawString("minutes", getWidth() - 50, y0 + 20);
        gr.drawString("clients", x0 - 50, 20);

        gr.drawString("x0: " + x0, 10, 60);
        gr.drawString("y0: " + y0, 10, 80);
    }

    public void drawChartLine(Graphics gr, ChartLine line, Color color)
    {
        gr.setColor(color);
        gr.drawLine(line.x1, line.y1, line.x2, line.y2);
    }

    public void populateServStart(Client client)
    {
        int length = resizeYvalue(client.getGrade());
        int x1 = x0 + resizeXvalue(client.timeArrival);
        int y1 = y0;
        int x2 = x1;
        int y2 = y1 - length;
        client.stayLine.clear();
        client.addStayLine(new ChartLine(x1, y1, x2, y2));
        System.out.println("[ServStart] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

    }

    public void populateServEndLines(Client client)
    {
        int x1 = client.lastLineX;
        int y1 = client.lastLineY;
        int x2 = x0 + resizeXvalue(client.timeLeave);
        int y2 = y1;
        int x3 = x2;
        int y3 = y2;
        int x4 = x2;
        int y4 = y0;
        System.out.println("[ServEnd] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")] [(" + x3 + ";" + y3 + ")(" + x4 + ";" + y4 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

        client.addStayLine(new ChartLine(x1, y1, x2, y2));
        client.addStayLine(new ChartLine(x3, y3, x4, y4));

    }

    public void populateSrvDowngradeLines(Client client, int downgradetime)
    {
        int x1 = client.lastLineX;
        int y1 = client.lastLineY;
        int x2 = x0 + resizeXvalue(downgradetime);
        int y2 = y0 - resizeYvalue(client.getGrade() + 1); // previous grade
        int x3 = x2;
        int y3 = y2;
        int x4 = x2;
        int y4 = y0 - resizeYvalue(client.getGrade());
        client.addStayLine(new ChartLine(x1, y1, x2, y2));
        client.addStayLine(new ChartLine(x3, y3, x4, y4));
        System.out.println("[ServDown] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")] [(" + x3 + ";" + y3 + ")(" + x4 + ";" + y4 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

    }

    private int resizeXvalue(int seconds)
    {
        return (int) (seconds / X_FACTOR);
    }

    private int resizeYvalue(int clientsSize)
    {
        return (int) (clientsSize * Y_FACTOR);
    }
}
