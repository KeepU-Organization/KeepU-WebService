package com.keepu.webAPI.controller;

import com.keepu.webAPI.service.HuggingFaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/huggingface")
public class HuggingFaceController {

    @Autowired
    private HuggingFaceService huggingFaceService;

    public static class PromptRequest {
        public String prompt;
    }

    @PostMapping("/infer")
    public String infer(@RequestBody PromptRequest request) {
        return huggingFaceService.infer(request.prompt);
    }
}