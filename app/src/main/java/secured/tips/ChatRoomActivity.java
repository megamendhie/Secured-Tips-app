package secured.tips;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import datafiles.ChatMessage;
import datafiles.MessageAdapter;
import datafiles.MyHashtagHelper;
import datafiles.NewsFunction;
import datafiles.TipzoneActivity;

public class ChatRoomActivity extends TipzoneActivity{
    static int room = 1;
    private boolean login = false;
    FirebaseUser user;
    String userID="x";
    String userName;
    String subRoom;
    FirebaseDatabase chatDatabase;
    private Map<String, Boolean> message_likes = new HashMap<>();
    private final String TAG = "SecuredChat";
    private ListView listView;
    private DatabaseReference mRef;
    private FirebaseListAdapter<ChatMessage> adapter;
    private FloatingActionButton btnSend;
    private MyHashtagHelper mHastagHelper;
    ActionBar actionBar;
    Object timestampCreated;
    ProgressBar loader;
    String[] clubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        getWindow().setBackgroundDrawableResource(R.drawable.chatroom_bg);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        chatDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/");
        chatDatabase.goOffline();
        chatDatabase.goOnline();

        message_likes.put("00default", true);
        char[] additionalSymbols = new char[]{'@'};
        mHastagHelper = MyHashtagHelper.Creator.create(getResources().getColor(R.color.hastag), null, additionalSymbols);

        clubs = getResources().getStringArray(R.array.club_arrays);
        ArrayAdapter<String> club_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clubs);
        final MultiAutoCompleteTextView input = findViewById(R.id.input);
        input.setAdapter(club_adapter);
        input.setTokenizer(new SpaceTokenizer());
        input.setThreshold(3);
        input.setOnClickListener((View v)-> {
                listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_NORMAL);
                Log.i(TAG, "onClick: Input");
        });
        mHastagHelper.handle(input);

        user = FirebaseAuth.getInstance().getCurrentUser();
        subRoom = selectDatabase();
        mRef = chatDatabase.getReference().child("chatrooms").child(subRoom);
        mRef.keepSynced(true);
        if(user!=null){
            login = true;
            userID = user.getUid();
            userName = user.getDisplayName();
        }
        listView = findViewById(R.id.list);
        listView.setDrawingCacheEnabled(true);
        listView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        btnSend = findViewById(R.id.fab);
        loader =  findViewById(R.id.loader);
        listView.setEmptyView(loader);

        showOldMesseges();
        listView.smoothScrollToPosition(adapter.getCount()-1);

        attachKeyboardListeners();
        adapter.startListening();

        btnSend.setOnClickListener((View view) -> {
                if(user!=null){
                    if(!NewsFunction.isNetworkAvailable(getApplicationContext())) {
                        popUp2();
                        return;
                    }

                    if (input.getText().toString().trim().equals("")) {
                    } else {
                        timestampCreated = ServerValue.TIMESTAMP;
                        mRef.push()
                             .setValue(new ChatMessage(userName, input.getText().toString(),
                             userID, "2_"+userID, 0, message_likes));
                        input.setText("");
                        Log.i(TAG, "systemTime: " + new Date().getTime());
                    }
                }
                else{
                    popUp();
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

    public void popUp2(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        builder.setMessage("No Internet connection")
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
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
            case 7:
                return "room_7";
            default:
                return "room_1";
        }
    }

    public FirebaseUser getUser(){
        return user;
    }

    private void showOldMesseges(){
        adapter = new MessageAdapter(this, ChatMessage.class, R.layout.mssg_in, mRef, userID, selectDatabase(), this);
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
        mRef = chatDatabase.getReference().child("chatrooms").child(subRoom);
        mRef.keepSynced(true);
        if(user!=null){
            if(login!=true){
                adapter.stopListening();
                userID = user.getUid();
                userName = user.getDisplayName();
                login=true;
                showOldMesseges();
                adapter.startListening();
            }
        }
    }

    @Override
    protected void onHideKeyboard() {
        // do things when keyboard is hidden
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
        Log.i(TAG, "onHideKeyboard: ");
    }

    public class SpaceTokenizer implements MultiAutoCompleteTextView.Tokenizer {

        public int findTokenStart(CharSequence text, int cursor) {
            int i = cursor;

            while (i > 0 && text.charAt(i - 1) != ' ') {
                i--;
            }
            while (i < cursor && text.charAt(i) == ' ') {
                i++;
            }

            return i;
        }

        public int findTokenEnd(CharSequence text, int cursor) {
            int i = cursor;
            int len = text.length();

            while (i < len) {
                if (text.charAt(i) == ' ') {
                    return i;
                } else {
                    i++;
                }
            }

            return len;
        }

        public CharSequence terminateToken(CharSequence text) {
            int i = text.length();

            while (i > 0 && text.charAt(i - 1) == ' ') {
                i--;
            }

            if (i > 0 && text.charAt(i - 1) == ' ') {
                return text;
            } else {
                if (text instanceof Spanned) {
                    SpannableString sp = new SpannableString(text + " ");
                    TextUtils.copySpansFrom((Spanned) text, 0, text.length(),
                            Object.class, sp, 0);
                    return sp;
                } else {
                    return text + " ";
                }
            }
        }
    }
}
