package ru.ponomarenko.hwbasics2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.ponomarenko.hwbasics2.R;
import ru.ponomarenko.hwbasics2.model.Note;
import ru.ponomarenko.hwbasics2.service.MainService;
import ru.ponomarenko.hwbasics2.service.MyPrimitiveItemClick;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    ArrayList<Note> dataSource;
    private MyPrimitiveItemClick itemClickListener;
    private MyPrimitiveItemClick itemLongClickListener;

    public NoteListAdapter(MyPrimitiveItemClick itemClickListener, MyPrimitiveItemClick itemLongClickListener) {

        dataSource = MainService.getInstance().getNoteRepo().getData();
        this.itemClickListener = itemClickListener;
        this.itemLongClickListener = itemLongClickListener;

    }

    @NonNull
    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_notes_item, parent, false);

        return new NoteListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.ViewHolder holder, int position) {
        holder.fill(position);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvCreateDate;
        private TextView tvUpdateDate;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_name);
            tvCreateDate = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_create_date);
            tvUpdateDate = (TextView) itemView.findViewById(R.id.layout_notes_item_tv_update_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null)
                        itemClickListener.onItemClick(itemView, position);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (itemLongClickListener != null) {
                        itemLongClickListener.onItemClick(itemView, position);
                        return true;
                    }
                    return false;
                }
            });

        }

        /**
         * Вызывается из onBindViewHolder
         *
         * @param position
         */
        public void fill(int position) {

            Note item = dataSource.get(position);

            tvName.setText(item.getName());
            tvCreateDate.setText(item.getCreationDate().toString());

            if (item.getUpdateDate() == null) {
                tvUpdateDate.setText("");
            } else {
                tvUpdateDate.setText(item.getUpdateDate().toString());
            }
        }
    }
}
