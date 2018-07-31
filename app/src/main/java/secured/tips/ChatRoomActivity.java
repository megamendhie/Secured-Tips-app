package secured.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import datafiles.Cache;
import datafiles.ChatMessage;
import datafiles.MessageAdapter;

public class ChatRoomActivity extends AppCompatActivity {
    static int room = 1;
    private boolean login = false;
    FirebaseUser user;
    String userID="x";
    String userName;
    Cache cacher = new Cache();
    private Map<String, Boolean> message_likes = new HashMap<>();;
    private final String TAG = "SecuredChat";
    private ListView listView;
    private DatabaseReference mRef;
    private FirebaseListAdapter<ChatMessage> adapter;
    private FloatingActionButton btnSend;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        message_likes.put("00default", true);

        final EditText input = findViewById(R.id.input);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String subRoom = selectDatabase();
        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms").child(subRoom);
        mRef.keepSynced(true);
        if(user!=null){
            login = true;
            userID = user.getUid();
            userName = user.getDisplayName();
        }
        listView = findViewById(R.id.list);
        btnSend = findViewById(R.id.fab);
        showOldMesseges();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user!=null){
                    if (input.getText().toString().trim().equals("")) {
                    } else {
                        mRef
                                .push()
                                .setValue(new ChatMessage(userName, input.getText().toString(),
                                        userID, "2_"+userID, 0, message_likes));
                        input.setText("");
                    }
                }
                else{
                    popUp();
                }

            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    public void popUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("You must login first")
                .setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .show();
    }

    public String selectDatabase(){
        room = getIntent().getIntExtra("ROOM", 1);
        switch (room){
            case 1:
                return "room_1";
            case 2:
                return "room_2";
            case 3:
                return "room_3";
            case 4:
                return "room_4";
            case 5:
                return "room_5";
            case 6:
                return "room_6";
            default:
                return "room_1";
        }
    }

    public FirebaseUser getUser(){
        return user;
    }


    private void showOldMesseges(){
        adapter = new MessageAdapter(this, ChatMessage.class, R.layout.mammy, mRef, userID, selectDatabase(), this);
        listView.setAdapter(adapter);
        Log.i(TAG, "messages shown");
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    protected void onResume(){
        super.onResume();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            if(login!=true){
                userID = user.getUid();
                userName = user.getDisplayName();
                login=true;
                showOldMesseges();
                adapter.startListening();
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
