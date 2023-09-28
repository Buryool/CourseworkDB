package Week4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleInteractionWindow {

    public static void main(String[] args) {
        // 创建一个 JFrame 实例作为主窗口
        JFrame frame = new JFrame("简单交互窗口");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        // 创建一个 JPanel 用于容纳组件
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        // 创建三个输入框和一个按钮
        JTextField textField1 = new JTextField();
        JTextField textField2 = new JTextField();
        JTextField textField3 = new JTextField();
        JButton submitButton = new JButton("确定");

        // 添加组件到面板
        panel.add(new JLabel("输入框1:"));
        panel.add(textField1);
        panel.add(new JLabel("输入框2:"));
        panel.add(textField2);
        panel.add(new JLabel("输入框3:"));
        panel.add(textField3);
        panel.add(submitButton);

        // 添加按钮点击事件监听器
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取输入框的内容
                String input1 = textField1.getText();
                String input2 = textField2.getText();
                String input3 = textField3.getText();

                // 在这里添加你希望执行的逻辑，可以使用输入框中的内容
                // 这里只是简单地打印内容
                System.out.println("输入框1: " + input1);
                System.out.println("输入框2: " + input2);
                System.out.println("输入框3: " + input3);
            }
        });

        // 将面板添加到主窗口
        frame.add(panel);

        // 设置窗口可见
        frame.setVisible(true);
    }
}
