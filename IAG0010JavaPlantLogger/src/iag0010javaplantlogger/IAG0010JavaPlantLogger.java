/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iag0010javaplantlogger;

import java.io.*;
import java.net.*;
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

    public static void readOrWriteData(JTextArea displayedText) {
        int nRead = 0, offset = 0, nToRead = 0, nToWrite = 0;
        byte Buf[] = new byte[2048];

        try {
            inStream = socket.getInputStream(); 
            nRead = inStream.read(Buf, offset, nToRead);
            displayedText.append(getStringFromInputStream(inStream));
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nRead < 0) {
            System.out.println("Nothin can be displayed");
        }

        try {
            outStream = socket.getOutputStream();
            outStream.write(Buf, offset, nToWrite);
        } catch (IOException ex) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getStringFromInputStream(InputStream is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    Logger.getLogger(IAG0010JavaPlantLogger.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
        return sb.toString();
    }
}
