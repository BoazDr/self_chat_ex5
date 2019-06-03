package com.boaz.dragonski.self_chat_ex5;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.view.ViewGroup;


public class MessagesInfo extends Fragment {
    public interface listenerToDelete {
        void onDelete(OneMessage oneMessage);
    }
    private listenerToDelete deleteListener;
    public MessagesInfo() {
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle Args = getArguments();
        if (Args == null) return;
        super.onViewCreated(view, savedInstanceState);
        final OneMessage oneMessage = Args.getParcelable("message");
        if (oneMessage == null) return;
        Button delete = view.findViewById(R.id.permanent_delete);
        TextView phone = view.findViewById(R.id.phone);
        TextView timeStamp = view.findViewById(R.id.timeStamp);
        phone.setText(oneMessage.getDeviceCreatedMessage());
        timeStamp.setText(oneMessage.getTimestamp());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onDelete(oneMessage);
            }
        });
    }
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.message_info, viewGroup, false);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        deleteListener = (listenerToDelete) context;
    }
}
