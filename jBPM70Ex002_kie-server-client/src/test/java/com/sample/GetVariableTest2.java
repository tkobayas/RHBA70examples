package com.sample;

import static com.sample.Constants.CONTAINER_ID;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import org.kie.server.client.ProcessServicesClient;

public class GetVariableTest2 extends TestCase {

    public void testRest() throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        //params.put("Var1", "AAA");
        ProcessServicesClient processClient = KieServerRestUtils.getProcessServicesClient();
        long processInstanceId = processClient.startProcess(CONTAINER_ID, "project1.helloProcessCustomVar", params);

        System.out.println("startProcess() : processInstanceId = " + processInstanceId);

        String var1 = processClient.getProcessInstanceVariable(CONTAINER_ID, processInstanceId, "myPojo", String.class);

        System.out.println("MyPojo = " + var1);
    }
}