package quest_2;

import java.awt.*;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Client
{
    final int timeArrival;  // in seconds
    final int timeServ;     // in seconds
    final int timeLeave;    // in seconds
    final Color color;
    int grade = 0;
    int lastLineX;
    int lastLineY;

    Client(int timeArrival, int timeServ, Color color)
    {
        this.timeArrival = timeArrival;
        this.timeServ = timeServ;
        this.timeLeave =  timeArrival + timeServ;
        this.color = color;
    }

    void upGrade()
    {
        grade++;
    }

    void downGrade()
    {
        grade--;
        System.out.println("client is downgraded to " + grade);
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

    void setGrade(int newGrade)
    {
        grade = newGrade;
    }

    public int getGrade()
    {
        return grade;
    }
}
