import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setSize(500,600);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setLocationRelativeTo(null);
        window.setLayout(null);

        JButton[] numberButtons = new JButton[10];
        for (int i = 1; i < numberButtons.length;i++){
            numberButtons[i] = new JButton(i+"");
            numberButtons[i].setBounds(130+60*((i-1)%3),200+60*((i-1)<3? 0: (i-1) < 6 ? 1 : 2),60,60);
            numberButtons[i].setFocusable(false);
            window.add(numberButtons[i]);
        }
        numberButtons[0] = new JButton("0");
        numberButtons[0].setFocusable(false);
        numberButtons[0].setBounds(numberButtons[8].getX(),numberButtons[8].getY()+numberButtons[8].getWidth(),numberButtons[8].getWidth(),numberButtons[8].getHeight());
        window.add(numberButtons[0]);

        JButton[] operatorButtons = new JButton[9];
        for (int i = 0 ; i < operatorButtons.length;i++){
            operatorButtons[i] = new JButton();
            operatorButtons[i].setFocusable(false);
            window.add(operatorButtons[i]);
        }
        operatorButtons[0].setText("+");
        operatorButtons[1].setText("-");
        operatorButtons[2].setText(".");
        operatorButtons[3].setText("*");
        operatorButtons[4].setText("/");
        operatorButtons[5].setText(")");
        operatorButtons[6].setText("(");
        operatorButtons[7].setText("^");
        operatorButtons[8].setText("√");
        operatorButtons[0].setBounds(numberButtons[6].getX()+numberButtons[6].getWidth(),numberButtons[6].getY(),numberButtons[6].getWidth(),numberButtons[6].getHeight());
        operatorButtons[1].setBounds(numberButtons[9].getX()+numberButtons[9].getWidth(),numberButtons[9].getY(),numberButtons[9].getWidth(),numberButtons[9].getHeight());
        operatorButtons[2].setBounds(numberButtons[0].getX()+numberButtons[0].getWidth(),numberButtons[0].getY(),numberButtons[0].getWidth(),numberButtons[0].getHeight());
        operatorButtons[3].setBounds(numberButtons[3].getX()+numberButtons[3].getWidth(),numberButtons[3].getY(),numberButtons[3].getWidth(),numberButtons[3].getHeight());
        operatorButtons[4].setBounds(numberButtons[3].getX()+numberButtons[3].getWidth(),numberButtons[3].getY()-numberButtons[3].getWidth(),numberButtons[3].getWidth(),numberButtons[3].getHeight());
        operatorButtons[5].setBounds(numberButtons[3].getX(),numberButtons[3].getY()-numberButtons[3].getWidth(),numberButtons[3].getWidth(),numberButtons[3].getHeight());
        operatorButtons[6].setBounds(numberButtons[2].getX(),numberButtons[2].getY()-numberButtons[2].getWidth(),numberButtons[2].getWidth(),numberButtons[2].getHeight());
        operatorButtons[7].setBounds(numberButtons[1].getX()-numberButtons[1].getWidth(),numberButtons[1].getY(),numberButtons[1].getWidth(),numberButtons[1].getHeight());
        operatorButtons[8].setBounds(numberButtons[4].getX()-numberButtons[4].getWidth(),numberButtons[4].getY(),numberButtons[4].getWidth(),numberButtons[4].getHeight());
        JButton deleteBut = new JButton("->");
        deleteBut.setFocusable(false);
        JButton calculateBut = new JButton("=");
        calculateBut.setFocusable(false);
        JButton resetBut = new JButton("C");
        resetBut.setFocusable(false);
        deleteBut.setBounds(numberButtons[1].getX(),numberButtons[1].getY()-numberButtons[1].getWidth(),numberButtons[1].getWidth(),numberButtons[1].getHeight());
        calculateBut.setBounds(numberButtons[9].getX()+numberButtons[9].getWidth(),numberButtons[9].getY()+numberButtons[9].getWidth(),numberButtons[9].getWidth(),numberButtons[9].getHeight());
        resetBut.setBounds(numberButtons[0].getX()-numberButtons[0].getWidth(),numberButtons[0].getY(),numberButtons[0].getWidth(),numberButtons[0].getHeight());
        window.add(resetBut);
        window.add(calculateBut);
        window.add(deleteBut);

        JTextField exerciseBut = new JTextField("");
        exerciseBut.setFont(new Font("Comic Sans ms", Font.BOLD, 18));
        exerciseBut.setHorizontalAlignment(JTextField.CENTER);
        exerciseBut.setBounds(numberButtons[1].getX(),numberButtons[1].getY()-numberButtons[1].getWidth()*2, numberButtons[1].getWidth()+exerciseBut.getText().length()*10, numberButtons[1].getHeight());
        exerciseBut.setForeground(Color.green);
        exerciseBut.setEditable(false);
        window.add(exerciseBut);

        JButton methodBut = new JButton("method");
        methodBut.setForeground(Color.blue);
        methodBut.setFocusable(false);
        methodBut.setOpaque(false);
        methodBut.setContentAreaFilled(false); // להסיר צבע רקע מהכפתור
        methodBut.setBorderPainted(false);
        methodBut.setBounds(exerciseBut.getX()-80,exerciseBut.getY(),100,70);
        methodBut.setVisible(false);
        window.add(methodBut);


        for (int i = 0; i < numberButtons.length; i++) {
            int index = i; // הגדרת משתנה מקומי לשמירת הערך הנוכחי של i
            numberButtons[index].addActionListener((event) -> {
                if (exerciseBut.getForeground() == Color.red){
                    exerciseBut.setForeground(Color.green);
                    exerciseBut.setText("");

                }
                methodBut.setVisible(false);
                exerciseBut.setText(exerciseBut.getText() + numberButtons[index].getText());
                exerciseBut.setBounds(numberButtons[1].getX(),numberButtons[1].getY()-numberButtons[1].getWidth()*2, numberButtons[1].getWidth()+exerciseBut.getText().length()*10, numberButtons[1].getHeight());
            });
        }
        for (int i = 0; i < operatorButtons.length; i++) {
            int index = i; // הגדרת משתנה מקומי לשמירת הערך הנוכחי של i
            operatorButtons[index].addActionListener((event) -> {
                if (exerciseBut.getForeground() != Color.red){
                    exerciseBut.setText(exerciseBut.getText() + operatorButtons[index].getText());
                    methodBut.setVisible(false);
                    exerciseBut.setBounds(numberButtons[1].getX(), numberButtons[1].getY() - numberButtons[1].getWidth() * 2, numberButtons[1].getWidth() + exerciseBut.getText().length() * 10, numberButtons[1].getHeight());
                }
            });
        }
        deleteBut.addActionListener((event) ->{
            if (!(exerciseBut.getText().isEmpty()) && exerciseBut.getForeground() != Color.red) {
                exerciseBut.setText(exerciseBut.getText().substring(0, exerciseBut.getText().length() - 1));
                methodBut.setVisible(false);
                exerciseBut.setBounds(numberButtons[1].getX(), numberButtons[1].getY() - numberButtons[1].getWidth() * 2, numberButtons[1].getWidth() + exerciseBut.getText().length() * 10, numberButtons[1].getHeight());
            }
        });
        resetBut.addActionListener((event) ->{
            exerciseBut.setText("");
            exerciseBut.setForeground(Color.green);
            methodBut.setVisible(false);
            exerciseBut.setBounds(numberButtons[1].getX(),numberButtons[1].getY()-numberButtons[1].getWidth()*2, numberButtons[1].getWidth()+exerciseBut.getText().length()*10, numberButtons[1].getHeight());
        });
        calculateBut.addActionListener((event) ->{
            if (!(exerciseBut.getText().isEmpty()) && exerciseBut.getForeground() != Color.red) {
                String exercise = exerciseBut.getText();
                try {
                    exerciseBut.setText(Calculator.getSolving(exercise) + "");
                    methodBut.setVisible(true);
                } catch (Exception e) {
                    exerciseBut.setText(e.getMessage());
                    exerciseBut.setForeground(Color.red);
                }
                exerciseBut.setBounds(numberButtons[1].getX(), numberButtons[1].getY() - numberButtons[1].getWidth() * 2, numberButtons[1].getWidth() + exerciseBut.getText().length() * 10, numberButtons[1].getHeight());
            }
        });
        methodBut.addActionListener((event)->{
            UIManager.put("OptionPane.okButtonText", "בחזרה למחשבון !");
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 16));
            UIManager.put("Button.background", Color.RED);
            UIManager.put("Button.foreground", Color.WHITE);
            JOptionPane.showMessageDialog(window,Calculator.getWayToSolution());
        });
        window.setVisible(true);
    }
}