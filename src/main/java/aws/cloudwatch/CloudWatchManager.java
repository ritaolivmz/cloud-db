package aws.cloudwatch;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.*;

public class CloudWatchManager {

    private AmazonCloudWatchEvents cwe;

    private void accessCloudWatch() {
        if (cwe == null) {
            cwe = AmazonCloudWatchEventsClientBuilder.defaultClient();
        }
    }

    public void listAllRulesAssociatedToLambdaFunction() {
        ListRuleNamesByTargetRequest listRuleNamesByTargetRequest = new ListRuleNamesByTargetRequest();
        listRuleNamesByTargetRequest.setTargetArn("arn:aws:lambda:eu-west-1:130154230810:function:hello-lambda-function");

        //retrieve CloudWatch rule name using lambda function RNA
        ListRuleNamesByTargetResult listRuleNamesByTargetResult =  cwe.listRuleNamesByTarget(listRuleNamesByTargetRequest);

        if (listRuleNamesByTargetResult != null) {
            System.out.println("# of rules: " + listRuleNamesByTargetResult.getRuleNames().size());
            for (String rule : listRuleNamesByTargetResult.getRuleNames()) {
                System.out.println("rule "+rule);
            }
        }

        //retrieve rule object
        ListRulesRequest listRulesRequest = new ListRulesRequest();
        ListRulesResult listRulesResult = cwe.listRules(listRulesRequest);

        String ruleArn = "arn:aws:events:eu-west-1:130154230810:rule/hello-lambda-rule";

        if (listRulesResult != null) {
            System.out.println("size: "+listRulesResult.getRules().size());
            for (Rule rule : listRulesResult.getRules()) {
                if (rule.getArn().compareTo(ruleArn) == 0) {
                    System.out.println("found the rule: " + rule.getArn() + " - " + rule.getName());
                    return ;
                }
            }
        }
    }

    public void changeChronExpression() {
        accessCloudWatch();
        PutRuleRequest putRuleRequest = new PutRuleRequest().withName("hello-lambda-rule");
        putRuleRequest.setScheduleExpression("cron(0/10 * * * ? *)");
        cwe.putRule(putRuleRequest);
    }

}
