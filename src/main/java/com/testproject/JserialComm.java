package com.testproject;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

public class JserialComm{
    static public void main(String[] args) throws InterruptedException {
        SerialPort comPort = SerialPort.getCommPorts()[0];
      //  comPort.openPort();
        MessageListener listener = new MessageListener();
        //comPort.addDataListener(listener);
       /* try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
        comPort.removeDataListener();
        comPort.closePort();*/
       boolean runActuall = false;
       if(runActuall)
       for(int i=0;i<180;i+=10)
       {
           //comPort.addDataListener(listener);
           String command = "1;"+i ;
           byte[] commandBytes = command.getBytes();
           comPort.openPort();
           waitWhilePortOpenedOrRetry(comPort);
           comPort.addDataListener(new MessageListener());
           System.out.println("SENDING COMMAND "+ command);
           comPort.writeBytes(commandBytes,commandBytes.length);

       }else{
           comPort.openPort();
           waitWhilePortOpenedOrRetry(comPort);
           System.out.println("IS PORT OPENED"+comPort.isOpen());
           String command = "1;10\n";
           byte[] commandBytes = command.getBytes();
           comPort.addDataListener(new MessageListener());
           System.out.println("SENDING COMMAND 2 "+ command);
           comPort.writeBytes(commandBytes,commandBytes.length);
       }
    }
    private static void waitWhilePortOpenedOrRetry(SerialPort comPort) throws InterruptedException {
        int count = 0;
        while(!comPort.isOpen() && count<40){
            System.out.println("waiting for comport to open");
            Thread.sleep(100);
            count++;
        }
        System.out.println("Trying to close and reopen the port");
        comPort.closePort();
        Thread.sleep(5000);
        comPort.openPort();
    }

    private static final class MessageListener implements SerialPortMessageListener
    {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

        @Override
        public byte[] getMessageDelimiter() { return  System.getProperty("line.separator").getBytes(); }

        @Override
        public boolean delimiterIndicatesEndOfMessage() { return true; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            byte[] delimitedMessage = event.getReceivedData();
            System.out.println("Received the following delimited message: " + new String(delimitedMessage));
        }
    }


}

