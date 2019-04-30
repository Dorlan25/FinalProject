package com.example.b_quest;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//this adapter takes information form the ChatActivity class in order to display messages on the screen
//and its started from the ChatActivity class
public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //declaring the array that contain the information to be displayed
    private ArrayList<Message> messages = new ArrayList<>();

    //this arrayList contains the name of the user writing the message, this will help at the time of choosing how to display the messages
    private String userName;

    //getting the context
    private Context context;

    //constructor
    public ChatRecyclerViewAdapter(ArrayList<Message> messages, String userName, ChatActivity context) {
        this.messages = messages;
        this.userName = userName;
        this.context = context;

    }

    //this method check who is the person writing the message and base on this choose the right layout to be displayed
    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getUser().equals(userName)) {
            return R.layout.my_message;
        } else {
            return R.layout.message;
        }
    }

    //the onCreateViewHolder builds the place where each individual message will be shown based on who is writing the message
    //the switch statement is to choose what layout is to be used, the my_message layout is for "your own" messages and the message
    //message layout is for other users
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view;

        switch (viewType) {
            case R.layout.my_message:
                view = LayoutInflater.from(context).inflate(R.layout.my_message, parent, false);
                holder = new MyViewHolder(view);
                break;

            case R.layout.message:
                view = LayoutInflater.from(context).inflate(R.layout.message, parent, false);
                holder = new SecondViewHolder(view);
                break;

            default:
                view = LayoutInflater.from(context).inflate(R.layout.my_message, parent, false);
                holder = new MyViewHolder(view);
                break;
        }
        return holder;
    }

    //the onBindViewHolder methods add the information to the layouts created by the onCreateViewHolder method
    //again based on who is writing th message
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).message.setText(messages.get(position).getMessage());
        } else if (holder instanceof SecondViewHolder) {
            ((SecondViewHolder) holder).name.setText(messages.get(position).getUser());
            ((SecondViewHolder) holder).message.setText(messages.get(position).getMessage());
        }

    }

    //this method defines how many times the adapter will be creating layouts
    @Override
    public int getItemCount() {
        return messages.size();
    }


    //this classes define the information to be attach to the holder, the holder will be an instance of one of this classes
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
        }
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;

        public SecondViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user);
            message = itemView.findViewById(R.id.message);

        }
    }
}

