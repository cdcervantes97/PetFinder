package com.petfinder.ui.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.petfinder.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    private TextView name;
    private TextView message;
    private TextView time;
    private ImageView messageProfilePicture;
    private ImageView messagePhoto;

    public MessageHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.message_name);
        message = (TextView) itemView.findViewById(R.id.message_message);
        time = (TextView) itemView.findViewById(R.id.message_time);
        messageProfilePicture = (ImageView) itemView.findViewById(R.id.message_profile_picture);
        messagePhoto = (ImageView) itemView.findViewById(R.id.message_photo);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }

    public TextView getMessage() {
        return message;
    }

    public void setMessage(TextView message) {
        this.message = message;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public ImageView getMessageProfilePicture() {
        return messageProfilePicture;
    }

    public void setMessageProfilePicture(ImageView messageProfilePicture) {
        this.messageProfilePicture = messageProfilePicture;
    }

    public ImageView getMessagePhoto() {
        return messagePhoto;
    }

    public void setMessagePhoto(ImageView messagePhoto) {
        this.messagePhoto = messagePhoto;
    }
}
