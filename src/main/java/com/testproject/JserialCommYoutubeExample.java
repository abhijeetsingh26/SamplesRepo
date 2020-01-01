package com.testproject;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.io.IOException;

public class JserialCommYoutubeExample {
    static public void main(String[] args) throws InterruptedException {
        SerialPort comPort = SerialPort.getCommPort("COM4");
        comPort.setBaudRate(38400);
      //  comPort.openPort();
        MessageListener listener = new MessageListener();
        //comPort.addDataListener(listener);
       /* try { Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
        comPort.removeDataListener();
        comPort.closePort();*/
        byte[] arr = new byte[1];
        arr[0] = (byte)1;
        System.out.println(arr);
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
           comPort.addDataListener(new MessageListener());
           try {
               int i=0;


               while(i<180) {
                   byte[] arr2 = new byte[1];
                   arr2[0] = (byte) i;
                   comPort.getOutputStream().flush();
                   System.out.println("SENDING ["+i+"]");
                   comPort.getOutputStream().write(arr2);
                   comPort.getOutputStream().flush();
                   i+=1;
                   Thread.sleep(30);
               }

//               while(i<180){
//                   System.out.println("SENDING COMMAND["+i+"]");
//                   String command = "1;"+i+"L";
//                   byte[] commandBytes = command.getBytes();
//                   comPort.getOutputStream().flush();
//                   comPort.getOutputStream().write(commandBytes);
//                   comPort.getOutputStream().flush();
//                   System.out.println("SENDING COMMAND["+i+"] END");
//                   i+=3;
//                   Thread.sleep(500);
//               }
//               while(i>0){
//                   System.out.println("SENDING COMMAND["+i+"]");
//                   String command = "1;"+i+"L";
//                   byte[] commandBytes = command.getBytes();
//                   comPort.getOutputStream().flush();
//                   comPort.getOutputStream().write(commandBytes);
//                   comPort.getOutputStream().flush();
//                   System.out.println("SENDING COMMAND["+i+"] END");
//                   i-=3;
//                   Thread.sleep(500);
//               }
           } catch (IOException e) {
               e.printStackTrace();
           }


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

