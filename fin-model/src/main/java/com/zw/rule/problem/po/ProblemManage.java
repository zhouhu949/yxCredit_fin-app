package com.zw.rule.problem.po;

/**
 * Created by Administrator on 2017/9/19.
 */
public class ProblemManage {
    private String id;
    private String problem_name;
    private String problem_content;
    private String problem_type;
    private String problem_desc;
    private String problem_state;
    private String create_time;
    private String alter_time;
    private String platform_type;

    public String getPlatform_type() {
        return platform_type;
    }

    public void setPlatform_type(String platform_type) {
        this.platform_type = platform_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProblem_name() {
        return problem_name;
    }

    public void setProblem_name(String problem_name) {
        this.problem_name = problem_name;
    }

    public String getProblem_content() {
        return problem_content;
    }

    public void setProblem_content(String problem_content) {
        this.problem_content = problem_content;
    }

    public String getProblem_type() {
        return problem_type;
    }

    public void setProblem_type(String problem_type) {
        this.problem_type = problem_type;
    }

    public String getProblem_state() {
        return problem_state;
    }

    public void setProblem_state(String problem_state) {
        this.problem_state = problem_state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAlter_time() {
        return alter_time;
    }

    public void setAlter_time(String alter_time) {
        this.alter_time = alter_time;
    }

    public String getProblem_desc() {
        return problem_desc;
    }

    public void setProblem_desc(String problem_desc) {
        this.problem_desc = problem_desc;
    }
}
