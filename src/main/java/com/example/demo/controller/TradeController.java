package com.example.demo.controller;

import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;
import com.example.demo.model.response.WebResponse;
import com.example.demo.service.TradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TradeController {

    private final TradeService tradeService;

    TradeController(TradeService tradeService){
        this.tradeService = tradeService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            path = "/api/v1/trades",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<TradeCreateResponse> create(@Valid @RequestBody TradeCreateRequest request){
        TradeCreateResponse response = tradeService.create(request);

        return WebResponse.<TradeCreateResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("created")
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(
            path = "/api/v1/trades",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<TradeUpdateResponse> update(@Valid @RequestBody TradeUpdateRequest request){
        TradeUpdateResponse response = tradeService.update(request);

        return WebResponse.<TradeUpdateResponse>builder()
                .status(HttpStatus.OK.value())
                .data(response)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "api/v1/trades",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<List<GetTradeResponse>> findAll(){
        List<GetTradeResponse> tradeResponseList = tradeService.findAll();

        return WebResponse.<List<GetTradeResponse>>builder().data(tradeResponseList).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
            path = "api/v1/trades/{tradeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<GetTradeResponse> findById(@Valid @PathVariable("tradeId") String tradeId){
        GetTradeResponse tradeResponse = tradeService.findById(tradeId);

        return WebResponse.<GetTradeResponse>builder().data(tradeResponse).build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(
            path = "api/v1/trades/{tradeId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    private WebResponse<String> delete(@PathVariable("tradeId") String tradeId){
        tradeService.delete(tradeId);

        return WebResponse.<String>builder()
                .status(200)
                .message("OK")
                .build();
    }
}
