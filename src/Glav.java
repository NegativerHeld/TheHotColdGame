import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Glav {
    public String NamePlay;
    public int Attept;
    public int Target;
    public int Prom;
    private JTextField Vvod;
    private JLabel Nazv;
    private JLabel Vedite;
    private JPanel FirstPanel;
    private JPanel SecPanel;
    private JButton Check;
    private JButton NewGAme;
    private JButton Statistic;
    private JButton Exit;
    private JTextArea Pole;

    public Glav() {
        NamePlay = JOptionPane.showInputDialog("Введите ваше имя:");

        NewGAme.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Target = new Random().nextInt(100)+1;
                Attept = 0;
                Pole.setText("Новая игра началась! Угадай число\n");
                Pole.append("Привет! "+NamePlay+" Попробуй угадать число от 1 до 100\n");
            }
        });
        Check.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    int V = Integer.parseInt(Vvod.getText());
                    Attept++;
                    if (V < 1 || V > 100) {
                        JOptionPane.showMessageDialog(null, "Ошибка! Переменные введены неверно!");
                    } else {
                        Prom = Math.abs(V - Target);
                        if (Prom < 5 && V < Target) {
                            Pole.append("Горячо! Попробуй больше!\n");
                        } else if (Prom < 5 && V > Target) {
                            Pole.append("Горячо! Попробуй меньше!\n");
                        } else if (Prom < 10 && V < Target) {
                            Pole.append("Теплее! Попробуй больше!\n");
                        } else if (Prom < 10 && V > Target) {
                            Pole.append("Теплее! Попробуй меньше!\n");
                        } else if (Prom < 20 && V > Target) {
                            Pole.append("Холодно! Попробуй меньше!\n");
                        } else if (Prom < 20 && V < Target) {
                            Pole.append("Холодно! Попробуй больше!\n");
                        } else if (Prom < 30 && V > Target) {
                            Pole.append("Мороз! Попробуй меньше!\n");
                        } else if (Prom < 30 && V < Target) {
                            Pole.append("Мороз! Попробуй больше!\n");
                        } else if (V < Target) {
                            Pole.append("Холодрыга! Попробуй больше\n");
                        } else if (V > Target){
                            Pole.append("Холодрыга! Попробуй меньше! \n");
                        }
                        else if (V == Target) {
                            Pole.append("Поздравляю! ты угадал число " + Target + " за " + Attept + " попыток!\n");
                            try (FileWriter writer = new FileWriter(NamePlay, true)) {
                                writer.write(NamePlay + " - " + Attept + " попыток\n");
                            } catch (IOException exception) {
                                Pole.append("Ошибка записи результата.\n");
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    Pole.append("Введите корректные данные \n");
                } catch (HeadlessException ex) {
                    throw new RuntimeException(ex);
                }
                Vvod.setText("");
            }
        });
        Statistic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Pole.setText("");
                try (BufferedReader reader = new BufferedReader(new FileReader(NamePlay))){
                    String line;
                    while ((line = reader.readLine()) != null){
                        Pole.append(line+"\n");
                    }
                }catch (IOException exception) {
                    Pole.append("Ошибка чтения файла.\n");
                }
            }
        });
        Exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Glav");
        frame.setContentPane(new Glav().FirstPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200,400);
        frame.setVisible(true);
    }
}
