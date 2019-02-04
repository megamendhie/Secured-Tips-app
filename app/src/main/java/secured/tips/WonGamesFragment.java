package secured.tips;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datafiles.Cache;
import datafiles.ChatMessage;
import datafiles.PostAdapt;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class WonGamesFragment extends Fragment {
    String userID, myUserID;
    private final String TAG = "MyChatApp";
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    Cache cache = new Cache();
    List<ChatMessage> chatMessages;
    List<DatabaseReference> Refs;
    SectionedRecyclerViewAdapter adapt;


    public WonGamesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_won_games, container, false);
        recyclerView = rootView.findViewById(R.id.rvRecent);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //UserID from firebase
        userID = cache.getUserID();
        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms");
        myUserID = FirebaseAuth.getInstance().getUid();
        adapt = new SectionedRecyclerViewAdapter(){
            @Override
            public long getItemId(int position){
                return position;
            }

            @Override
            public void setHasStableIds(boolean hasStableIds) {
                super.setHasStableIds(hasStableIds);
            }
        };

        mRef.keepSynced(true);
        loadPosts();
        return rootView;
    }

    public void loadPosts(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    final String roomName = snapshot.getKey();
                    loadMessages(snapshot, roomName);
                }
                recyclerView.setAdapter(adapt);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadMessages(DataSnapshot snapshot, final String roomName){
        snapshot.getRef().orderByChild("messageStatus").equalTo("1_"+userID).limitToLast(30)
                .addValueEventListener(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot postSnapshot) {
                        if(postSnapshot.hasChildren()) {
                            chatMessages = new ArrayList<ChatMessage>();
                            Refs = new ArrayList<DatabaseReference>();
                            for (DataSnapshot snapshot : postSnapshot.getChildren()) {
                                ChatMessage message = snapshot.getValue(ChatMessage.class);
                                DatabaseReference ref = snapshot.getRef();
                                Refs.add(ref);
                                chatMessages.add(message);
                            }
                            Collections.reverse(Refs);
                            Collections.reverse(chatMessages);
                            adapt.addSection(roomName,new PostAdapt(getRoomName(roomName),
                                    chatMessages, getContext(), getActivity(), myUserID, Refs));
                            adapt.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private String getRoomName(final String roomName){
        switch (roomName){
            case "room_1":
                return "General Discussion";
            case "room_2":
                return "3-10 odds";
            case "room_3":
                return "11-50 odds";
            case "room_4":
                return "51-100 odds";
            case "room_5":
                return "151-350 odds";
            case "room_6":
                return "351+ odds";
            case "room_7":
                return "Draws";
        }


        return null;
    }
}

