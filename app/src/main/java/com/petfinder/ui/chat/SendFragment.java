package com.petfinder.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import com.petfinder.R;

public class SendFragment extends Fragment {

    private ImageView profilePicture;
    private TextView name;
    private RecyclerView messageHistory;
    private EditText messageInput;
    private Button sendButton;
    private MessageAdapter adapter;
    private ImageButton galleryButton;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;
    private static final int PHOTO_PROFILE = 2;
    private String profilePictureCadena;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_chat, container, false);

        profilePicture = (ImageView) root.findViewById(R.id.profile_picture);
        name = (TextView) root.findViewById(R.id.name);
        messageHistory = (RecyclerView) root.findViewById(R.id.message_history);
        messageInput = (EditText) root.findViewById(R.id.message_input);
        sendButton = (Button) root.findViewById(R.id.send_button);
        galleryButton = (ImageButton) root.findViewById(R.id.gallery_button);
        profilePictureCadena = "";

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("chat"); // Chat Room (name)
        storage = FirebaseStorage.getInstance();

        adapter = new MessageAdapter(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        messageHistory.setLayoutManager(l);
        messageHistory.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.push().setValue(new MessageSend(messageInput.getText().toString(),name.getText().toString(),profilePictureCadena,"1", ServerValue.TIMESTAMP));
                messageInput.setText("");
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Select a Picture"),PHOTO_SEND);
            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Select a Picture"),PHOTO_PROFILE);
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ReceivedMessage m = dataSnapshot.getValue(ReceivedMessage.class);
                Log.d("Message Received", dataSnapshot.toString());
                adapter.addMessage(m);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return root;
    }

    private void setScrollbar(){
        messageHistory.scrollToPosition(adapter.getItemCount()-1);
    }
}