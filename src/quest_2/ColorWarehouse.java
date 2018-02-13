package quest_2;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by egto1016 on 13.02.2018.
 */
public class ColorWarehouse
{
    ArrayList<Color> colors = new ArrayList<Color>();
    int i = 0;

    public ColorWarehouse()
    {
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.ORANGE);
        colors.add(Color.MAGENTA);
        colors.add(Color.WHITE);
    }

    public Color getColor()
    {
        Color returnColor = colors.get(i);

        i++;
        if (i == colors.size())
        {
            i = 0;
        }

        return returnColor;
    }
}
