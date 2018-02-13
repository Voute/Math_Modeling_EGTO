package quest_2;

import java.awt.*;

/**
 * Created by egto1016 on 12.02.2018.
 */
public class Client
{
    final double timeArrival;
    final double timeServ;
    final double timeLeave;
    final Color color;
    int grade = 0;
    int lastLineX;
    int lastLineY;

    Client(double timeArrival, double timeServ, Color color)
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

    void setGrade(int newGrade)
    {
        grade = newGrade;
    }

    public int getGrade()
    {
        return grade;
    }
}
