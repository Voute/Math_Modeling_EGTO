import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by egto1016 on 06.02.2018.
 */
public class Quest_1
{
    final EFrame rootFrame;
    final ArrayList<JButton> openTaskButtons;

    Double[] generatedValues_1;

    public Quest_1()
    {
        rootFrame = EFrame.createSimpleFrame("Practice 1. Tasks", 100, 400);
        rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        openTaskButtons = new ArrayList<>();
    }

    public void doTasks()
    {
        task_1_0();
        task_1_1();

        for (JButton button :openTaskButtons
             ) {
            rootFrame.coreComponent.add(button);
        }
    }

    public void show()
    {
        rootFrame.setVisible(true);
    }

    private void task_1_0()
    {
        generatedValues_1 = Calculator.generateNumbers();
    }

    private void task_1_1()
    {
        String title = "Task 1.1";
        EFrame frame = EFrame.createTextFrame(title, 250, 100);

        TextArea area = (TextArea)frame.coreComponent;

        double mathExpectation = Calculator.calculateMathExpectation(generatedValues_1);
        double selectiveAverage = Calculator.calculateSelectiveAverage(generatedValues_1);

        area.appendln("M{X} = " + mathExpectation);
        area.append("V srednyaya = " + selectiveAverage);

        createOpenTaskButton(title, frame);
    }

    private void createOpenTaskButton(String title, ActionListener action)
    {
        JButton button = new JButton(title);
        button.addActionListener(action);
        openTaskButtons.add(button);
    }

    private void createOpenTaskButton(String title, EFrame frame) {
        JButton button = new JButton(title);
        openTaskButtons.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.setVisible(true);
            }
        });
    }
}
