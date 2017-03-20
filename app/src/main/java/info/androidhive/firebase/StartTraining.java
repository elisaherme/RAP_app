package info.androidhive.firebase;


    import java.io.IOException;
    import java.io.InputStream;
    import java.io.OutputStream;
    import java.util.ArrayList;
    import java.util.UUID;
    import java.lang.String;
    import java.lang.StringBuilder;

    import android.support.v7.app.AppCompatActivity;
    import android.bluetooth.BluetoothAdapter;
    import android.bluetooth.BluetoothDevice;
    import android.bluetooth.BluetoothSocket;
    import android.content.Intent;
    import android.os.Bundle;
    import android.os.Handler;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.widget.Button;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.os.Message;

    public class StartTraining extends AppCompatActivity {

        private static final String TAG = "Start_Training";

        // SPP UUID service
        private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

        final int handlerState = 0;//used to identify handler message
        Button btnOn, btnOff;
        TextView txtString, txtStringLength, sensorView0, sensorView1, sensorView2, sensorView3;
        Handler bluetoothIn;
        private BluetoothAdapter btAdapter = null;
        private BluetoothSocket btSocket = null;
        private StringBuilder recDataString = new StringBuilder();
        private ConnectedThread mConnectedThread;
        private String FSR0, FSR1, FSR2, velocity;
        private String myStr;
        int [] FSR1Seat = new int[]{};
        int [] FSR2Seat = new int[]{};
        int [] FSR3Seat = new int []{};
        int [] V = new int[]{};

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_start_training);

            //Link the buttons and textViews to respective views
            btnOn = (Button) findViewById(R.id.button13);
            btnOff = (Button) findViewById(R.id.button25);
            sensorView0 = (TextView) findViewById(R.id.sensorView0);
            sensorView1 = (TextView) findViewById(R.id.sensorView1);
            sensorView2 = (TextView) findViewById(R.id.sensorView2);
            sensorView3 = (TextView) findViewById(R.id.sensorView3);

            bluetoothIn = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == handlerState) { //if message is what we want
                        String readMessage = (String) msg.obj;// msg.arg1 = bytes from connect thread
                        recDataString.append(readMessage); //keep appending to string until ~
                        int endOfLineIndex = recDataString.indexOf("~");// determine the end-of-line
                        if (endOfLineIndex > 0) { // make sure there data before ~
                            String dataInPrint = recDataString.substring(0, endOfLineIndex); // extract string
                            txtString.setText("Data Received = " + dataInPrint);
                            int dataLength = dataInPrint.length(); //get length of data received
                            txtStringLength.setText("String Length = " + String.valueOf(dataLength));
                            myStr = recDataString.toString();

                            if (recDataString.charAt(0) == 'F') {
                                for(int i = 0 ; i < dataInPrint.length(); i++)
                                {
                                    if (recDataString.indexOf("FSR0") == -1) {
                                        int find_index = dataInPrint.indexOf("|");
                                        dataInPrint = dataInPrint.substring(find_index, endOfLineIndex);
                                        find_index = dataInPrint.indexOf("|");
                                        FSR0 = dataInPrint.substring(0, find_index);
                                        int F0 = Integer.valueOf(FSR0);
                                        FSR1Seat[i] = F0;
                                    }
                                    if (recDataString.indexOf("FSR1") == -1) {
                                        int find_index = dataInPrint.indexOf("|");
                                        dataInPrint = dataInPrint.substring(find_index, endOfLineIndex);

                                        find_index = dataInPrint.indexOf("|");
                                        FSR1 = dataInPrint.substring(0, find_index);
                                        int F1 = Integer.valueOf(FSR1);
                                        FSR2Seat[i] = F1;

                                    }
                                    if (recDataString.indexOf("FSR2") == -1) {

                                        int find_index = dataInPrint.indexOf("|");
                                        dataInPrint = dataInPrint.substring(find_index, endOfLineIndex);

                                        find_index = dataInPrint.indexOf("|");

                                        FSR2 = dataInPrint.substring(0, find_index);
                                        int F2 = Integer.valueOf(FSR2);
                                        FSR3Seat[i] = F2;
                                    }
                                    if (recDataString.indexOf("V") == -1) {

                                        int find_index = dataInPrint.indexOf("|");

                                        dataInPrint = dataInPrint.substring(find_index, endOfLineIndex);

                                        find_index = dataInPrint.indexOf("|");
                                        velocity = dataInPrint.substring(0, find_index);
                                        int V1 = Integer.valueOf(velocity);
                                        V[i] = V1;
                                    }


                                    sensorView0.setText(" FSR 0 Newtons = " + FSR0 + "V"); //update the textviews with sensor values
                                    sensorView1.setText(" FSR 1 Newtons = " + FSR1 + "V");
                                    sensorView2.setText(" FSR 2 Newtons = " + FSR2 + "V");
                                    sensorView3.setText(" velocity = " + velocity + "V");
                                }

                            }

                            sensorView0.setText(" FSR 0 Newtons = " + FSR0 + "V"); //update the textviews with sensor values
                            sensorView1.setText(" FSR 1 Newtons = " + FSR1 + "V");
                            sensorView2.setText(" FSR 2 Newtons = " + FSR2 + "V");
                            sensorView3.setText(" velocity = " + velocity + "V");
                        }
                    }

                    recDataString.delete(0,recDataString.length()); //clear all string data
                    //strIncom=" ";
                    //dataInPrint=" ";

                }};

            btAdapter = BluetoothAdapter.getDefaultAdapter();// get Bluetooth adapter
            checkBTState();

            // Set up onClick listeners for buttons to send 1 or 0 to turn on/off LED

            btnOn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    btnOn.setEnabled(false);
                    mConnectedThread.write("0"); // Send "0" via Bluetooth
                    Toast.makeText(getBaseContext(), "Start taking in data", Toast.LENGTH_SHORT).show();

                }
            });

            btnOff.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    btnOff.setEnabled(false);
                    mConnectedThread.write("1");
                    Toast.makeText(getBaseContext(), "Stop taking in data", Toast.LENGTH_SHORT).show();
                }
            });
        }

        private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

            return device.createRfcommSocketToServiceRecord(MY_UUID);
            //creates secure outgoing connecetion with BT device using UUID
        }

        @Override
        public void onResume() {
            super.onResume();

            // MAC-address of Bluetooth module (you must edit this line)
            String address = "98:D3:31:FB:2C:16";

            //create device and set the MAC address
            BluetoothDevice device = btAdapter.getRemoteDevice(address);

            try {
                btSocket = createBluetoothSocket(device);
            } catch (IOException e) {
                Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
            }
            // Establish the Bluetooth socket connection.
            try {
                btSocket.connect();
            } catch (IOException e) {
                try {
                    btSocket.close();
                } catch (IOException e2) {
                    //insert code to deal with this
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();

            //I send a character when resuming.beginning transmission to check device is connected
            //If it is not an exception will be thrown in the write method and finish() will be called
            //mConnectedThread.write("x");
        }

        @Override
        public void onPause() {
            super.onPause();
            try {
                //Don't leave Bluetooth sockets open when leaving activity
                btSocket.close();
            } catch (IOException e2) {
                //insert code to deal with this
            }
        }

        //Checks that the Android device Bluetooth is available and prompts to be turned on if off
        private void checkBTState() {

            if (btAdapter == null) {
                Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
            }
            else {
                if (btAdapter.isEnabled()) {
                    Toast.makeText(getBaseContext(), "Bluetooth already on", Toast.LENGTH_LONG).show();
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
                } catch (IOException e) {
                    //
                }

                mmInStream = tmpIn;
                mmOutStream = tmpOut;
            }

            public void run() {
                byte[] buffer = new byte[1024];
                int bytes;

                // Keep looping to listen for received messages
                while (true) {
                    try {
                        bytes = mmInStream.read(buffer); //read bytes from input buffer
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
              byte[] msgBuffer = input.getBytes(); //converts entered String into bytes
             try {
                mmOutStream.write(msgBuffer);//write bytes over BT connection via outstream
            } catch (IOException e) {
            //if you cannot write, close the application
             Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
             finish();

            }
             }
        }


    // when you click the back button, it returns to Home_Screen
    public void onClickBack(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}