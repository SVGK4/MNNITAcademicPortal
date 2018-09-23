package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdminListAdapter extends RecyclerView.Adapter<AdminListAdapter.AdminViewHolder> {

    private Context mContext;
    private List<AdminUser> userList ;

    public AdminListAdapter(Context mCtx,List<AdminUser> userList){
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
        AdminUser user = userList.get(position);

        holder.name.setText("Name : "+user.getNAME());
        holder.regd_no.setText("Regd.No : " + user.getREGD_NO());
        holder.mail_id.setText("Email : " + user.getEMAIL());
        holder.branch.setText("Branch : " + user.getBRANCH());
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
