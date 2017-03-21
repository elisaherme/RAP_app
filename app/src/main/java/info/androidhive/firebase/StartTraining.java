package info.androidhive.firebase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class StartTraining extends Activity {

    TextView txtArduino, txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
    Handler bluetoothIn;

    final int handlerState = 0;        				 //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    ArrayList<String> FSR1Seat = new ArrayList<>();
    ArrayList<String> FSR2Seat = new ArrayList<>();
    ArrayList<String> FSR3Seat = new ArrayList<>();
    ArrayList<String> V = new ArrayList<>();

    private ConnectedThread mConnectedThread;

    // SPP UUID service - this should work for most devices
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    // String for MAC address
    private static String address = "98:D3:31:FB:2C:16";
    boolean keepGoing = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_training);

        //Link the buttons and textViews to respective views
        txtString = (TextView) findViewById(R.id.txtString);
        txtStringLength = (TextView) findViewById(R.id.testView1);
        sensorView0 = (TextView) findViewById(R.id.sensorView0);
        sensorView1 = (TextView) findViewById(R.id.sensorView1);
        sensorView2 = (TextView) findViewById(R.id.sensorView2);
        sensorView3 = (TextView) findViewById(R.id.sensorView3);

        //while(keepGoing){
            bluetoothIn = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == handlerState) {										//if message is what we want
                        String readMessage = (String) msg.obj;   // msg.arg1 = bytes from connect thread
                        recDataString.append(readMessage);      								//keep appending to string until ~
                        int endOfLineIndex = recDataString.indexOf("~"); // determine the end-of-line
                        if ((endOfLineIndex > 0) && (keepGoing)) {                                           // make sure there data before ~
                            String dataInPrint = recDataString.substring(0, endOfLineIndex);    // extract string
                            txtString.setText("Data Received = " + dataInPrint);
                            int dataLength = dataInPrint.length();							//get length of data received
                            txtStringLength.setText("String Length = " + String.valueOf(dataLength));
                            if (recDataString.charAt(0) == '#')								//if it starts with # we know it is what we are looking for
                            {
                                String str = recDataString.toString().replace("#", "").replace("~","");
                                //list.add(str);
                                String[] parts = str.split(";");
                                String sensor0 = parts[0];             //get sensor value from string between indices 1-5
                                String sensor1 = parts[1];            //same again...
                                String sensor2 = parts[2];
                                String sensor3 = parts[3];
                                FSR1Seat.add(sensor0);
                                FSR2Seat.add(sensor1);
                                FSR3Seat.add(sensor2);
                                V.add(sensor3);
                                Log.d("sensor0",sensor0);
                                Log.d("sensor1",sensor1);
                                Log.d("sensor2",sensor2);
                                Log.d("sensor3",sensor3);
                                sensorView0.setText(" FSR 0 = " + sensor0);	//update the textviews with sensor values
                                sensorView1.setText(" FSR 1 = " + sensor1);
                                sensorView2.setText(" FSR 2 = " + sensor2);
                                sensorView3.setText(" Velocity = " + sensor3);
                            }
                            recDataString.delete(0, recDataString.length()); 					//clear all string data
                            // strIncom =" ";
                            dataInPrint = " ";
                            //myStr.delete(0, myStr.length());
                        }
                    }
                }
            };
        //}

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter
        checkBTState();

    }


    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return  device.createRfcommSocketToServiceRecord(BTMODULEUUID);
        //creates secure outgoing connecetion with BT device using UUID
    }

    public void onClickStop(View view){
        keepGoing = false;

        if(view.getId() == R.id.StopButton)
        {
            Button btn = (Button)findViewById(R.id.StopButton);
            btn.setEnabled(false);

        }

        Intent i = new Intent(this, LastTraining.class);
        i.putStringArrayListExtra("list", FSR1Seat);
        i.putStringArrayListExtra("fsr2", FSR2Seat);
        i.putStringArrayListExtra("fsr3", FSR3Seat);
        i.putStringArrayListExtra("vel", V);
        startActivity(i);

    }

    @Override
    public void onResume() {
        super.onResume();

        //Get MAC address from DeviceListActivity via intent
        Intent intent = getIntent();

        //Get the MAC address from the DeviceListActivty via EXTRA
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        //create device and set the MAC address
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }
        // Establish the Bluetooth socket connection.
        try
        {
            btSocket.connect();
        } catch (IOException e) {
            try
            {
                btSocket.close();
            } catch (IOException e2)
            {
                //insert code to deal with this
            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();

        //I send a character when resuming.beginning transmission to check device is connected
        //If it is not an exception will be thrown in the write method and finish() will be called
        mConnectedThread.write("x");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try
        {
            //Don't leave Bluetooth sockets open when leaving activity
            btSocket.close();
        } catch (IOException e2) {
            //insert code to deal with this
        }
    }


    public void onClickMainScreen(View view){
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }


    //Checks that the Android device Bluetooth is available and prompts to be turned on if off
    private void checkBTState() {

        if(btAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    //create new class for connect thread
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    bytes = mmInStream.read(buffer);        	//read bytes from input buffer
                    String readMessage = new String(buffer, 0, bytes);
                    // Send the obtained bytes to the UI Activity via handler
                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }
        }
    }
}
