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

    Chart2(int width, int height)
    {
        super(width, height);
        BAR_WIDTH = 6;  // 6
        BAR_HEIGHT = 40;
        X_FACTOR = 2d;
        Y_FACTOR = 100;

        x0 = 50;
        y0 = getHeight() - 50;
    }

    public void drawInitialLines(Graphics gr, int minutes)
    {
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

    @Override
    public void drawBar(Graphics gr, double height, double x, int barId)
    {
        height = factorX(height);
        x = factorX(x);
        gr.setColor(Color.CYAN);
        gr.fillRect(x0 + (int)x + BAR_WIDTH * barId, y0 - (int)height, BAR_WIDTH, (int)height);
        gr.setColor(Color.RED);
        gr.drawRect(x0 + (int)x + BAR_WIDTH * barId, y0 - (int)height, BAR_WIDTH, (int)height);
        System.out.println("rect height = " + height + " rect x = " + x);
    }

    public void drawServStart(Graphics gr, Client client)
    {
        int x = x0 + ((int)client.timeArrival) * BAR_WIDTH;
        int y = y0;
        int length = client.getGrade() * BAR_HEIGHT;
        gr.setColor(client.color);
        gr.drawLine(x, y, x, y - length);
        client.lastLineX = x;
        client.lastLineY = y - length;
    }

    public void drawServEnd(Graphics gr, Client client)
    {
        int x1 = client.lastLineX;
        int y1 = client.lastLineY;
        int x2 = x0 + ((int)client.timeLeave) * BAR_WIDTH;
        int y2 = y1;
        gr.setColor(client.color);
        gr.drawLine(x1, y1, x2, y2);
        x1 = x2;
        y1 = y2;
        x2 = x1;
        y2 = y0;
        gr.drawLine(x1, y1, x2, y2);
    }

    public void drawServDowngraded(Graphics gr, Client client, double downgradetime)
    {
        int endX = x0 + ((int)downgradetime) * BAR_WIDTH;
        int endY = y0 - (client.getGrade() + 1) * BAR_HEIGHT;
        gr.setColor(client.color);
        gr.drawLine(client.lastLineX, client.lastLineY, endX, endY);
        int endY2 = y0 - client.getGrade() * BAR_HEIGHT;
        gr.drawLine(endX, endY, endX, endY2);
        client.lastLineX = endX;
        client.lastLineY = endY2;
    }
}
