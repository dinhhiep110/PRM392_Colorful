package com.coloful.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.coloful.R;
import com.coloful.dao.AnswerDao;
import com.coloful.dao.QuestionDao;
import com.coloful.model.Answer;
import com.coloful.model.Question;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LearningActivity extends AppCompatActivity {
    private int quizCount = 1;
    private int qid = 0;
    private int QUIZ_COUNT = 5;
    private List<Question> quesList;
    private Question currentQ;
    private AnswerDao answerDao;
    private Button ansBtn1, ansBtn2, ansBtn3, ansBtn4;
    private TextView countLabel, questionLabel;
    private String rightAnswer;
    private int rightAnswerCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_learning);
        Intent intent = getIntent();
        int quizId = intent.getIntExtra("quizId", 0);
        QuestionDao questionDao = new QuestionDao();
        answerDao = new AnswerDao();
        countLabel = findViewById(R.id.countLabel);
        questionLabel = findViewById(R.id.questionLabel);
        ansBtn1 = findViewById(R.id.answerBtn1);
        ansBtn2 = findViewById(R.id.answerBtn2);
        ansBtn3 = findViewById(R.id.answerBtn3);
        ansBtn4 = findViewById(R.id.answerBtn4);
        quesList = questionDao.getQuestionForQuiz(this, quizId);
        setQuizCount(quesList);
        setQuestionView(qid);
    }

    @SuppressLint("SetTextI18n")
    private void setQuestionView(int qid) {
        countLabel.setText("Q" + quizCount);
        currentQ = quesList.get(qid);
        List<String> ansList = randomAllAnswer().subList(0, 3).stream().map(Answer::getContent).collect(Collectors.toList());
        ansList.add(currentQ.getAnswer());
        Collections.shuffle(ansList);
        questionLabel.setText(currentQ.getContent());
        ansBtn1.setText(ansList.get(0));
        ansBtn2.setText(ansList.get(1));
        ansBtn3.setText(ansList.get(2));
        ansBtn4.setText(ansList.get(3));
        setRightAnswer();
    }

    private void setRightAnswer() {
        rightAnswer = currentQ.getAnswer();
    }

    private void setQuizCount(List<Question> quesList) {
        QUIZ_COUNT = quesList.size();
    }

    private List<Answer> randomAllAnswer() {
        List<Answer> allAnswer = answerDao.getAllAnswer(this);
        Collections.shuffle(allAnswer);
        return allAnswer;
    }

    public void checkAnswer(View view) {

        // Get pushed button.
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // Correct
            alertTitle = "Correct Answer ^^";
            rightAnswerCount++;

        } else {
            alertTitle = "Wrong Answer !";
        }

        // Create AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("\bAnswer: \n" + rightAnswer);
        builder.setPositiveButton("OK", (dialogInterface, i) -> {
            if (quizCount == QUIZ_COUNT) {
                // Show Result.
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                intent.putExtra("TOTAL_SCORE", quesList.size());
                startActivity(intent);
            } else {
                qid++;
                quizCount++;
                showNextQuestion(qid);
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void showNextQuestion(int qid) {
        setQuestionView(qid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}