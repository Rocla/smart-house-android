package ch.he.arc.p1.g5.fi5t;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Toast;
import android.bluetooth.BluetoothSocket;




public class Connection extends Activity {


    TextView tvModuleInfo;
    Button bBlueTests, bLogin;
    Button bModule;
    TextView dStatus;
    ProgressBar mProgress;
    CheckBox cbRemember;
    EditText dUsername, dPassword;
    SharedPreferences savedUser;
    String sUsername;
    String sPassword;
    Boolean blnChecked, blnBT = false;

    String message;
    String[] messageParts = new String[14];

    // Debugging
    private static final String TAG = "Connection";
    private static final boolean D = false;

    // Message types sent from the BluetoothChatService Handler
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    // Key names received from the BluetoothChatService Handler
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;

    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    private Button mSendButton;

    // Name of the connected device
    private String mConnectedDeviceName = null;
    // Array adapter for the conversation thread
    private ArrayAdapter<String> mConversationArrayAdapter;
    // String buffer for outgoing messages
    private StringBuffer mOutStringBuffer;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private static BlueConnection mChatService = null;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        bModule = (Button) findViewById(R.id.bModule);

        tvModuleInfo = (TextView)findViewById(R.id.tvModuleInfo);

        bModule.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                openOptionsMenu();

            }

        });


        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        dUsername = (EditText) findViewById(R.id.dUsername);
        bLogin = (Button) findViewById(R.id.bLogin);

        savedUser = getSharedPreferences(Services.MyProfile, Context.MODE_PRIVATE);

        bBlueTests = (Button) findViewById(R.id.bBluetests);
        dStatus = (TextView) findViewById(R.id.dStatus);
        dPassword = (EditText) findViewById(R.id.dPassword);
        mProgress = (ProgressBar) findViewById(R.id.progressBarLogin);


        boolean checkBoxValue = savedUser.getBoolean(Services.RememberMeCheckbox, false);
        if (checkBoxValue) {
            cbRemember.setChecked(true);
            if (savedUser.contains(Services.UserName)){
                dUsername.setText(savedUser.getString(Services.UserName, ""));
            }
            if (savedUser.contains(Services.Password)){
                dPassword.setText(savedUser.getString(Services.Password, ""));
            }
        }



        mProgress.setVisibility(View.INVISIBLE);

        bBlueTests.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                startActivity(new Intent (Connection.this, BlueTests.class));


            }

        });


        bLogin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){

                //Intent myIntent = new Intent(Connection.this, Main.class);

                //startActivity(myIntent);

                //SystemClock.sleep(2000);

                dStatus.setText("Connection in Progress");

                mProgress.setVisibility(View.VISIBLE);

                bLogin.setEnabled(false);



                sPassword = dPassword.getText().toString();
                sUsername = dUsername.getText().toString();
                blnChecked = cbRemember.isChecked();

                message = "00" + " ," + sUsername + " ," + sPassword + "\0";
                int lengthOfMessage = message.length();
                //Toast.makeText(getApplicationContext(), "before: " + lengthOfMessage, Toast.LENGTH_SHORT).show();
                for (int i=lengthOfMessage;i<140;i++) {
                    message = message + " ";
                }
                Connection.sendMessage(message);

                String send = "";

//                for (int i = 0; i < message.length(); i++) {
//                    send = "" + message.charAt(i);
//                    Connection.sendMessage(send);
//                    SystemClock.sleep(100);
//                    //bLogin.setText(i);
//                }





                //Toast.makeText(getApplicationContext(), "characters: " + lengthOfMessage, Toast.LENGTH_SHORT).show();

                //SystemClock.sleep(500);

                //Toast.makeText(getApplicationContext(), "recieved: " + BlueFetch.ReceivedResponse, Toast.LENGTH_SHORT).show();


                //sendMessage(message);

                //String message2 = "$$$";




                new Thread(new Runnable() {
                    public void run() {

                        SystemClock.sleep(500);

                        runOnUiThread(new Runnable() {
                            public void run() {

                                //messageParts[0] = BlueFetch.ReceivedResponse;
                                //Toast.makeText(getApplicationContext(), "Part 0: " + messageParts[0], Toast.LENGTH_SHORT).show();

                                //SystemClock.sleep(100);

                                //messageParts[1] = BlueFetch.ReceivedResponse;
                                //Toast.makeText(getApplicationContext(), "Part 1: " + messageParts[1], Toast.LENGTH_SHORT).show();

//                                SystemClock.sleep(100);

//                                messageParts[2] = BlueFetch.ReceivedResponse;
//                                Toast.makeText(getApplicationContext(), "Part 2: " + messageParts[2], Toast.LENGTH_SHORT).show();


                                if (BlueFetch.ReceivedResponse.matches(BlueFetch.AuthorizedLogin)){

                                    //Toast.makeText(getApplicationContext(), "CONNECTED: ", Toast.LENGTH_SHORT).show();

                                    Editor editor = savedUser.edit();
                                    editor.putBoolean(Services.RememberMeCheckbox, blnChecked);
                                    editor.putString(Services.UserName, sUsername);
                                    editor.putString(Services.Password, sPassword);
                                    editor.apply();


                                    // Create Inner Thread Class
                                    new Thread(new Runnable() {
                                        public void run() {

                                            SystemClock.sleep(1000);

                                            mProgress.post(new Runnable() {
                                                public void run() {
                                                    mProgress.setVisibility(View.INVISIBLE);
                                                }
                                            });

                                            dStatus.post(new Runnable() {
                                                public void run() {
                                                    dStatus.setText("Connected!");
                                                }
                                            });

                                            SystemClock.sleep(200);

                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    // some code that needs to be ran in UI thread
                                                    Intent myIntent = new Intent(Connection.this, Main.class);

                                                    //sendMessage("---\r\n");

                                                    startActivity(myIntent);
                                                    //startActivityForResult(myIntent);

                                                    //finish();
                                                }
                                            });

                                            SystemClock.sleep(2000);

                                            runOnUiThread(new Runnable() {
                                                public void run() {

                                                    //bLogin.setText("Previous Session");
                                                    bLogin.setEnabled(true);

                                                }
                                            });


                                        }
                                    }).start();

                                }else{

                                new Thread(new Runnable() {
                                    public void run() {

                                        SystemClock.sleep(500);

                                        mProgress.post(new Runnable() {
                                            public void run() {
                                                mProgress.setVisibility(View.INVISIBLE);
                                            }
                                        });

                                        dStatus.post(new Runnable() {
                                            public void run() {
                                                dStatus.setText("Mauvais \n Username ou Password!");
                                            }
                                        });

                                    }
                                }).start();
                                bLogin.setEnabled(true);

                            }

                        }
                        });

                    }
                }).start();

            }

        });

    }

    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mChatService == null) setupChat();
        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();

        // Performing this check in onResume() covers the case in which BT was
        // not enabled during onStart(), so we were paused to enable it...
        // onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
        if (mChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mChatService.getState() == BlueConnection.STATE_NONE) {
                // Start the Bluetooth chat services
                mChatService.start();
            }
        }
    }

    private void setupChat() {

        // Initialize the array adapter for the conversation thread
        mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.blue_message);
        //mConversationView = (ListView) findViewById(R.id.in);
        //mConversationView.setAdapter(mConversationArrayAdapter);

        // Initialize the compose field with a listener for the return key
        //mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        //mOutEditText.setOnEditorActionListener(mWriteListener);

        // Initialize the send button with a listener that for click events
        //mSendButton = (Button) findViewById(R.id.button_send);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//        mSendButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // Send a message using content of the edit text widget
//                TextView view = (TextView) findViewById(R.id.edit_text_out);
//                String message = view.getText().toString();
//                sendMessage(message);
//            }
//        });

        // Initialize the BluetoothChatService to perform bluetooth connections
        mChatService = new BlueConnection(this, mHandler);

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if(D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if(D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if(D) Log.e(TAG, "--- ON DESTROY ---");
    }

    private void ensureDiscoverable() {
        if(D) Log.d(TAG, "ensure discoverable");
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }

    /**
     * Sends a message.
     * @param message  A string of text to send.
     */
    public static void sendMessage(String message) {
        // Check that we're actually connected before trying anything
        if (mChatService.getState() != BlueConnection.STATE_CONNECTED) {
            //Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
            return;
        }

        // Check that there's actually something to send
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothChatService to write
            byte[] send = message.getBytes();
            mChatService.write(send);

            // Reset out string buffer to zero and clear the edit text field
            //mOutStringBuffer.setLength(0);
            //mOutEditText.setText(mOutStringBuffer);
        }
    }

    // The action listener for the EditText widget, to listen for the return key
    private TextView.OnEditorActionListener mWriteListener =
            new TextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                    // If the action is a key-up event on the return key, send the message
                    if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                        String message = view.getText().toString();
                        //sendMessage(message);
                    }
                    if(D) Log.i(TAG, "END onEditorAction");
                    return true;
                }
            };

    private final void setStatus(int resId) {
        //final ActionBar actionBar = getActionBar();
        tvModuleInfo.setText(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        //final ActionBar actionBar = getActionBar();
        tvModuleInfo.setText(subTitle);
    }

    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BlueConnection.STATE_CONNECTED:
                            setStatus(getString(R.string.title_connected_to, mConnectedDeviceName) + " " + mConnectedDeviceName);

                            mConversationArrayAdapter.clear();
                            break;
                        case BlueConnection.STATE_CONNECTING:
                            setStatus(R.string.title_connecting);
                            break;
                        case BlueConnection.STATE_LISTEN:
                        case BlueConnection.STATE_NONE:
                            setStatus(R.string.title_not_connected);
                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    byte[] writeBuf = (byte[]) msg.obj;
                    // construct a string from the buffer
                    String writeMessage = new String(writeBuf);
                    mConversationArrayAdapter.add("Me:  " + writeMessage);
                    break;
                case MESSAGE_READ:
                    byte[] readBuf = (byte[]) msg.obj;
                    // construct a string from the valid bytes in the buffer

                    String readMessage = new String(readBuf, 0, msg.arg1);
                    //mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                    //SystemClock.sleep(500);
                    BlueFetch.ReceivedResponse = readMessage;
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    //Toast.makeText(getApplicationContext(), "Connected to "
                    //        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, true);
                }
                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data, false);
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occurred
                    //Log.d(TAG, "BT not enabled");
                    //Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    private void connectDevice(Intent data, boolean secure) {
        // Get the device MAC address
        String address = data.getExtras()
                .getString(BlueDeviceList.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        mChatService.connect(device, secure);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.connection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent serverIntent = null;
        switch (item.getItemId()) {
            case R.id.secure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, BlueDeviceList.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
                return true;
            case R.id.insecure_connect_scan:
                // Launch the DeviceListActivity to see devices and do scan
                serverIntent = new Intent(this, BlueDeviceList.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
                return true;
            //case R.id.discoverable:
            // Ensure this device is discoverable by others
            //    ensureDiscoverable();
            //    return true;
        }
        return false;
    }

}
