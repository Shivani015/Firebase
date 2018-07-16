package com.example.shivanikoul.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    EditText messageET;
    Button sendBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference messageReference;

    ArrayList<String> messageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        messageET = findViewById(R.id.messageET);
        sendBtn = findViewById(R.id.sendBtn);

        messageData =new ArrayList<>();

        //1
        firebaseDatabase = FirebaseDatabase.getInstance();
        messageReference = firebaseDatabase.getReference("message");

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermessage = messageET.getText().toString().trim();

                if (usermessage.length() == 0) {
                    return;
                }

                messageReference.setValue(usermessage);

            }

        });

            messageReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String receivedmessage =dataSnapshot.getValue(String.class);

                    messageData.add(receivedmessage);
                    ArrayAdapter<String> adapter =new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1,messageData);
                    listView.setAdapter(adapter);

                    Toast.makeText(MainActivity.this,receivedmessage,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }
}
