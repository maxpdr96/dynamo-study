package com.hidarisoft.dynamostudy.chain.chains;

import com.hidarisoft.dynamostudy.chain.AbstractRequestTestHandler;
import com.hidarisoft.dynamostudy.entity.TestModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
@Slf4j
public class SecondChain extends AbstractRequestTestHandler {
    @Override
    public TestModel process(TestModel request) {
        log.info("SECOND CHAIN");
        return checkNext(request);
    }
}
