package com.example.pc.myapplication;

import android.os.Message;
import android.os.Messenger;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private Button bCon , bStartAudioRecv , bStartAudioPlay;
    private EditText sendText;
    private TextView text;
    private Thread thread , receiveThread;
    private DatagramPacket dp , recvDp;
    private DatagramSocket ds , recvDs;
    boolean RUN_THREAD = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bCon = (Button)findViewById(R.id.bCon);
        bStartAudioRecv = (Button)findViewById(R.id.button);
        bStartAudioPlay = (Button)findViewById(R.id.button2);
        sendText = (EditText)findViewById(R.id.editText);
        text = (TextView)findViewById(R.id.textView);

        receiveThread = new Thread(Receiver);
        receiveThread.start();

        bCon.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                thread = new Thread(Connection);
                thread.start();
            }
        });

        bStartAudioRecv.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                thread = new Thread(StartAudioRecv);
                thread.start();
            }
        });

        bStartAudioPlay.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                thread = new Thread(StartAudioPlay);
                thread.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RUN_THREAD = false;
        receiveThread.interrupt();
        receiveThread = null;
        recvDs.close();
    }



    private  Runnable Connection = new Runnable(){
        @Override
        public void run() {

            try{
                String sendData = "REGX";
                InetAddress serverIP = InetAddress.getByName("192.168.25.1");
                int serverPort = 7080;
                dp = new DatagramPacket(sendData.getBytes(), sendData.length(), serverIP, serverPort);
                ds = new DatagramSocket();
                ds.setReuseAddress(true);
                ds.send(dp);
            }
            catch(Exception  e){
                Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                e.printStackTrace();
                //displayExceptionMessage(e.getMessage());
            }
            finally {
                ds.close();
            }
        }
    };

    private Runnable StartAudioRecv = new Runnable() {
        @Override
        public void run() {
            try{
                    String sendData = "CHTX";
                    InetAddress serverIP = InetAddress.getByName("192.168.25.1");
                    int serverPort = 7080;
                    dp = new DatagramPacket(sendData.getBytes(), sendData.length(), serverIP, serverPort);
                    ds = new DatagramSocket();
                    ds.setReuseAddress(true);
                    ds.send(dp);
            }
            catch(Exception  e){
                Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                e.printStackTrace();
                //displayExceptionMessage(e.getMessage());
            }
            finally {
                ds.close();
            }
        }
    };

    private Runnable StartAudioPlay = new Runnable() {
        @Override
        public void run() {
            try{
                String sendData = "CHRX";
                InetAddress serverIP = InetAddress.getByName("192.168.25.1");
                int serverPort = 7080;
                dp = new DatagramPacket(sendData.getBytes(), sendData.length(), serverIP, serverPort);
                ds = new DatagramSocket();
                ds.setReuseAddress(true);
                ds.send(dp);
            }
            catch(Exception  e){
                Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                e.printStackTrace();
                //displayExceptionMessage(e.getMessage());
            }
            finally {
                ds.close();
            }
        }
    };

    public void displayExceptionMessage(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Runnable Receiver = new Runnable() {
        @Override
        public void run() {
            try{
                byte[] receData = new byte[4];
                int serverPort = 7080;
                recvDp = new DatagramPacket(receData , 4);
                recvDs = new DatagramSocket(serverPort);
                recvDs.setReuseAddress(true);
                String txt;

                while(RUN_THREAD){
                    text.setText("123");
                    recvDs.receive(recvDp);
                    txt = new String(receData , 0 , recvDp.getLength());
                    if(txt == "ANWS")
                        text.setText(txt);
                    else
                        text.setText(txt);
                }
            }
            catch(Exception  e){
                Log.e("ERROR", "ERROR IN CODE: " + e.toString());
                e.printStackTrace();
            }
            finally {
                recvDs.close();
            }
        }
    };
}
