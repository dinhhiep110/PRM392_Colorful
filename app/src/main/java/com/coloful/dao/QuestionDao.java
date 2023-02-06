package com.coloful.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coloful.model.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {

    DBHelper db;
    SQLiteDatabase sqLiteDatabase;

    public List<Question> getQuestionForQuiz(Context context, Integer quizId) {
        List<Question> questionList = new ArrayList<>();
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from question where quiz_id =?", new String[]{quizId.toString()});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question q = new Question();
            q.setId(cursor.getInt(0));
            q.setContent(cursor.getString(1));
            Cursor cs = sqLiteDatabase.rawQuery("select * from answer where question_id = ?", new String[]{q.getId().toString()});
            cs.moveToFirst();
            q.setAnswer(cs.getString(1));

            questionList.add(q);
            cursor.moveToNext();
        }
        return questionList;
    }
}
