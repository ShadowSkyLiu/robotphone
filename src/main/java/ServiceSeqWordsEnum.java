public enum ServiceSeqWordsEnum {
    //        1.（招呼） 喂，你好。
//
//        2.（问意图）您好，我这边是专业办理银行贷款的，请问你最近有办理银行贷款的考虑吗?
//
//        3.（问意图）是这样子的，我们是平安银行的合作单位，专门解决个人还有中小企业融资难，融资贵的问题的，我这边方便了解一下你的情况吗?
//
//        4.（了解信息）这样，我先问一下，您目前是需要多少资金呢?
//
//        5.（了解信息）那您是开公司，还是自己上班的。
//        5.1. 那您目前工资多少呢？
//
//        6.（了解信息）那方便问一下，您自己有房子或者车子吗?
//
//        7. （确认了意图）好的，我这边已经帮您登记了基本信息，稍微我们会安排专门的基金顾问跟您做进一步的交流，到时候你有其他问题也可以跟他沟通
//
//        8. 好的，那我就先不打扰您了， 祝你生活愉快，再见。
    GREETING("喂，你好。"),
    ATTENTION_LOAN("您好，我这边是专业办理银行贷款的，请问你最近有办理银行贷款的考虑吗?"),
    ATTENTION_ASKINFO("是这样子的，我们是平安银行的合作单位，专门解决个人还有中小企业融资难，融资贵的问题的，我这边方便了解一下你的情况吗?"),
    QUALIFY_AMOUNT("这样，我先问一下，您目前是需要多少资金呢?"),
    QUALIFY_SALARY("那您目前工资多少呢？"),
    QUALIFY_ASSET("那方便问一下，您自己有房子或者车子吗?"),
    CONFIRM_YES("好的，我这边已经帮您登记了基本信息，稍微我们会安排专门的基金顾问跟您做进一步的交流，到时候你有其他问题也可以跟他沟通"),
    BYE("好的，那我就先不打扰您了， 祝你生活愉快，再见。");

    private String sentence; // 语音

    public String getSentence() {
        return sentence;
    }

    ServiceSeqWordsEnum(String sentence) {
        this.sentence = sentence;
    }

    public ServiceSeqWordsEnum previous() {
        return values()[(this.ordinal() - 1) % values().length];
    }

    public ServiceSeqWordsEnum next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}
