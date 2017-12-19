/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iag0010javaplantlogger;

import static com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility.getBytes;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Quentin
 */
public class IAG0010JavaPlantLogger {

    /**
     * @param args the command line arguments
     */
    private static InetAddress adress;
    private static Socket socket;
    private static final int PORT = 1234;
    private static InputStream inStream;
    private static OutputStream outStream;

    public static void main(String[] args) {
        // TODO code application logic here
        Fenetre fen = new Fenetre();

    }

    public static void connectToServer() {

        try {
            adress = InetAddress.getByAddress(new byte[]{127, 0, 0, 1});
            socket = new Socket(adress, PORT);
        } catch (UnknownHostException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeServer() {
        try {
            socket.close();
            socket.shutdownInput();
            socket.shutdownOutput();
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void readData(JTextArea displayedText) {
        int nRead = 0;
        byte Buf[] = new byte[2048];

        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            nRead = in.read(Buf);
            displayedText.append("Message received from emulator: " + new String(Buf, 1, nRead));
            displayedText.append("\n");
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nRead < 0) {
            System.out.println("Nothing can be displayed");
        }
    }

    public static void sendData(String dataToSend) {
        byte[] Buf = new byte[2 * dataToSend.length() + 6];
        Buf[0] = (byte) (2 * dataToSend.length() + 6);
        BitConverter.ToBytes(dataToSend, Buf, 4);
        
        try {
            System.out.println("Vous êtes au bon endroit");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(Buf);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
    /*public static void sendStop() {
        String password = "stop";
        int sizePassword = password.length();
        //byte[] Buf = new byte[sizePassword * 2 + 6];
        byte[] Buf = new byte[14];
        //Buf[0] = (byte) (sizePassword + 2 * 3);
        Buf[0] = (byte) (14);
        BitConverter.ToBytes(password, Buf, 4);
        
        try {
            System.out.println("Vous êtes au bon endroit");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(Buf);
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }*/
}
