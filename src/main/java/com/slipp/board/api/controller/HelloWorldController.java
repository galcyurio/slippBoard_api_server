package com.slipp.board.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping(value="/excludeSecurity/helloWorld", method = RequestMethod.GET)
    public ResponseEntity<String> getExcludeSecurityHelloWorld() {
        return new ResponseEntity<>("HelloWorld", HttpStatus.OK);
    }

    @RequestMapping(value="/includeSecurity/helloWorld", method = RequestMethod.GET)
    public ResponseEntity<String> getIncludeSecurityHelloWorld() {
        return new ResponseEntity<>("HelloWorld", HttpStatus.OK);
    }

}
