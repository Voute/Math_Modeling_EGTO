package quest_1;

import java.awt.*;

public class Chart extends Canvas
{
    protected final Color background = Color.BLACK;
    protected int x0, y0;
    protected int BAR_WIDTH = 10;
    protected double X_FACTOR = 1d;
    protected double Y_FACTOR = 100;

    public Chart(int width, int height)
    {
        super();
        setSize(width, height);
        setPreferredSize(new Dimension(width, height));
        setBackground(background);
        createBufferStrategy(1);

        x0 = (int) (width / 4);
        y0 = (int) (height * 3 / 4);
    }

    public void drawInitialLines(Graphics gr, int L)
    {
        gr.setColor(Color.WHITE);
        gr.drawString("x0 = " + x0 + " y0 = " + y0, 400,400);
        gr.drawLine(0, y0, getWidth(), y0);  // x axis
        gr.drawLine(x0, 0, x0, getHeight());  // y axis

        gr.drawString("0", x0 - 10,y0 + 15);
        gr.drawString("pj", x0 - 15, 15);

        int Ly = 30;
        gr.drawString("1/L", x0 - 30, Ly);
    }

    protected int factorX(double i)
    {
        return (int) (i * X_FACTOR);
    }
    protected int factorY(double i)
    {
        return (int) (i * Y_FACTOR);
    }


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
}
