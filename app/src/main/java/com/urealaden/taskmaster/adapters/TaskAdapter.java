package com.urealaden.taskmaster.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Task;
import com.urealaden.taskmaster.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    List<Task> tasks;

    public TaskAdapter(List<Task> tasks){
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View fragment = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tasks,parent,false);
        TaskViewHolder vh = new TaskViewHolder(fragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = tasks.get(position);

        ((TextView)holder.itemView.findViewById(R.id.taskTitle)).setText(holder.task.getName());
        ((TextView)holder.itemView.findViewById(R.id.taskDescription)).setText(holder.task.getDescription());

    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        public Task task;
        public TaskViewHolder(@NonNull View itemView){super(itemView);}
    }
}
