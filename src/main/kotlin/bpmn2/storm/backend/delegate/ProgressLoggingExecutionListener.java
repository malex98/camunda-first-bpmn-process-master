/*
 * Copyright Camunda Services GmbH and/or licensed to Camunda Services GmbH
 * under one or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information regarding copyright
 * ownership. Camunda licenses this file to you under the Apache License,
 * Version 2.0; you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bpmn2.storm.backend.delegate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import bpmn2.storm.backend.model.PKI;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;

/**
 * Execution listener to log property extension value
 *
 * @author kristin.polenz
 *
 */
public class ProgressLoggingExecutionListener implements ExecutionListener {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    public static List<String> progressValueList = new ArrayList<String>();

    private String propertyValue;

    public ProgressLoggingExecutionListener(String value) {
        this.propertyValue = value;
    }

    public void notify(DelegateExecution execution) throws Exception {
        PKI application = (PKI)execution.getVariable("application");
        application.setSt(application.getSt());
        long lst = Integer.getInteger(application.getSt().toString()) + 1;
        application.setSt(Long.toString(lst));
        execution.setVariable("st",lst);

        progressValueList.add(propertyValue);
        LOGGER.info("value of service task extension property 'progress': " + propertyValue);
    }

}
