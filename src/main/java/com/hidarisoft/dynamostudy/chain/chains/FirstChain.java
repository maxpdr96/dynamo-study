package com.hidarisoft.dynamostudy.chain.chains;

import com.hidarisoft.dynamostudy.chain.AbstractRequestTestHandler;
import com.hidarisoft.dynamostudy.entity.TestModel;

public class FirstChain extends AbstractRequestTestHandler {
    @Override
    public TestModel process(TestModel request) {
        return checkNext(request);
    }
}
