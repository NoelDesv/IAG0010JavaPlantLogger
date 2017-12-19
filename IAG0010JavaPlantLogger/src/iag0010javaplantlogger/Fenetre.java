/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iag0010javaplantlogger;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Quentin
 */
public class Fenetre extends JFrame {

    final JButton OPENBUTTON = new JButton("Open");
    final JButton CLOSEBUTTON = new JButton("Close");
    final JButton CONNECTBUTTON = new JButton("Connect");
    final JButton DISCONNECTBUTTON = new JButton("Disconnect");
    final JButton STARTBUTTON = new JButton("Start");
    final JButton BREAKBUTTON = new JButton("Break");
    final JButton EXITBUTTON = new JButton("Exit");
    final Dimension d = new Dimension(150, 30);

    final JTextArea TEXTAREA = new JTextArea();
    final JScrollPane SCROLLPANE = new JScrollPane(TEXTAREA);
    // set min, max and preferred sizes

    final JPanel TEXTAREAPANEL = new JPanel();
    final JPanel LOGFILEPANEL = new JPanel();
    final JPanel CONNECTIONPANEL = new JPanel();
    final JPanel MEASUREMENTPANEL = new JPanel();
    final JPanel EXITPANEL = new JPanel();
    final JPanel pan = new JPanel();

    boolean fileSelected = false;
    boolean connected = false;
    String fileName;
    Scanner sc = new Scanner(System.in);

    public Fenetre() {
        this.setTitle("IAG0010JavaPlantLogger");
        this.setSize(700, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setPreferredSizeOnButtons();
        textAreaPanel();
        logFilePanel();
        connectionPanel();
        measurementPanel();
        exitPanel();
        finalPanel();

        this.getContentPane().add(pan);
        this.setVisible(true);

        addActionListenerOnButtons();
        CLOSEBUTTON.setEnabled(false);
        CONNECTBUTTON.setEnabled(false);
        DISCONNECTBUTTON.setEnabled(false);
        STARTBUTTON.setEnabled(false);
        BREAKBUTTON.setEnabled(false);
    }

    public void textAreaPanel() {
        TEXTAREAPANEL.setLayout(new BoxLayout(TEXTAREAPANEL, BoxLayout.X_AXIS));
        TEXTAREAPANEL.add(TEXTAREA);
        TEXTAREAPANEL.add(SCROLLPANE);
    }

    public void logFilePanel() {
        LOGFILEPANEL.setBorder(BorderFactory.createTitledBorder("Log file"));
        LOGFILEPANEL.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        LOGFILEPANEL.add(OPENBUTTON);
        LOGFILEPANEL.add(CLOSEBUTTON);
    }

    public void connectionPanel() {
        CONNECTIONPANEL.setBorder(BorderFactory.createTitledBorder("Connection"));
        CONNECTIONPANEL.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        CONNECTIONPANEL.add(CONNECTBUTTON);
        CONNECTIONPANEL.add(DISCONNECTBUTTON);
    }

    public void measurementPanel() {
        MEASUREMENTPANEL.setBorder(BorderFactory.createTitledBorder("Measurement"));
        MEASUREMENTPANEL.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 20));
        MEASUREMENTPANEL.add(STARTBUTTON);
        MEASUREMENTPANEL.add(BREAKBUTTON);
    }

    public void exitPanel() {
        EXITPANEL.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
        EXITPANEL.add(EXITBUTTON);
    }

    public void finalPanel() {
        pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
        pan.add(TEXTAREAPANEL);
        pan.add(LOGFILEPANEL);
        pan.add(CONNECTIONPANEL);
        pan.add(MEASUREMENTPANEL);
        pan.add(EXITPANEL);
    }

    public void setPreferredSizeOnButtons() {
        OPENBUTTON.setPreferredSize(d);
        CLOSEBUTTON.setPreferredSize(d);
        CONNECTBUTTON.setPreferredSize(d);
        DISCONNECTBUTTON.setPreferredSize(d);
        STARTBUTTON.setPreferredSize(d);
        BREAKBUTTON.setPreferredSize(d);
        EXITBUTTON.setPreferredSize(d);
    }

    public void addActionListenerOnButtons() {
        OPENBUTTON.addActionListener(new OPENBUTTONActionListener());
        CLOSEBUTTON.addActionListener(new CLOSEBUTTONActionListener());
        CONNECTBUTTON.addActionListener(new CONNECTBUTTONActionListener());
        DISCONNECTBUTTON.addActionListener(new DISCONNECTBUTTONActionListener());
        STARTBUTTON.addActionListener(new STARTBUTTONActionListener());
        BREAKBUTTON.addActionListener(new BREAKBUTTONActionListener());
        EXITBUTTON.addActionListener(new EXITBUTTONActionListener());
    }

    class OPENBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Open\"");

            do {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Files txt", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    fileName = chooser.getSelectedFile().getName();
                    System.out.println("You chose to open this file: " + fileName);
                    fileSelected = true;
                    OPENBUTTON.setEnabled(false);
                    CLOSEBUTTON.setEnabled(true);
                    CONNECTBUTTON.setEnabled(true);
                }
                return;
            } while (!fileSelected);

        }
    }

    class CLOSEBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Close\"");
        }
    }

    class CONNECTBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Connect\"");

            do {
                IAG0010JavaPlantLogger.connectToServer();
                //TEXTAREA.append("Salut!");
                /* J'essaie d'afficher ce qu'envoie le serveur, c'est à dier "Identify".
                Pour cela, je me sers de la fonction qui se trouve dans la classe 
                principale et qui prend en argument le JTextArea où je 
                souhaite écrire*/
                IAG0010JavaPlantLogger.readData(TEXTAREA);
                IAG0010JavaPlantLogger.sendData("coursework");
                IAG0010JavaPlantLogger.readData(TEXTAREA);
                //TEXTAREA.append("Please, enter the password.");
                connected = true;
            } while (!connected && fileSelected);
            
            DISCONNECTBUTTON.setEnabled(true);
            STARTBUTTON.setEnabled(true);
            BREAKBUTTON.setEnabled(true);
            CONNECTBUTTON.setEnabled(false);
        }
    }

    class DISCONNECTBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Disconnect\"");
            IAG0010JavaPlantLogger.sendData("stop");
            //IAG0010JavaPlantLogger.readData(TEXTAREA);
            
            DISCONNECTBUTTON.setEnabled(false);
            STARTBUTTON.setEnabled(false);
            BREAKBUTTON.setEnabled(false);
            CONNECTBUTTON.setEnabled(true);
            CLOSEBUTTON.setEnabled(false);
            OPENBUTTON.setEnabled(true);
        }
    }

    class STARTBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Start\"");
        }
    }

    class BREAKBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Break\"");
        }
    }

    class EXITBUTTONActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("You have clicked on \"Exit\"");
            System.exit(0);
        }
    }
}
