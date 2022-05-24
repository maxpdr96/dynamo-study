package com.hidarisoft.dynamostudy.chain;

import com.hidarisoft.dynamostudy.entity.TestModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.validation.Valid;

@Slf4j
@Component
public abstract class AbstractRequestTestHandler {

    private AbstractRequestTestHandler abstractRequestTestHandler;

    @Autowired
    private ApplicationContext context;

    public AbstractRequestTestHandler nextHandle(AbstractRequestTestHandler abstractRequestTestHandler) {
        this.abstractRequestTestHandler = context.getBean(abstractRequestTestHandler.getClass());
        return this.abstractRequestTestHandler;
    }

    public abstract TestModel process(@Valid TestModel request);

    public TestModel checkNext(@Valid TestModel request) {
        if (abstractRequestTestHandler == null) {
            return request;
        }
        return abstractRequestTestHandler.process(request);
    }

}
