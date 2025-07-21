package com.ra.ss9.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/demo-log")
public class DemoLogController {
    @GetMapping("/trace")
    public String logTrace() {
        log.trace("Đã ghi log trace");
        return "Đã ghi TRACE log";
    }

    @GetMapping("/debug")
    public String logDebug() {
        log.debug("Đã ghi log debug");
        return "Đã ghi DEBUG log";
    }

    @GetMapping("/info")
    public String logInfo() {
        log.info("Đã ghi log info");
        return "Đã ghi INFO log";
    }

    @GetMapping("/warning")
    public String logWarning() {
        log.warn("Đã ghi log warning");
        return "Đã ghi WARN log";
    }

    @GetMapping("/error")
    public String logError() {
        log.error("Đã ghi log error");
        return "Đã ghi ERROR log";
    }
}
