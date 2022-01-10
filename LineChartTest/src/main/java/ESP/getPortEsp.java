package ESP;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.util.Arrays;

public class getPortEsp  {
    public static void main(String[] args)  {
//        System.out.println(Arrays.toString(SerialPort.getCommPorts()));
        getESp eSp = new getESp();
        for(int i=0;i<SerialPort.getCommPorts().length;i++){
            System.out.println(SerialPort.getCommPorts()[i]);

            System.out.println(eSp.getListeningEvents());
            eSp.serialEvent(new SerialPortEvent(SerialPort.getCommPorts()[i],SerialPort.LISTENING_EVENT_DATA_AVAILABLE));
        }

    }


}
class getESp implements  SerialPortDataListener{

    @Override
    public int getListeningEvents() {
        return  SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

    }
}
