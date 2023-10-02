package com.example.demo.service;

import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;

import java.util.List;

public interface ITradeService {

    public TradeCreateResponse create(TradeCreateRequest request);

    public TradeUpdateResponse update(TradeUpdateRequest request);

    public GetTradeResponse get(String tradeId);

    public List<GetTradeResponse> list();

    public void delete(String tradeId);
}
