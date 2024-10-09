package com.example.calculator.controller;

import com.example.calculator.rpc.JsonRpcRequest;
import com.example.calculator.rpc.JsonRpcResponse;
import com.example.calculator.service.CalculatorService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonRpcController {
    @Autowired
    private CalculatorService calculatorService;
    @PostMapping("/jsonrpc")
    public ResponseEntity<Object> handleJsonRpcRequest(@RequestBody JsonRpcRequest request) {
        try {
            String method = request.getMethod();
            JsonNode params = request.getParams();
            double a = params.get("a").asDouble();
            double b = params.get("b").asDouble();
            double result;
            switch (method) {
                case "add":
                    result = calculatorService.add(a, b);
                    return ResponseEntity.ok(new JsonRpcResponse(result, request.getId()));
                case "subtract":
                    result = calculatorService.subtract(a, b);
                    return ResponseEntity.ok(new JsonRpcResponse(result, request.getId()));
                case "multiply":
                    result = calculatorService.multiply(a, b);
                    return ResponseEntity.ok(new JsonRpcResponse(result, request.getId()));
                case "divide":
                    result = calculatorService.divide(a, b);
                    return ResponseEntity.ok(new JsonRpcResponse(result, request.getId()));
                default:
                    return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
