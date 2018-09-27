package com.example.svgk.mnnitacademicportal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.AdminViewHolder> {

    private Context mContext;
    private List<MultipleUsers> userList;
    private List<DownloadFiles> files;

    public CardViewAdapter(Context mCtx, List<MultipleUsers> userList, List<DownloadFiles> files) {
        this.mContext = mCtx;
        this.userList = userList;
        this.files = files;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if (mContext == DownloadActivity.DOWNLOAD_ACTIVITY_CONTEXT) {
            view = inflater.inflate(R.layout.download_files_list, parent, false);
        } else {
            view = inflater.inflate(R.layout.students_list_in_admin, parent, false);
        }
        return new AdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {

        if (mContext == DownloadActivity.DOWNLOAD_ACTIVITY_CONTEXT) {
            DownloadFiles downloadFiles = files.get(position);
            holder.file_name.setText(downloadFiles.getName());

        } else {
            MultipleUsers user = userList.get(position);

            holder.name.setText("Name : " + user.getNAME());
            holder.regd_no.setText("Regd.No : " + user.getREGD_NO());
            holder.mail_id.setText("Email : " + user.getEMAIL());
            holder.branch.setText("Branch : " + user.getBRANCH());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mContext == DownloadActivity.DOWNLOAD_ACTIVITY_CONTEXT) {
                    DownloadFiles downloadFiles = files.get(position);
                    getPdf(downloadFiles.getUrl());
                } else if (mContext == EmailActivity.EMAIL_ACTIVITY_CONTEXT) {
                    EmailActivity emailActivity = (EmailActivity) mContext;
                    emailActivity.recyclerView.setAdapter(null);
                    emailActivity.emailMessge.setVisibility(View.VISIBLE);
                    emailActivity.emailSubject.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    private void getPdf(String pdfUrl) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(pdfUrl));
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        if (mContext == DownloadActivity.DOWNLOAD_ACTIVITY_CONTEXT) {
            return files.size();
        } else {
            return userList.size();
        }
    }

    class AdminViewHolder extends RecyclerView.ViewHolder {

        TextView name, regd_no, branch, mail_id, file_name;

        public AdminViewHolder(View itemView) {
            super(itemView);
            if (mContext == DownloadActivity.DOWNLOAD_ACTIVITY_CONTEXT) {
                file_name = itemView.findViewById(R.id.file_name);
            } else {
                name = itemView.findViewById(R.id.student_name_admin);
                regd_no = itemView.findViewById(R.id.student_registration_no_admin);
                branch = itemView.findViewById(R.id.student_branch_admin);
                mail_id = itemView.findViewById(R.id.student_email_admin);
            }

        }
    }

}
