package com.hidarisoft.dynamostudy.controller;

import com.hidarisoft.dynamostudy.entity.TestModel;
import com.hidarisoft.dynamostudy.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/")
public class TestDynamoController {

    @Autowired
    private TestService testService;

    @GetMapping
    private ResponseEntity<TestModel> callChainsTestModel() {
        var reqTestModel = TestModel.builder()
                .pk("12345678909")
                .sk("SK")
                .createdAt(LocalDateTime.now())
                .createdBy("MX").build();
        return ResponseEntity.ok(testService.callChains(reqTestModel));
    }
}
