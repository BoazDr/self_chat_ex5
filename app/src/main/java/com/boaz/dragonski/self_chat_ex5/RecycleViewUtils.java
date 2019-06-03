package com.boaz.dragonski.self_chat_ex5;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import android.view.ViewGroup;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


public class RecycleViewUtils {
    static class RowAdapter extends RecyclerView.Adapter<RowHolder> {
        private OnLongClickMessageListener messageListener;
        private List<OneMessage> messages = new ArrayList<>();
        protected RowAdapter(OnLongClickMessageListener msgListener) {
            this.messageListener = msgListener;
        }
        @NonNull
        @Override
        public RowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int idx) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_row_text, viewGroup, false);
            return new RowHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RowHolder rowHolder, int idx) {
            final OneMessage message = messages.get(idx);
            rowHolder.oneRowTextView.setText(message.getText());
            rowHolder.oneRowTextView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    messageListener.onLongClickedOnMessage(message);
                    return false;
                }
            });
        }
        @Override
        public int getItemCount() {
            return messages.size();
        }

        public void setMessages(List<OneMessage> msgs) {
            this.messages = msgs;
            notifyDataSetChanged();
        }
    }
    static class RowHolder extends RecyclerView.ViewHolder{
        public TextView oneRowTextView;
        public RowHolder(@NonNull View view) {
            super(view);
            oneRowTextView = view.findViewById(R.id.one_row_text);
        }
    }
    public interface OnLongClickMessageListener {
        void onLongClickedOnMessage(OneMessage oneMessage);
    }

}
