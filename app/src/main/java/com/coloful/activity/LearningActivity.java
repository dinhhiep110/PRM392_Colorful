package com.coloful.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coloful.R;
import com.coloful.dao.DBHelper;
import com.coloful.dao.QuestionDao;
import com.coloful.dao.QuizDao;
import com.coloful.model.Answer;
import com.coloful.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LearningActivity extends AppCompatActivity {
    int quizId;
    QuizDao myDB;
    Button ansBtn1, ansBtn2, ansBtn3, ansBtn4;
    TextView countLabel, questionLabel;
    String rightAnswer;
    int rightAnswerCount =0;
    int quizCount = 1;
    static final private int QUIZ_COUNT = 5;
    ArrayList<ArrayList<String>> quizArray = new ArrayList<>();

    String quizData[][] = {
            {"hoho", "VN", "HQ","TQ", "NB"},
            {"A1", "Q1", "Q1","Q3", "Q4"},
            {"A2", "Q5", "Q6","Q7", "Q8"},
            {"A3", "B1", "B2","B3", "B4"},
            {"A4", "B5B", "B6","B7", "B7"},
    };

    List<Question> quesList;
    List<Answer> ansList;

    Question currentQ;

    int qid=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4257b0")));
//        quizId = getIntent().getIntExtra("quizId", 0);

//        countLabel = findViewById(R.id.countLabel);

        DBHelper db = new DBHelper(this);
        quesList = db.getAllQuestions();
        ansList = db.getTopThreeAnswer();
        currentQ = quesList.get(qid);
        questionLabel = findViewById(R.id.questionLabel);
        ansBtn1 = findViewById(R.id.answerBtn1);
        ansBtn2 = findViewById(R.id.answerBtn2);
        ansBtn3 = findViewById(R.id.answerBtn3);
        ansBtn4 = findViewById(R.id.answerBtn4);

        setQuestionView();

//        for (int i = 0; i<quizData.length; i++){
//            ArrayList<String> tmpArray = new ArrayList<>();
//            tmpArray.add(quizData[i][0]); //country
//            tmpArray.add(quizData[i][1]);//right answer
//            tmpArray.add(quizData[i][2]);//choice1
//            tmpArray.add(quizData[i][3]);//choice 2
//            tmpArray.add(quizData[i][4]);// choice 3
//            quizArray.add(tmpArray);
//        }
//        showNextQuestion();
    }

    private void setQuestionView()
    {
        questionLabel.setText(currentQ.getContent());
        ansBtn1.setText(currentQ.getAnswer());
        ansBtn2.setText(ansList.get(0).getContent());
        ansBtn3.setText(ansList.get(1).getContent());
        ansBtn4.setText(ansList.get(2).getContent());

        qid++;
    }

    public void checkAnswer(View view) {

        // Get pushed button.
        Button answerBtn = findViewById(view.getId());
        String btnText = answerBtn.getText().toString();

        String alertTitle;

        if (btnText.equals(rightAnswer)) {
            // Correct
            alertTitle = "Correct!";
            rightAnswerCount++;

        } else {
            alertTitle = "Wrong...";
        }

        // Create AlertDialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(alertTitle);
        builder.setMessage("Answer : " + rightAnswer);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (quizCount == QUIZ_COUNT) {
                    // Show Result.
                    Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                    intent.putExtra("RIGHT_ANSWER_COUNT", rightAnswerCount);
                    startActivity(intent);
                } else {
                    quizCount++;
                    showNextQuestion();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void showNextQuestion(){
        //update quizCountLabel
        countLabel.setText("Q" + quizCount);

        //Random number between 0 => 14
        Random random = new Random();
        int randomNum = random.nextInt(quizArray.size());
        // Pick one quiz set.
        ArrayList<String> quiz = quizArray.get(randomNum);

        // Set question and right answer.
        // Array format: {"Country", "Right Answer", "Choice1", "Choice2", "Choice3"}
        questionLabel.setText(quiz.get(0));
        rightAnswer = quiz.get(1);

        // Remove "Country" from quiz and Shuffle choices.
        quiz.remove(0);
        Collections.shuffle(quiz);

        // Set choices.
        ansBtn1.setText(quiz.get(0));
        ansBtn2.setText(quiz.get(1));
        ansBtn3.setText(quiz.get(2));
        ansBtn4.setText(quiz.get(3));

        // Remove this quiz from quizArray.
        quizArray.remove(randomNum);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}