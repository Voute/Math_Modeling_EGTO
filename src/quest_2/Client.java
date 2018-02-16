package quest_2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Client
{
    final int timeArrival;  // in seconds
    final int timeServ;     // in seconds
    final int timeLeave;    // in seconds
    int timeServStart;
    int timeInQueue;
    final int timeInHall;
    final Color color;
    int grade = 0;
    int lastLineX;
    int lastLineY;
    ArrayList<ChartLine> stayLine;

    Client(int timeArrival, int timeServ, Color color)
    {
        this.timeArrival = timeArrival;
        this.timeServ = timeServ;
        this.timeLeave =  timeArrival + timeServ;
        this.color = color;
        this.timeInHall = timeLeave - timeArrival;

        stayLine = new ArrayList<>();
    }

    void upGrade()
    {
        grade++;
    }

    void addStayLine(ChartLine line)
    {
        stayLine.add(line);
        lastLineX = line.x2;
        lastLineY = line.y2;
    }

    int downGrade()
    {
        grade--;
        System.out.println("client is downgraded to " + grade);
        return grade;
    }

    void setLastLineX(int n)
    {
        lastLineX = n;
        System.out.println("[set] lastLineX: " + n);
    }

    void setLastLineY(int n)
    {
        lastLineY = n;
        System.out.println("[set] lastLineY: " + n);
    }

    int setServStartTime(int servStartTime)
    {
        timeServStart = servStartTime;
        timeInQueue = timeServStart - timeArrival;
        return timeInQueue;
    }

    void setGrade(int newGrade)
    {
        grade = newGrade;
    }

    public int getGrade()
    {
        return grade;
    }
}
