/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iag0010javaplantlogger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quentin
 */
public class ReceivingThread extends Thread {

    final Object receivingThreadLock = new Object();
    ReceivingThread receivingThread = new ReceivingThread();
    boolean mustWait = true;

    ReceivingThread() {
        setDaemon(true);

        synchronized(receivingThreadLock) {
            while (mustWait) {
                try {
                    receivingThreadLock.wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReceivingThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                mustWait = true;
            }
        }
        
        synchronized(receivingThreadLock) {
                mustWait = false;
                receivingThreadLock.notify();
            }
    }

    @Override
    public void run() {
        receivingThread.start();
    }
}
