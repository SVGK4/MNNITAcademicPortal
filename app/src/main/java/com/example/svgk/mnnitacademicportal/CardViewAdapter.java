package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.AdminViewHolder> {

    private Context mContext;
    private List<MultipleUsers> userList ;

    public CardViewAdapter(Context mCtx, List<MultipleUsers> userList){
        this.mContext = mCtx;
        this.userList = userList;
    }


    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.students_list_in_admin,parent,false);
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {
        MultipleUsers user = userList.get(position);

        holder.name.setText("Name : "+user.getNAME());
        holder.regd_no.setText("Regd.No : " + user.getREGD_NO());
        holder.mail_id.setText("Email : " + user.getEMAIL());
        holder.branch.setText("Branch : " + user.getBRANCH());


        if(mContext == EmailActivity.EMAIL_ACTIVITY_CONTEXT){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EmailActivity emailActivity = (EmailActivity)mContext;
                    emailActivity.recyclerView.setAdapter(null);
                    emailActivity.emailMessge.setVisibility(View.VISIBLE);
                    emailActivity.emailSubject.setVisibility(View.VISIBLE);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class AdminViewHolder extends RecyclerView.ViewHolder{

        TextView name,regd_no,branch,mail_id;

        public AdminViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.student_name_admin);
            regd_no = itemView.findViewById(R.id.student_registration_no_admin);
            branch = itemView.findViewById(R.id.student_branch_admin);
            mail_id = itemView.findViewById(R.id.student_email_admin);

        }
    }

}
