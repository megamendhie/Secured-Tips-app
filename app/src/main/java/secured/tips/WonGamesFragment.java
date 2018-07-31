package secured.tips;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import datafiles.Cache;
import datafiles.ChatMessage;
import datafiles.PostAdapter;

public class WonGamesFragment extends Fragment {
    String userID, myUserID;
    private final String TAG = "MyChatApp";
    private RecyclerView recyclerView;
    private DatabaseReference mRef;
    TextView txtNotice;
    Cache cache = new Cache();
    List<DatabaseReference> Refs = new ArrayList<DatabaseReference>();
    List<ChatMessage> chatMessages;
    private PostAdapter adapter;
    FirebaseAuth auth;


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
        View rootView=inflater.inflate(R.layout.fragment_recentpost, container, false);
        recyclerView = rootView.findViewById(R.id.rvRecent);

        txtNotice = rootView.findViewById(R.id.txtNotice);
        chatMessages = new ArrayList<ChatMessage>();
        userID = cache.getUserID();
        mRef = FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReference().child("chatrooms");
        auth = FirebaseAuth.getInstance();
        myUserID = auth.getUid().toString();

        mRef.keepSynced(true);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    snapshot.getRef().orderByChild("messageStatus").equalTo("1_"+userID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot snap : dataSnapshot.getChildren()){
                                chatMessages.add(snap.getValue(ChatMessage.class));
                                Refs.add(snap.getRef());
                                adapter.setHasStableIds(true);
                                adapter = new PostAdapter(getContext(), chatMessages, myUserID, Refs);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setAdapter(adapter);
                                if(adapter.getItemCount()==0){
                                    txtNotice.setVisibility(View.VISIBLE);
                                }
                                else {
                                    txtNotice.setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return rootView;
    }

}
