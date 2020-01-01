package com.testproject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.Enumeration;
public class ReadSerialPort {
    static SerialPort serialPort;

    public static void main(String[] args) {
        serialPort = new SerialPort("COM4");
        try {
            serialPort.openPort();//Open port
            serialPort.setParams(9600, 8, 1, 0);//Set params
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            serialPort.setEventsMask(mask);//Set mask
            serialPort.addEventListener(new SerialPortReader());//Add SerialPortEventListener
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    /*
     * In this class must implement the method serialEvent, through it we learn about
     * events that happened to our port. But we will not report on all events but only
     * those that we put in the mask. In this case the arrival of the data and change the
     * status lines CTS and DSR
     */
    static class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
           // System.out.println("Event"+ event.getEventType());
            if(event.isRXCHAR()){//If data is available
               // System.out.println("TYPE["+event.getEventValue()+"]");
                //if(event.getEventValue() == 1){//Check bytes count in the input buffer
                    //Read data, if 10 bytes available
                    try {
                        byte buffer[] = serialPort.readBytes(1);

                        //System.out.println("DATA="+ new String(buffer));
                        System.out.print(new String(buffer));
                    }
                    catch (SerialPortException ex) {
                        System.out.println(ex);
                    }
              //  }
            }

        }
    }
}
