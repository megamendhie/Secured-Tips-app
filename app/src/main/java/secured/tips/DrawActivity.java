package secured.tips;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datafiles.RecodeAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseRefToday, databaseRefWon, mDatabase;
    FirebaseDatabase database;
    FirebaseUser user;
    String userID="x";
    ActionBar actionBar;
    Button btnSub;
    CardView crdSub, crdEmpty;
    RecyclerView listToday, listWon;
    SectionedRecyclerViewAdapter adapterToday, adapterWon;
    List<DatabaseReference> refToday, refWon;
    LayoutInflater inflater;
    ProgressBar prgToday, prgWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vipp);
        crdSub = findViewById(R.id.crdSub);
        crdEmpty = findViewById(R.id.crdEmpty);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase.getInstance();
        listToday = findViewById(R.id.listToday);
        listWon = findViewById(R.id.listWon);
        prgToday = findViewById(R.id.prgToday);
        prgWon = findViewById(R.id.prgWon);
        btnSub = findViewById(R.id.btnSub); btnSub.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(this);
        listToday.setLayoutManager(linearLayoutManager);
        listWon.setLayoutManager(linearLayoutManager2);

        databaseRefToday = database.getReference().child("recommendedGames").child("Draws").child("main");
        databaseRefToday.keepSynced(true);
        databaseRefWon = database.getReference().child("recommendedGames").child("Draws").child("won");
        databaseRefWon.keepSynced(true);

        inflater = getLayoutInflater();

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            userID = user.getUid();
            mDatabase = FirebaseDatabase.getInstance("https://d-bet-98dcf-e81ed.firebaseio.com/").getReference().child("Users").child(userID);
            mDatabase.keepSynced(true);
        }

        adapterWon = new SectionedRecyclerViewAdapter(){
            @Override
            public long getItemId(int position){return position;}

            @Override
            public void setHasStableIds(boolean hasStableIds){
                super.setHasStableIds(hasStableIds);
            }
        };

        adapterToday = new SectionedRecyclerViewAdapter(){
            @Override
            public long getItemId(int position){return position;}

            @Override
            public void setHasStableIds(boolean hasStableIds){
                super.setHasStableIds(hasStableIds);
            }
        };
        setLayout();
    }

    public void loadTips(){
        crdSub.setVisibility(View.GONE);
        listToday.setVisibility(View.VISIBLE);
        databaseRefToday.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapterToday.removeAllSections();
                refToday = new ArrayList<DatabaseReference>();
                if(!dataSnapshot.hasChildren()){
                    crdEmpty.setVisibility(View.VISIBLE);
                    prgToday.setVisibility(View.GONE);
                    return;
                }
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String ref = snapshot.getValue(String.class);
                    refToday.add(FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReferenceFromUrl(ref));
                }
                Collections.reverse(refToday);
                adapterToday.addSection(new RecodeAdapter(refToday, DrawActivity.this, DrawActivity.this, userID));
                adapterToday.notifyDataSetChanged();
                listToday.setAdapter(adapterToday);
                prgToday.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseRefWon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapterWon.removeAllSections();
                refWon = new ArrayList<DatabaseReference>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String ref = snapshot.getValue(String.class);
                    refWon.add(FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReferenceFromUrl(ref));
                }
                Collections.reverse(refWon);
                adapterWon.addSection(new RecodeAdapter(refWon, DrawActivity.this, DrawActivity.this, userID));
                adapterWon.notifyDataSetChanged();
                listWon.setAdapter(adapterWon);
                prgWon.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadTipsZero(){
        databaseRefWon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapterWon.removeAllSections();
                refWon = new ArrayList<DatabaseReference>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    String ref = snapshot.getValue(String.class);
                    refWon.add(FirebaseDatabase.getInstance("https://d-bet-98dcf-e1240.firebaseio.com/").getReferenceFromUrl(ref));
                }
                Collections.reverse(refWon);
                adapterWon.addSection(new RecodeAdapter(refWon, DrawActivity.this, DrawActivity.this, userID));
                adapterWon.notifyDataSetChanged();
                listWon.setAdapter(adapterWon);
                prgWon.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draws_menu, menu);
        return true;
    }

    public void setLayout() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null) {
            loadTips();
            return;
        }
        else {
            Toast.makeText(getApplicationContext(), "You haven't logged in", Toast.LENGTH_SHORT).show();
            listToday.setVisibility(View.GONE);
            prgToday.setVisibility(View.GONE);
            loadTipsZero();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSub:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("SENDER", 2);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== R.id.mnVip)
            startActivity(new Intent(this, PremiumActivity.class));
        finish();
        return true;
    }
}
