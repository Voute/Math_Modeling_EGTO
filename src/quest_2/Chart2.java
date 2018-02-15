package quest_2;

import quest_1.Chart;

import java.awt.*;
import java.util.List;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Chart2 extends Chart
{
    int TRAIT_LENGTH = 6;
    int BAR_HEIGHT;
    int POWER_SEC;

    Chart2(int width, int height)
    {
        super(width, height);
        BAR_WIDTH = 4;  // 6
        BAR_HEIGHT = 40;
        X_FACTOR = 2d;
        Y_FACTOR = 100;

        x0 = 50;
        y0 = getHeight() - 50;
    }

    public void zoomIn()
    {
        // 6 12 24
        if (BAR_WIDTH < 24)
        {
            BAR_WIDTH *= 2;
            BAR_HEIGHT *= 2;
            POWER_SEC *= 2;
        }

    }

    public void zoomOut()
    {
        // 6 12 24
        if (BAR_WIDTH > 6)
        {
            BAR_WIDTH /= 2;
            BAR_HEIGHT /= 2;
            POWER_SEC /= 2;
        }
    }

    public void drawInitialLines(Graphics gr, int minutes)
    {
        gr.setColor(Color.black);
        gr.fillRect(0,0,getWidth(), getHeight());
        gr.setColor(Color.WHITE);
//        gr.drawString("x0 = " + x0 + " y0 = " + y0, 400,400);
        gr.drawLine(0, y0, getWidth(), y0);  // x axis
        gr.drawLine(x0, 0, x0, getHeight());  // y axis

        int half_length = TRAIT_LENGTH / 2;

        for (int i = 1; i <= minutes; i++)
        {
            int x = x0 + i*BAR_WIDTH;

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
            int y = y0 - i * BAR_HEIGHT;
            gr.drawLine(x0 - half_length, y, x0 + half_length, y);
            gr.drawString(Integer.toString(i), x0 - half_length - 20, y + 6);
        }

        gr.drawString("minutes", getWidth() - 50, y0 + 20);
        gr.drawString("clients", x0 - 50, 20);
    }

    public void drawServStart(Graphics gr, Client client)
    {
        int length = client.getGrade() * BAR_HEIGHT;
        int x1 = x0 + ((int)client.timeArrival) * BAR_WIDTH + getChartSecondsRemainder(client.timeArrival);
        int y1 = y0;
        int x2 = x1;
        int y2 = y1 - length;
        gr.setColor(client.color);
        gr.drawLine(x1, y1, x2, y2);
        client.lastLineX = x2;
        client.lastLineY = y2;
        System.out.println("[ServStart] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

    }

    public void drawServEnd(Graphics gr, Client client)
    {
        int x1 = client.lastLineX;
        int y1 = client.lastLineY;
        int x2 = x0 + ((int)client.timeLeave) * BAR_WIDTH + getChartSecondsRemainder(client.timeLeave);
        int y2 = y1;
        int x3 = x2;
        int y3 = y2;
        int x4 = x2;
        int y4 = y0;
        System.out.println("[ServEnd] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")] [(" + x3 + ";" + y3 + ")(" + x4 + ";" + y4 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

        gr.setColor(client.color);
        gr.drawLine(x1, y1, x2, y2);
        gr.drawLine(x3, y3, x4, y4);
    }

    public void drawServDowngraded(Graphics gr, Client client, double downgradetime)
    {
        int x1 = client.lastLineX;
        int y1 = client.lastLineY;
        int x2 = x0 + ((int)downgradetime) * BAR_WIDTH + getChartSecondsRemainder(downgradetime);
        int y2 = y0 - (client.getGrade() + 1) * BAR_HEIGHT;
        int x3 = x2;
        int y3 = y2;
        int x4 = x2;
        int y4 = y0 - client.getGrade() * BAR_HEIGHT;
        gr.setColor(client.color);
        gr.drawLine(x1, y1, x2, y2);
        gr.drawLine(x3, y3, x4, y4);
        client.lastLineX = x4;
        client.lastLineY = y4;
        System.out.println("[ServDown] [(" + x1 + ";" + y1 + ")(" + x2 + ";" + y2 + ")] [(" + x3 + ";" + y3 + ")(" + x4 + ";" + y4 + ")]");
        System.out.println("[client] start: " + client.timeArrival + " end: " + client.timeLeave);

    }

    private int getChartSecondsRemainder(double n)
    {
        int mod = (int)( (n - (int)n) * 10 );
        int seconds = mod * 6; // 6 seconds are in 0.1 minute
        return (int)(seconds / 10) * POWER_SEC; // draw every 10 seconds
    }

    private int calculateSeconds(double minutes)
    {
        int min = (int)minutes;
        int seconds = (int)( (double)(minutes - min) * 60d );
        seconds += (min * 60);
        return seconds;
    }
}
