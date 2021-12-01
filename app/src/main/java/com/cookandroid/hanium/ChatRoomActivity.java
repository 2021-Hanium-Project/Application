package com.cookandroid.hanium;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    ArrayList<ChatData> list = new ArrayList<>();
    EditText editText;
    TextView chatroom;
    Button button;
    String myId,partnerId, chatroomName ,myNickname,partnerNickname;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        sharedPreferences = getSharedPreferences("preferences",MODE_PRIVATE);
        myNickname = sharedPreferences.getString("nickname","");

        recyclerView = findViewById(R.id.chatroom_recyclerview);
        editText = findViewById(R.id.chatRoom_EditText);
        button = findViewById(R.id.chatRoom_sendBtn);
        adapter = new RecyclerAdapter(list, myNickname);
        chatroom = findViewById(R.id.chatroom);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        Intent intent= getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences("preferences",MODE_PRIVATE);
        myId = sharedPreferences.getString("id","");
        partnerId = intent.getStringExtra("partnerId");
        partnerNickname = intent.getStringExtra("partnerNickname");

        chatroomName=makeRoomName(myId,partnerId);
        chatroom.setText(partnerNickname);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("chat").child(chatroomName);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                ChatData data = snapshot.getValue(ChatData.class);
                adapter.addChat(data);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText() != null){
                    ChatData chat = new ChatData();
                    chat.setNickname(myNickname);
                    chat.setMessage(editText.getText().toString());

                    myRef.push().setValue(chat);
                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                    editText.setText("");

                }
            }
        });

    }

    private String makeRoomName(String myId, String partnerId){
        int intMyId = Integer.parseInt(myId);
        int intPartnerId = Integer.parseInt(partnerId);

        if(intMyId>intPartnerId){
            return partnerId+"-"+myId;
        }

        else{
            return myId+"-"+partnerId;
        }
    }
}