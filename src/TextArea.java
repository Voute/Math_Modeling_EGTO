import javax.swing.*;

/**
 * Created by egto1016 on 23.01.2018.
 */
public class TextArea extends JTextArea
{
    public void appendln(String line)
    {
        line += "\n";
        super.append(line);
    }
}
