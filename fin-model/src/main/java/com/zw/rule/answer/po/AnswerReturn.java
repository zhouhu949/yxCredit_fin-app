package com.zw.rule.answer.po;

/**
 * Created by Administrator on 2018/1/3.
 */
public class AnswerReturn {
    private static final long serialVersionUID = -4122066429866956830L;

    private String questionId;//问题Id

    private String answer;//申请人选定的选项,复选答案，用 / 分割，如 A/C

    private String spendTime;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime;
    }
}
