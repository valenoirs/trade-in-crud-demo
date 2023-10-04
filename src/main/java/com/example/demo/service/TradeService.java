package com.example.demo.service;

import com.example.demo.entity.Trade;
import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;
import com.example.demo.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TradeService implements ITradeService {

    private final TradeRepository tradeRepository;
    private final ValidationService validationService;

    TradeService(TradeRepository tradeRepository, ValidationService validationService) {
        this.tradeRepository = tradeRepository;
        this.validationService = validationService;
    }

    @Transactional
    @Override
    public TradeCreateResponse create(TradeCreateRequest request) {
        validationService.validate(request);

        Trade trade = new Trade();
        trade.setId(UUID.randomUUID().toString());
        trade.setCustomerName(request.getCustomerName());
        trade.setCustomerPhone(request.getCustomerPhone());
        trade.setCustomerEmail(request.getCustomerEmail());
        trade.setBrand(request.getBrand());
        trade.setModel(request.getModel());
        trade.setType(request.getType());
        trade.setYear(request.getYear());
        trade.setMileage(request.getMileage());
        trade.setFuel(request.getFuel());
        trade.setColor(request.getColor());
        trade.setInspectionLocation(request.getInspectionLocation());

        tradeRepository.save(trade);

        return TradeCreateResponse.builder()
                .id(trade.getId())
                .build();
    }

    @Transactional
    @Override
    public TradeUpdateResponse update(TradeUpdateRequest request) {
        validationService.validate(request);

        Trade trade = tradeRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));

        trade.setInspectorName(request.getInspectorName());
        trade.setInspectorPhone(request.getInspectorPhone());
        trade.setInspectorEmail(request.getInspectorEmail());
        trade.setBrand(request.getBrand());
        trade.setModel(request.getModel());
        trade.setType(request.getType());
        trade.setYear(request.getYear());
        trade.setMileage(request.getMileage());
        trade.setFuel(request.getFuel());
        trade.setColor(request.getColor());
        trade.setInspectionLocation(request.getInspectionLocation());

        tradeRepository.save(trade);

        return TradeUpdateResponse.builder()
                .id(request.getId())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public GetTradeResponse findById(String tradeId){
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));

        return createTradeResponse(trade);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GetTradeResponse> findAll() {
        List<Trade> trades = tradeRepository.findAll();

        return trades.stream().map(this::createTradeResponse).toList();
    }

    @Transactional
    public void delete(String tradeId){
        Trade contact = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found"));

        tradeRepository.delete(contact);
    }

    private GetTradeResponse createTradeResponse(Trade trade){
        return GetTradeResponse.builder()
                .id(trade.getId())
                .customerName(trade.getCustomerName())
                .customerPhone(trade.getCustomerPhone())
                .customerEmail(trade.getCustomerEmail())
                .inspectorName(trade.getInspectorName())
                .inspectorPhone(trade.getInspectorPhone())
                .inspectorEmail(trade.getInspectorEmail())
                .brand(trade.getBrand())
                .model(trade.getModel())
                .type(trade.getType())
                .year(trade.getYear())
                .mileage(trade.getMileage())
                .fuel(trade.getFuel())
                .color(trade.getColor())
                .inspectionLocation(trade.getInspectionLocation())
                .build();
    }
}
