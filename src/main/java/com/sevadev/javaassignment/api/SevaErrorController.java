package com.sevadev.javaassignment.api;

import com.sevadev.javaassignment.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SevaErrorController implements ErrorController {
    final Logger logger = LoggerFactory.getLogger(SevaErrorController.class);

    @RequestMapping("/error")
    public ErrorDTO handleError() {
        logger.error("An unknown error occured while processing an API call!");
        return ErrorDTO.builder().statusCode(500).message("An unknown error occurred! Please revisit your request and try again.").build();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
