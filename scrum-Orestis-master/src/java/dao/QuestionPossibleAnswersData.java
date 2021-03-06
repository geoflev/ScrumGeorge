package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Answer;
import models.Question;

public class QuestionPossibleAnswersData {

    private static Database db = new Database();
//        private  Database db;
//   
//    public QuestionPossibleAnswersData(){
//        db = new Database();
//    }

    private static ResultSet getAll() {
        return db.getResults("SELECT * FROM questions ORDER BY id");
    }

    private static ResultSet getById(int id) {
        return db.getResults(
                "SELECT \n"
                + "    questions.id,text_quest, answers.id,text_ans\n"
                + "FROM\n"
                + "    questions,\n"
                + "    answers\n"
                + "WHERE\n"
                + "    questions.id = answers.question_id\n"
                + "        AND questions.id = " + id + ";");
    }

//    public static void printOne() {
//        ResultSet rs = getById(1);
//        String question = "";
//        String ans;
//        List<String> answers = new ArrayList();
//        try {
//            if (rs.next()) {
//                question = rs.getString(1);
//                ans = rs.getString(2);
//                answers.add(ans);
//            }
//            while (rs.next()) {
//                ans = rs.getString(2);
//                answers.add(ans);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(question);
//        System.out.println(answers);
//    }

    public static models.QuestionPossibleAnswers getOne(int id) {
        ResultSet rs = getById(id);
        Question question = new Question();
        List<Answer> answers = new ArrayList();
        int ans_id;
        try {
            if (rs.next()) {
                int quest_id = rs.getInt(1);
                question.setId(quest_id);
                question.setText(rs.getString(2));

                Answer answer = new Answer();
                ans_id = rs.getInt(3);
                answer.setId(ans_id);
                answer.setText(rs.getString(4));
                answers.add(answer);
            }
            while (rs.next()) {
                Answer answer = new Answer();
                ans_id = Integer.parseInt(rs.getString(3));
                answer.setId(ans_id);
                answer.setText(rs.getString(4));
                answers.add(answer);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(question);
        System.out.println(answers);
        models.QuestionPossibleAnswers qpa = new models.QuestionPossibleAnswers(question, answers);
        return qpa;
    }

}
