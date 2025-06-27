    package com.keepu.webAPI.controller;

    import com.keepu.webAPI.service.OpenRouterService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/api/v1/openrouter")
    public class OpenRouterController {

        @Autowired
        private OpenRouterService openRouterService;

        @PostMapping
        public String chat(@RequestBody String prompt) {
            return openRouterService.infer(prompt);
        }
    }