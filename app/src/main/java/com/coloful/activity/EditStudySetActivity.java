package com.coloful.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coloful.R;
import com.coloful.dao.QuestionDao;
import com.coloful.dao.QuizDao;
import com.coloful.datalocal.DataLocalManager;
import com.coloful.model.Account;
import com.coloful.model.Quiz;

public class EditStudySetActivity extends AppCompatActivity {
    private Button btnSave;
    private EditText edtEditQuizTitle;
    private Account account;
    private ImageButton buttonAdd;
    private LinearLayout list;
    private ArrayAdapter<String> arrayAdapter;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_study_set);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4257b0")));
        context = this;
        QuizDao quizDao = new QuizDao();
        QuestionDao questionDao = new QuestionDao();

        edtEditQuizTitle = findViewById(R.id.edt_edit_quiz_title);
        list = findViewById(R.id.list);
        btnSave = findViewById(R.id.btn_edit_set);

        buttonAdd = (ImageButton) findViewById(R.id.add);
        account = DataLocalManager.getAccount();
        Intent intent = getIntent();
        int quizId = intent.getIntExtra("quizId", 0);
        Quiz quiz = quizDao.getQuizById(this,quizId);
        questionDao.removeAllQuestionByQuizId(this,quizId);
        edtEditQuizTitle.setText(quiz.getTitle());
        addItem();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (list.getChildCount() < 1) {
                    Toast.makeText(context, "You need at least one question, please check again!", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (int i = 0; i < list.getChildCount(); i++) {
                    if (list.getChildAt(i) instanceof LinearLayoutCompat) {
                        LinearLayoutCompat ll = (LinearLayoutCompat) list.getChildAt(i);
                        EditText edtAnswer = ll.findViewById(R.id.edt_answer);
                        for (int j = 0; j < ll.getChildCount(); j++) {
                            if (ll.getChildAt(j) instanceof EditText) {
                                EditText et = (EditText) ll.getChildAt(j);
                                if (et.getId() == R.id.edt_question) {
                                    quizDao.addQuestion(EditStudySetActivity.this, et.getText().toString().trim(), (long) quizId, edtAnswer.getText().toString().trim());
                                }
                            }
                        }
                    }
                }
                pushNotification(edtEditQuizTitle.getText().toString(), quizId);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem();
            }
        });
    }

    public void addItem() {
        LayoutInflater layoutInflater =
                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.list_view_create_set_item, null);
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 15, 0, 15);
        addView.setLayoutParams(params);

        list.addView(addView);
        Button buttonRemove = (Button) addView.findViewById(R.id.remove);
        final View.OnClickListener thisListener = v -> ((LinearLayout) addView.getParent()).removeView(addView);
        buttonRemove.setOnClickListener(thisListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                MainActivity mainActivity = new MainActivity();
                Intent intent = new Intent(this, mainActivity.getClass());
                intent.putExtra("backScreen", "Home");
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private final String NOTIFICATION_CHANNEL_ID = "EditSuccessStudySet";
    private final String NOTIFICATION_CHANNEL_NAME = "Edit Study Set Notifications";

    private void pushNotification(String data,int quizId) {
        Intent openActivity = new Intent(this, StudySetDetailsActivity.class);
        openActivity.putExtra("screen", "home");
        openActivity.putExtra("quizId", quizId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 10, openActivity,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = builder
                .setSmallIcon(R.drawable.ic_baseline_add_circle_24)
                .setContentTitle("Your quiz " + edtEditQuizTitle.getText() + " is edited successfully")
                .setContentText(data)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(Notification.DEFAULT_LIGHTS)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(data))
                .build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    NOTIFICATION_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(4, notification);
    }
}