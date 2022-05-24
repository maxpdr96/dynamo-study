package com.hidarisoft.dynamostudy.service;

import com.hidarisoft.dynamostudy.chain.chains.FirstChain;
import com.hidarisoft.dynamostudy.chain.chains.SecondChain;
import com.hidarisoft.dynamostudy.chain.chains.ThirdChain;
import com.hidarisoft.dynamostudy.entity.TestModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
@AllArgsConstructor
public class TestService {
    private FirstChain firstChain;
    private SecondChain secondChain;
    private ThirdChain thirdChain;

    public TestModel callChains(@Valid TestModel reqTestModel){
        firstChain.nextHandle(secondChain)
                .nextHandle(thirdChain);

        return firstChain.process(reqTestModel);
    }

}
