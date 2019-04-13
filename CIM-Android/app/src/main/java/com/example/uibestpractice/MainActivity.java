package com.example.uibestpractice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MsgUpdater{

    private List<Msg> msgList = new ArrayList<Msg>();

    private EditText inputText;

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    private MsgService.MsgBinder msgBinder;

    private ServiceConnection serviceConnection;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = (int) (Math.random() * 888);
        setContentView(R.layout.activity_main);
        initMsgs(); // 初始化消息数据
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                msgBinder = (MsgService.MsgBinder) iBinder;
                msgBinder.setup("192.168.42.174", 45678, MainActivity.this);
                //todo
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    String toSend = "Me@" + TimeGetter.getTime() + ":\n" + content;
                    Msg msg = new Msg(toSend, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
                    inputText.setText(""); // 清空输入框中的内容
                    msgBinder.sendMsg("MobileUser"+id, content);
                }
            }
        });
        Intent intent = new Intent(this, MsgService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    private void initMsgs() {
//        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
//        msgList.add(msg1);
//        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
//        msgList.add(msg2);
//        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
//        msgList.add(msg3);
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Intent intent = new Intent(this, MsgService.class);
//        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
//    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }

    @Override
    public void newMsg(String s) {
        msgList.add(new Msg(s, Msg.TYPE_RECEIVED));
        adapter.notifyItemInserted(msgList.size() - 1); // 当有新消息时，刷新ListView中的显示
        msgRecyclerView.scrollToPosition(msgList.size() - 1); // 将ListView定位到最后一行
    }

    @Override
    public void unhandledException(CInstMsgException e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT);
    }
}
