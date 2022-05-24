package com.hidarisoft.dynamostudy.service;

import com.hidarisoft.dynamostudy.chain.chains.FirstChain;
import com.hidarisoft.dynamostudy.chain.chains.SecondChain;
import com.hidarisoft.dynamostudy.chain.chains.ThirdChain;
import com.hidarisoft.dynamostudy.entity.TestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TestServiceTest {

    @InjectMocks
    private TestService testService;
    @Mock
    private FirstChain firstChain;
    @Mock
    private SecondChain secondChain;
    @Mock
    private ThirdChain thirdChain;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShouldReturnSuccessWhenChainIsCalled() {
        when(firstChain.nextHandle(any(SecondChain.class)))
                .thenReturn(secondChain);
        when(secondChain.nextHandle(any(ThirdChain.class)))
                .thenReturn(thirdChain);

        when(firstChain.process(any())).thenReturn(buildTestModel());

        assertEquals("12345678909", testService.callChains(buildTestModel()).getPk());
    }

    private TestModel buildTestModel() {
        return TestModel.builder()
                .pk("12345678909")
                .sk("SK")
                .createdBy("MX")
                .createdAt(LocalDateTime.of(2022, 2, 8, 12, 58)).build();
    }
}