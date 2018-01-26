import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;

public class Chart extends Canvas
{
    final Color background = Color.BLACK;
    int x0, y0;
    final int BAR_WIDTH = 10;
    final int CHART_FACTOR = 100;

    public Chart(int width, int height)
    {
        super();
        setSize(width, height);
        setBackground(background);
        createBufferStrategy(1);

        x0 = (int) (width / 4);
        y0 = (int) (height * 3 / 4);
    }

    public void drawInitialLines(Graphics gr, int L)
    {
        gr.setColor(Color.WHITE);
        gr.drawString("x0 = " + x0 + " y0 = " + y0, 400,400);
//        gr.drawLine(10,10,50,50);
        gr.drawLine(0, y0, getWidth(), y0);  // x axis
        gr.drawLine(x0, 0, x0, getHeight());  // y axis

        gr.drawString("0", x0 - 10,y0 + 15);
        gr.drawString("pj", x0 - 15, 15);

//        double oneL = 1d / (double)L;
        int Ly = 30;
        gr.drawString("1/L", x0 - 30, Ly);
//        System.out.println(Ly);
//        System.out.println(oneL);
        gr.drawLine(x0 - 10, Ly, getWidth(), Ly);

//        gr.fillRect(10,10,50,50);
    }

    private int factor(double i)
    {
        return (int) (i * CHART_FACTOR);
    }

    public void drawBar(Graphics gr, double height, double x)
    {
        gr.setColor(Color.CYAN);

        height = factor(height);
        x = factor(x);
        gr.fillRect(x0 + (int)x, y0 - (int)height, BAR_WIDTH, (int)height);
        System.out.println("rect height = " + height + " rect x = " + x);
    }
}
