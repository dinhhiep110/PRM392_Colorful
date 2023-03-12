package com.coloful.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.coloful.constant.Constant;
import com.coloful.model.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerDao {

    private DBHelper db;

    private SQLiteDatabase sqLiteDatabase;

    public List<Answer> getAllAnswer(Context context) {
        List<Answer> answers = new ArrayList<>();
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        String selectQuery = "SELECT Distinct * FROM " + Constant.Answer.TABLE_NAME.getValue();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Answer ans = new Answer();
                ans.setContent(cursor.getString(1));
                answers.add(ans);
            } while (cursor.moveToNext());
        }
        // return quest list
        return answers;
    }
}
