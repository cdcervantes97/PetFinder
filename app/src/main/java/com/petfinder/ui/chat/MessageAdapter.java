package com.petfinder.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.petfinder.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private List<ReceivedMessage> messageList = new ArrayList<>();
    private Context c;

    public MessageAdapter(Context c) {
        this.c = c;
    }

    public void addMessage(ReceivedMessage m){
        messageList.add(m);
        notifyItemInserted(messageList.size());
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.card_view_message,parent,false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        holder.getName().setText(messageList.get(position).getName());
        holder.getMessage().setText(messageList.get(position).getMessage());
        if(messageList.get(position).getMessageType().equals("2")){
            holder.getMessageProfilePicture().setVisibility(View.VISIBLE);
            holder.getMessage().setVisibility(View.VISIBLE);
            Glide.with(c).load(messageList.get(position).getPhotoUrl()).into(holder.getMessagePhoto());
        }else if(messageList.get(position).getMessageType().equals("1")){
            holder.getMessagePhoto().setVisibility(View.GONE);
            holder.getMessage().setVisibility(View.VISIBLE);
        }
        if(messageList.get(position).getProfilePicture().isEmpty()){
            holder.getMessageProfilePicture().setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(c).load(messageList.get(position).getProfilePicture()).into(holder.getMessageProfilePicture());
        }
        Long hourCode = messageList.get(position).getTime();
        Date d = new Date(hourCode);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");//a pm o am
        holder.getTime().setText(sdf.format(d));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
