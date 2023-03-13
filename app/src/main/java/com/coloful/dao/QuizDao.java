package com.coloful.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.coloful.constant.Constant;
import com.coloful.model.Account;
import com.coloful.model.Question;
import com.coloful.model.Quiz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QuizDao {

    private final AccountDao accountDao;
    private DBHelper db;
    private SQLiteDatabase sqLiteDatabase;

    public QuizDao() {
        accountDao = new AccountDao();
    }

    public static List<Quiz> init() {
        List<Quiz> quizList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Quiz q = new Quiz();
            q.setId(i + 1);
            q.setTitle("ABC " + (i + 1));

            Account account = new Account();
            account.setUsername("Author " + (i + 1));
            q.setAuthor(account);
            List<Question> questionList = new ArrayList<>();

            for (int j = 0; j < 5; j++) {
                Question question = new Question();
                question.setAnswer("abcs " + j);
                question.setContent("hom nay ngay gi " + j + "?");
                questionList.add(question);
            }
            q.setQuestionList(questionList);
            quizList.add(q);
        }
        return quizList;
    }

    public static void initQuizData(Context context) {
        List<Quiz> quizList = new ArrayList<>();
        Quiz q = new Quiz();

        q.setTitle("Adjectives");
        Account account = new Account();
        account.setId(2);
        q.setAuthor(account);
        List<Question> questionList = new ArrayList<>();

        questionList.add(new Question("good", "acceptable\n" +
                "Eg: Clearly we need to come to an arrangement that is acceptable to both parties.\n" +
                "\n" +
                "satisfactory\n" +
                "Eg: We hope very much to find a satisfactory solution to the problem."));

        questionList.add(new Question("very bad", "abominable\n" +
                "Eg: The weather's been abominable all week.\n" +
                "\n" +
                "deplorable\n" +
                "Eg: I thought his behaviour was absolutely deplorable."));
        questionList.add(new Question("harmful", "adverse\n" +
                "Eg: So far the drug is thought not to have any adverse effects.\n" +
                "\n" +
                "detrimental\n" +
                "Eg: These chemicals have a detrimental effect impact on the environment."));
        questionList.add(new Question("today", "advantageous\n" +
                "Eg: The lower tax rate is particularly advantageous to poorer families.\n" +
                "\n" +
                "effective\n" +
                "\n" +
                "beneficial"));
        questionList.add(new Question("abc", "congested\n" +
                "\n" +
                "crammed\n" +
                "Eg: a crammed train/room"));
        q.setQuestionList(questionList);
        quizList.add(q);

        q = new Quiz();
        q.setTitle("Outsiders Vocab");
        account = new Account();
        account.setId(2);
        q.setAuthor(account);
        questionList = new ArrayList<>();

        questionList.add(new Question("Asset", "In a very cautious, wary, or tentative way"));

        questionList.add(new Question("Credible", "Easy to believe"));
        questionList.add(new Question("Contracted", "Shrunken, wrinkled, restricted, tightened, drawn together"));
        questionList.add(new Question("Nonchalant", "Calm, casual, and unconcerned about things"));
        questionList.add(new Question("Incredulous", "Unable or unwilling to believe something"));
        questionList.add(new Question("Smolder", "To burn slowly, and gently, usually with some smoke, but without flame, also feeling anger, but keeping it under control"));
        q.setQuestionList(questionList);
        quizList.add(q);

        q = new Quiz();
        q.setTitle("The Book Thief Vocabulary");
        account = new Account();
        account.setId(3);
        q.setAuthor(account);
        questionList = new ArrayList<>();

        questionList.add(new Question("malicious", "harmful, having ill intentions"));

        questionList.add(new Question("versatility", "flexibility, ability to do multiple types of things"));
        questionList.add(new Question("resonate", "evoke or suggest images, memories, and emotions"));
        questionList.add(new Question("disposition", "a person's inherent qualities of mind and character"));
        questionList.add(new Question("futile", "incapable of producing any useful result; pointless"));
        questionList.add(new Question("cynicism", "an inclination to believe that people are motivated purely by self interest; skepticism"));
        questionList.add(new Question("cynicism", "an inclination to believe that people are motivated purely by self interest; skepticism"));
        questionList.add(new Question("cynicism", "an inclination to believe that people are motivated purely by self interest; skepticism"));
        questionList.add(new Question("cynicism", "an inclination to believe that people are motivated purely by self interest; skepticism"));
        q.setQuestionList(questionList);
        quizList.add(q);

        q = new Quiz();
        q.setTitle("Feelings and emotions");
        account = new Account();
        account.setId(3);
        q.setAuthor(account);
        questionList = new ArrayList<>();

        questionList.add(new Question("malicious", "harmful, having ill intentions"));

        questionList.add(new Question("versatility", "flexibility, ability to do multiple types of things"));
        questionList.add(new Question("resonate", "evoke or suggest images, memories, and emotions"));
        questionList.add(new Question("disposition", "a person's inherent qualities of mind and character"));
        questionList.add(new Question("futile", "incapable of producing any useful result; pointless"));
        questionList.add(new Question("cynicism", "an inclination to believe that people are motivated purely by self interest; skepticism"));
        q.setQuestionList(questionList);
        quizList.add(q);

        q = new Quiz();
        q.setTitle("Outsiders Chapters 4-6 Vocab Terms");
        account = new Account();
        account.setId(4);
        q.setAuthor(account);
        questionList = new ArrayList<>();

        questionList.add(new Question("unceasingly", "continuously; not coming to an end"));

        questionList.add(new Question("apprehensive", "anxious or fearful that something bad or unpleasant will happen"));
        questionList.add(new Question("contemptuously", "scornfully; feeling that someone or something is worthless or beneath consideration"));
        questionList.add(new Question("ruefully", "regretfully; expressing sorrow or regret in a slightly humorous way"));
        questionList.add(new Question("reformatory", "an institution where young kids are sent as an alternative to prison"));
        questionList.add(new Question("premonition", "a strong feeling that something is about to happen, especially something unpleasant"));
        q.setQuestionList(questionList);
        quizList.add(q);


        QuizDao dao = new QuizDao();
        quizList.forEach(quiz -> {
            dao.insertQuiz(context, quiz);
        });
    }

    public List<Quiz> allQuiz(Context context, Integer accountID) {
        List<Quiz> quizList = new ArrayList<>();
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM quiz WHERE author = ?;", new String[]{accountID.toString()});
        cursor.moveToFirst();
        QuestionDao questionDao = new QuestionDao();
        while (!cursor.isAfterLast()) {
            Quiz q = new Quiz();
            q.setId(cursor.getInt(0));
            q.setTitle(cursor.getString(1));
            Account account = accountDao.getAccountForQuiz(context, cursor.getInt(2));
            q.setAuthor(account);
            q.setQuestionList(questionDao.getQuestionForQuiz(context, q.getId()));
            quizList.add(q);
            cursor.moveToNext();
        }
        return quizList;
    }

    public Quiz getQuizById(Context context, int id) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM quiz WHERE id = ?;", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        QuestionDao questionDao = new QuestionDao();
        if (cursor.getCount() > 0) {
            Quiz q = new Quiz();
            q.setId(cursor.getInt(0));
            q.setTitle(cursor.getString(1));
            q.setQuestionList(questionDao.getQuestionForQuiz(context, q.getId()));
            Account account = accountDao.getAccountForQuiz(context, cursor.getInt(2));
            q.setAuthor(account);
            return q;
        }

        return null;
    }

    public Long addQuiz(Context context, String author, String title) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Constant.Quiz.TITLE.getValue(), title);
        cv.put(Constant.Quiz.AUTHOR.getValue(), author);
        long result = sqLiteDatabase.insert(Constant.Quiz.TABLE_NAME.getValue(), null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added success", Toast.LENGTH_SHORT).show();
            return result;
        }
        return null;
    }

    public void addQuestion(Context context, String question, Long quiz_id, String answer) {
        ContentValues values = new ContentValues();
        values.put(Constant.Question.CONTENT.getValue(), question);
        values.put(Constant.Question.QUIZ_ID.getValue(), quiz_id);
        long q_id = sqLiteDatabase.insert(Constant.Question.TABLE_NAME.getValue(), null, values);
        if (q_id == -1) {
            Toast.makeText(context, "Add question failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added success", Toast.LENGTH_SHORT).show();
            addAnswer(context, answer, q_id);
        }
    }

    public void addAnswer(Context context, String answer, Long ques_id) {
        ContentValues values = new ContentValues();
        values.put(Constant.Answer.CONTENT.getValue(), answer);
        values.put(Constant.Answer.QUES_ID.getValue(), ques_id);
        long q_id = sqLiteDatabase.insert(Constant.Answer.TABLE_NAME.getValue(), null, values);
        if (q_id == -1) {
            Toast.makeText(context, "Add answer failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added answer success", Toast.LENGTH_SHORT).show();
        }
    }

    public void joinQuiz(Context context, Integer quizId, Integer accountID) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getWritableDatabase();
        String lastTimeJoin = Calendar.getInstance().getTime().toString();
        ContentValues values = new ContentValues();
        values.put(Constant.QuizAccount.LAST_TIME_JOIN.getValue(), lastTimeJoin);
        if (checkJoinQuiz(context, quizId, accountID)) {
            sqLiteDatabase.update(Constant.QuizAccount.TABLE_NAME.getValue(), values,
                    "quiz_id=? and account_id=?", new String[]{quizId.toString(), accountID.toString()});
            return;
        }

        values.put(Constant.QuizAccount.QUIZ_ID.getValue(), quizId);
        values.put(Constant.QuizAccount.ACCOUNT_ID.getValue(), accountID);
        long result = sqLiteDatabase.insert(Constant.QuizAccount.TABLE_NAME.getValue(), null, values);
    }

    private boolean checkJoinQuiz(Context context, int quizId, int accountId) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from quiz_account where quiz_id =? and account_id =?",
                new String[]{String.valueOf(quizId), String.valueOf(accountId)});

        return cursor.getCount() > 0;
    }

    public List<Quiz> getQuizRecently(Context context, Account account) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from quiz inner join quiz_account " +
                        "on quiz.id = quiz_account.quiz_id " +
                        "where quiz.author = ? " +
                        "order by quiz_account.last_time_join desc " +
                        "limit 3 ;"
                , new String[]{account.getId().toString()});
        cursor.moveToFirst();
        List<Quiz> quizList = new ArrayList<>();
        QuestionDao questionDao = new QuestionDao();
        while (!cursor.isAfterLast()) {
            Quiz q = new Quiz();
            q.setId(cursor.getInt(0));
            q.setTitle(cursor.getString(1));
            q.setAuthor(account);
            q.setQuestionList(questionDao.getQuestionForQuiz(context, q.getId()));
            quizList.add(q);
            cursor.moveToNext();
        }
        return quizList;
    }

    public List<Quiz> getYourQuiz(Context context, Account account) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from quiz where author =?"
                , new String[]{account.getId().toString()});
        cursor.moveToFirst();
        List<Quiz> quizList = new ArrayList<>();
        QuestionDao questionDao = new QuestionDao();
        while (!cursor.isAfterLast()) {
            Quiz q = new Quiz();
            q.setId(cursor.getInt(0));
            q.setTitle(cursor.getString(1));
            q.setAuthor(account);
            q.setQuestionList(questionDao.getQuestionForQuiz(context, q.getId()));
            quizList.add(q);
            cursor.moveToNext();
        }
        return quizList;
    }

    private boolean insertQuiz(Context context, Quiz quiz) {
        db = new DBHelper(context);
        sqLiteDatabase = db.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.Quiz.TITLE.getValue(), quiz.getTitle());
        contentValues.put(Constant.Quiz.AUTHOR.getValue(), quiz.getAuthor().getId());

        long quizId = sqLiteDatabase.insert(Constant.Quiz.TABLE_NAME.getValue(), null, contentValues);
        if (quizId <= 0) {
            return false;
        } else {
            for (Question question : quiz.getQuestionList()) {
                ContentValues values = new ContentValues();
                values.put(Constant.Question.CONTENT.getValue(), question.getContent());
                values.put(Constant.Question.QUIZ_ID.getValue(), quizId);
                long questionId = sqLiteDatabase.insert(Constant.Question.TABLE_NAME.getValue(), null, values);

                if (questionId > 0) {
                    ContentValues values1 = new ContentValues();
                    values1.put(Constant.Answer.CONTENT.getValue(), question.getAnswer());
                    values1.put(Constant.Answer.QUES_ID.getValue(), questionId);
                    long answerId = sqLiteDatabase.insert(Constant.Answer.TABLE_NAME.getValue(), null, values1);

                    if (answerId <= 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            sqLiteDatabase.close();
            return true;
        }
    }
}
