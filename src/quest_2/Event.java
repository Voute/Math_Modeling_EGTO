package quest_2;

/**
 * Created by egto1016 on 13.02.2018.
 */
public class Event
{
    public static final  int TYPE_START = 0;
    public static final int TYPE_END = 1;
    public final int eventTime;
    public final int eventType;
    public final Client client;

    public Event(int eventTime, int eventType, Client client)
    {
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.client = client;
    }
}
