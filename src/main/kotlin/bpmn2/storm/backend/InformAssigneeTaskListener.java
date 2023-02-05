package bpmn2.storm.backend;

import bpmn2.storm.backend.model.PKI;
import org.camunda.bpm.engine.delegate.TaskListener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateTask;
//https://github.com/camunda/camunda-bpm-examples/blob/master/process-engine-plugin/bpmn-parse-listener-on-user-task/README.md
public class InformAssigneeTaskListener implements TaskListener {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());
    public static List<String> assigneeList = new ArrayList<String>();

    private static InformAssigneeTaskListener instance = null;

    protected InformAssigneeTaskListener() { }

    public static InformAssigneeTaskListener getInstance() {
        if(instance == null) {
            instance = new InformAssigneeTaskListener();
        }
        return instance;
    }

    public void notify(DelegateTask delegateTask) {
        PKI application = (PKI)delegateTask.getVariable("application");
        //application.setSt(application.getSelected());
        long lst = Integer.getInteger(application.getSt().toString()) + 1;
        application.setSt(Long.toString(lst));
        delegateTask.setVariable("st",lst);

        String assignee = delegateTask.getAssignee();
        assigneeList.add(assignee);
        LOGGER.info("Hello " + assignee + "! Please start to work on your task " + delegateTask.getName());
    }

}