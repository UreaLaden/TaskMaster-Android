package com.urealaden.taskmaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.urealaden.taskmaster.R;
import com.urealaden.taskmaster.models.TaskItem;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter {
    List<TaskItem> taskItemList;

    public TaskRecyclerViewAdapter(List<TaskItem> taskItemList){
        this.taskItemList = taskItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment  = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tasks,parent,false);

        return new TaskItemViewHolder(fragment);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.taskFragmentTextView))
                .setText(taskItemList.get(position).getName() );
    }

    @Override
    public int getItemCount() {
        return taskItemList.size();
    }

    class TaskItemViewHolder extends RecyclerView.ViewHolder{
        public TaskItemViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }
}
