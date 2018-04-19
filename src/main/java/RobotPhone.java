import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class RobotPhone {
    private static Map<String, String> keywordsAnswerMap = new HashMap<String, String>();
    public RobotPhone() {
        keywordsAnswerMap.put("谁, 公司, 机构, 银行", "我们是专业的第3方融资服务平台，专门解决中小企业，个人融资难的问题的");
        keywordsAnswerMap.put("哪里, 在哪", "奥，我们在东门大桥，春熙路这边的，就是在市中心。");
        keywordsAnswerMap.put("产品, 怎么贷", "是这样子的，我们公司会根据您的实际情况，为您匹配到低费率而且是较快的贷款产品，当然我们公司也会收取贷款额少量比例的服务费用，具体多少还是根据客户实际情况来定的。请问你是需要抵押贷款能还是信用贷款？");
        keywordsAnswerMap.put("多少", "这个利息和额度的话，还是要根据您个人情况来定的哈，每个人的情况都不同的, 这样子吧，我先安排贷款顾问给您详细介绍下，看您的条件满足不。你再做决定。");
        keywordsAnswerMap.put("抵押, 信用, 材料, 资料", "恩，现在这些都是比较方便的啦。不管您是抵押,还是信用贷款，资料准备都很简单的了,就比如说:身 份证，征信报告，还有社保，公积金或者房厂证， 这些就可以了，这样吧，我一会还是发一个详细信 息到您手机上，您到时看一下好吗?。");
        keywordsAnswerMap.put("真的, 骗人, 假的, 相信", "这个您可以放心，您可以来我们公司当面了解一下的呀，眼见为实的嘛，对吧。");
    }

    //        哪里（两种情况、谁、地点）
    //        什么银行、啥银行、什么公司、啥公司、什么机构、啥机构
//        利息、利率
//        多久放款、多久能贷下来
    public static void main(String[] args) {
        ServiceSeqWordsEnum wordsEnum = ServiceSeqWordsEnum.GREETING;
        System.out.println("机器人：" + wordsEnum.getSentence());
        wordsEnum = wordsEnum.next();
        boolean attention = false;
        boolean isAnswer = false;
        int noUnderstand = 0;
        while (true) {
            if (wordsEnum == null) {
                System.out.println("挂电话...");
                break;
            }
            System.out.println("机器人：" + wordsEnum.getSentence());

            String str = readDataFromConsole("客户：");
            if (customerBusyWords(str)) {
                System.out.println("机器人：" + serviceAnserBusyWords(str));
                break;
            }

            if (ServiceSeqWordsEnum.ATTENTION_LOAN.equals(wordsEnum)) {
                if (KMP.matchString(str, "不需要") >= 0
                        || KMP.matchString(str, "没有") >=0
                        || KMP.matchString(str, "打扰我") >=0) {
                    System.out.println("机器人：" + serviceAnserBusyWords(str));
                    break;
                }

                attention = true;
                wordsEnum = wordsEnum.next();
                continue;
            }

            String matchAnswer = customerMatchKeywords(str);
            if (matchAnswer == null) {
                if (noUnderstand >= 2) {
                    System.out.println("不好意思, 我有点听不清您说话，那稍后再联系您，祝您生活愉快，再见");
                    break;
                }
                noUnderstand++;
                wordsEnum = wordsEnum.next();
            } else {
                if ("yes".equals(matchAnswer)) {
                    wordsEnum = wordsEnum.next();
                } else {
                    System.out.println(matchAnswer);
                    wordsEnum = wordsEnum.next();
                }
            }
        }
    }

    private static boolean customerBusyWords(String str) {
        List<String> busyWords = Arrays.asList(
                "在开会", "在开车", "有点事", "有事", "在忙", "有点忙", "不方便",
                "没有兴趣", "没兴趣", "不感兴趣");
        for (String busyWord: busyWords) {
            if (KMP.matchString(str, busyWord) >= 0) {
                return true;
            }
        }
        return false;
    }

    private static String serviceAnserBusyWords(String str) {
        return "好的，那具体到时候再说，我就先不打 扰您了，那祝你生活愉快，再见。";
    }

    private static String customerMatchKeywords(String str) {
        List<String> yes = Arrays.asList("嗯", "好", "行", "可以");
        List<String> who = Arrays.asList("谁", "公司", "机构", "银行");
        List<String> where = Arrays.asList("哪里", "在哪");
        List<String> plane = Arrays.asList("产品", "怎么贷"); // 方案
        List<String> amount = Arrays.asList("额度", "多少", "利息", "利率");
        List<String> material = Arrays.asList("抵押", "信用", "材料", "资料");
        List<String> suspect = Arrays.asList("真的", "骗人", "假的", "相信");

        for (String keyword: yes) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "yes";
            }
        }

        for (String keyword: who) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "我们是专业的第3方融资服务平台，专门解决中小企业，个人融资难的问题的";
            }
        }

        for (String keyword: where) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "奥，我们在东门大桥，春熙路这边的，就是在市中心。";
            }
        }

        for (String keyword: plane) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "是这样子的，我们公司会根据您的实际情况，为您匹配到低费率而且是较快的贷款产品，当然我们公司也会收取贷款额少量比例的服务费用，具体多少还是根据客户实际情况来定的。请问你是需要抵押贷款能还是信用贷款？";
            }
        }

        for (String keyword: amount) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "这个利息和额度的话，还是要根据您个人情况来定的哈，每个人的情况都不同的, 这样子吧，我先安排贷款顾问给您详细介绍下，看您的条件满足不。你再做决定。";
            }
        }

        for (String keyword: material) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "恩，现在这些都是比较方便的啦。不管您是抵押,还是信用贷款，资料准备都很简单的了,就比如说:身 份证，征信报告，还有社保，公积金或者房厂证， 这些就可以了，这样吧，我一会还是发一个详细信 息到您手机上，您到时看一下好吗?。";
            }
        }

        for (String keyword: suspect) {
            if (KMP.matchString(str, keyword) >= 0) {
                return "这个您可以放心，您可以来我们公司当面了解一下的呀，眼见为实的嘛，对吧。";
            }
        }

        return null;
    }

    private static String serviceAnserKeywords(String str) {
        return null;
    }



    /**
     * Use InputStreamReader and System.in to read data from console
     *
     * @param prompt
     *
     * @return input string
     */
    private static String readDataFromConsole(String prompt) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            System.out.print(prompt);
            str = br.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

}



//        1.（招呼） 喂，你好。
//
//        2.（问意图）您好，我这边是专业办理银行贷款的，请问你最近有办理银行贷款的考虑吗?
//
//        3.（问意图）是这样子的，我们是平安银行的合作单位，专门解决个人还有中小企业融资难，融资贵的问题的，我这边方便了解一下你的情况吗?
//
//        4.（了解信息）这样，我先问一下，您目前是需要多少资金呢?
//
//        5.（了解信息）那您是开公司，还是自己上班的。
//
//        6.（了解信息）那方便问一下，您自己有房子或者车子吗?
//
//        7. （确认了意图）好的，我这边已经帮您登记了基本信息，稍微我们会安排专门的基金顾问跟您做进一步的交流，到时候你有其他问题也可以跟他沟通
//
//        7. 好的，那具体到时候再说，我就先不打 扰您了，那祝你生活愉快，再见。


