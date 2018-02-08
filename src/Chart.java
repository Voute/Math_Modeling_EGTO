import java.awt.*;

public class Chart extends Canvas
{
    final Color background = Color.BLACK;
    int x0, y0;
    final int BAR_WIDTH = 10;
    final double X_FACTOR = 1d;
    final double Y_FACTOR = 100;
    int nextBarX = 0;

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
//        gre.drawLine(10,10,50,50);
        gr.drawLine(0, y0, getWidth(), y0);  // x axis
        gr.drawLine(x0, 0, x0, getHeight());  // y axis

        gr.drawString("0", x0 - 10,y0 + 15);
        gr.drawString("pj", x0 - 15, 15);

//        double oneL = 1d / (double)L;
        int Ly = 30;
        gr.drawString("1/L", x0 - 30, Ly);
//        System.out.println(Ly);
//        System.out.println("one L = " + oneL);
//        gre.drawLine(x0 - 10, Ly, getWidth(), Ly);

//        gre.fillRect(10,10,50,50);
    }

    private int factorX(double i)
    {
        return (int) (i * X_FACTOR);
    }
    private int factorY(double i)
    {
        return (int) (i * Y_FACTOR);
    }


    public void drawBar(Graphics gr, double height, double x)
    {

        height = factorX(height);
        x = factorX(x);
        gr.setColor(Color.CYAN);
        gr.fillRect(x0 + (int)x + nextBarX, y0 - (int)height, BAR_WIDTH, (int)height);
        gr.setColor(Color.RED);
        gr.drawRect(x0 + (int)x + nextBarX, y0 - (int)height, BAR_WIDTH, (int)height);
        System.out.println("rect height = " + height + " rect x = " + x);
        nextBarX += BAR_WIDTH;
    }
}
