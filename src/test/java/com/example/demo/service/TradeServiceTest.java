package com.example.demo.service;


import com.example.demo.entity.Trade;
import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;
import com.example.demo.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private ValidationService validationService;

    private TradeService tradeService;

    private Trade trade;

    private TradeCreateRequest tradeCreateRequest;

    private TradeUpdateRequest tradeUpdateRequest;

    @BeforeEach
    void setUp() {
        tradeService = new TradeService(tradeRepository, validationService);

        trade = Trade.builder()
                .id("trade-1")
                .customerName("budiyanto")
                .customerPhone("0123123123")
                .customerEmail("budi@email.com")
                .inspectorName("inspect")
                .inspectorPhone("8293910")
                .inspectorEmail("inspect@example.com")
                .brand("toyota")
                .model("avanza")
                .type("all new all")
                .year("2020")
                .mileage(0)
                .fuel("bensin")
                .color("putih")
                .inspectionLocation("Jl dukuh bawah no. 3").build();

        tradeCreateRequest = TradeCreateRequest.builder()
                .customerName("budiyanto")
                .customerPhone("0123123123")
                .customerEmail("budi@email.com")
                .brand("toyota")
                .model("avanza")
                .type("all new all")
                .year("2020")
                .mileage(0)
                .fuel("bensin")
                .color("putih")
                .inspectionLocation("Jl dukuh bawah no. 3").build();

        tradeUpdateRequest = TradeUpdateRequest.builder()
                .id("trade-1")
                .inspectorName("inspect")
                .inspectorPhone("8293910")
                .inspectorEmail("inspect@example.com")
                .brand("toyota")
                .model("avanza")
                .type("all new all")
                .year("2020")
                .mileage(0)
                .fuel("bensin")
                .color("putih")
                .inspectionLocation("Jl dukuh bawah no. 3").build();
    }

    @Test
    void getAllTrades_ReturnTradeList() {
        List<Trade> trades = new ArrayList<>();
        trades.add(trade);
        trades.add(Trade.builder()
                .id("trade-1")
                .customerName("budiyanto")
                .customerPhone("0123123123")
                .customerEmail("budi@email.com")
                .inspectorName("inspect")
                .inspectorPhone("8293910")
                .inspectorEmail("inspect@example.com")
                .brand("toyota")
                .model("avanza")
                .type("all new all")
                .year("2020")
                .mileage(0)
                .fuel("bensin")
                .color("putih")
                .inspectionLocation("Jl dukuh bawah no. 3").build());


        when(tradeRepository.findAll()).thenReturn(trades);

        List<GetTradeResponse> tradeResponseList = tradeService.findAll();

        assertThat(tradeResponseList).isNotNull();
        assertThat(tradeResponseList.size()).isEqualTo(2);
        verify(tradeRepository, times(1)).findAll();
    }

    @Test
    void getTradeById_ReturnTradeById() {
        String tradeId = "trade-1";

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.ofNullable(trade));

        GetTradeResponse getTradeResponse = tradeService.findById(tradeId);

        assertThat(getTradeResponse).isNotNull();
        assertThat(getTradeResponse.getId()).isEqualTo("trade-1");
        verify(tradeRepository, times(1)).findById(tradeId);
    }

    @Test
    void createTrade_ReturnTradeCreateResponse() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        TradeCreateResponse tradeCreateResponse = tradeService.create(tradeCreateRequest);

        assertThat(tradeCreateResponse).isNotNull();
        verify(tradeRepository, times(1)).save(any(Trade.class));
    }

    @Test
    void updateTradeById_ReturnTradeUpdateResponse() {
        String tradeId = "trade-1";

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.ofNullable(trade));
        when(tradeRepository.save(trade)).thenReturn(trade);

        TradeUpdateResponse tradeUpdateResponse = tradeService.update(tradeUpdateRequest);

        assertThat(tradeUpdateResponse).isNotNull();
        verify(tradeRepository, times(1)).findById(tradeId);
        verify(tradeRepository, times(1)).save(trade);
    }

    @Test
    void deleteTradeById_ReturnVoid() {
        String tradeId = "trade-1";

        when(tradeRepository.findById(tradeId)).thenReturn(Optional.ofNullable(trade));
        doNothing().when(tradeRepository).delete(trade);

        tradeService.delete(tradeId);

        verify(tradeRepository, times(1)).findById(tradeId);
        verify(tradeRepository, times(1)).delete(trade);
    }
}
