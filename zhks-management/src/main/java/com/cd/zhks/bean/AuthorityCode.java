package com.cd.zhks.bean;

/**
 * Created by ma.xiaofeng on 2018/5/4.
 */
public enum AuthorityCode {
    USER_MANAGE(1L, "用户管理"),
    ROLE_MANAGE(2L, "角色管理"),
    MEDICAL_CLASSIFY(3L, "医科分类"),
    DATA_MANAGE(4L, "资料管理"),
    PAPER_MANAGE(5L, "试卷管理"),
    QUESTION_BANK_MANAGE(6L, "题库管理"),
    EXAM_ONLINE_COUNT(7L, "在线考试统计"),
    TRAIN_ONLINE_COUNT(8L, "在线培训统计"),
    TRAIN_SCORE_SEARCH(9L, "培训成绩查询"),
    EXAM_SCORE_SEARCH(10L, "考试成绩查询"),
    MANAGER_INFORM_LIST(11L, "管理员通知列表"),
    EXAMER_INFORM_LIST(12L, "考试人员通知列表"),
    MOCK_EXAM(13L, "在线考试"),
    DATA_STUDY_TRAIN(14L, "资料学习培训"),
    QUESTION_BANK_TRAIN(15L, "题库学习培训")
    ;

    private Long id;
    private String authorityName;

    AuthorityCode(Long id, String authorityName) {
        this.id = id;
        this.authorityName = authorityName;
    }

    public Long getId() {
        return id;
    }

    public String getAuthorityName() {
        return authorityName;
    }
}
