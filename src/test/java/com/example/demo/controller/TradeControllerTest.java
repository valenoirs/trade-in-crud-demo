package com.example.demo.controller;

import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;
import com.example.demo.model.response.WebResponse;
import com.example.demo.service.TradeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TradeService tradeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllTradeShouldReturnAllTrades() throws Exception {
        // Arrange
        List<GetTradeResponse> trades = new ArrayList<GetTradeResponse>();
        for (int i = 0; i < 5; i++) {
            trades.add(new GetTradeResponse(
                    "trade-" + i,
                    "budiyanto",
                    "0123123123",
                    "budi@email.com",
                    "Mr. inspector",
                    "098098098",
                    "update@email.com",
                    "toyota",
                    "avanza",
                    "all new all",
                    "2020",
                    0,
                    "bensin",
                    "putih",
                    "Jl dukuh bawah no. 3"
            ));
        }

        when(tradeService.findAll()).thenReturn(trades);

        // Act
        mockMvc.perform(get("/api/v1/trades")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk()
                ).andDo(result -> {
                    WebResponse<List<GetTradeResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<List<GetTradeResponse>>>() {
                    });

                    // Assert
                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals(5, response.getData().size());
                });

        verify(tradeService, times(1)).findAll();
    }

    @Test
    void getTradeByIdShouldReturnNotFound() throws Exception {
        when(tradeService.findById("xxx")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));

        mockMvc.perform(get("/api/v1/trades/xxx")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });

                    assertNotNull(response.getError());
                });

        verify(tradeService, times(1)).findById("xxx");
    }

    @Test
    void getTradeByIdShouldReturnTradeById() throws Exception {
        GetTradeResponse trade = new GetTradeResponse(
                "trade-1",
                "budiyanto",
                "0123123123",
                "budi@email.com",
                "Mr. inspector",
                "098098098",
                "update@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

        when(tradeService.findById("trade-1")).thenReturn(trade);

        mockMvc.perform(get("/api/v1/trades/trade-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<GetTradeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<GetTradeResponse>>() {
                    });

                    assertNull(response.getError());
                    assertNotNull(response.getData());
                    assertEquals("trade-1", response.getData().getId());
                    assertEquals("budiyanto", response.getData().getCustomerName());
                    assertEquals("0123123123", response.getData().getCustomerPhone());
                    assertEquals("budi@email.com", response.getData().getCustomerEmail());
                    assertEquals("Mr. inspector", response.getData().getInspectorName());
                    assertEquals("098098098", response.getData().getInspectorPhone());
                    assertEquals("update@email.com", response.getData().getInspectorEmail());
                    assertEquals("toyota", response.getData().getBrand());
                    assertEquals("avanza", response.getData().getModel());
                    assertEquals("all new all", response.getData().getType());
                    assertEquals("2020", response.getData().getYear());
                    assertEquals(0, response.getData().getMileage());
                    assertEquals("bensin", response.getData().getFuel());
                    assertEquals("putih", response.getData().getColor());
                    assertEquals("Jl dukuh bawah no. 3", response.getData().getInspectionLocation());
                });

        verify(tradeService, times(1)).findById("trade-1");
    }

    @Test
    void createTradeFailedShouldReturnBadRequest() throws Exception {
        TradeCreateRequest createRequest = new TradeCreateRequest(
                "",
                "",
                "budi@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

        mockMvc.perform(post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest))
                ).andExpectAll(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void createTradeSuccessShouldReturnTradeId() throws Exception {
        TradeCreateRequest createRequest = new TradeCreateRequest(
                "budiyanto",
                "0123123123",
                "budi@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

        when(tradeService.create(createRequest)).thenReturn(new TradeCreateResponse("trade-1"));

        mockMvc.perform(post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpectAll(
                        status().isCreated()
                ).andDo(result -> {
                    WebResponse<TradeCreateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<TradeCreateResponse>>() {
                    });

                    assertNull(response.getError());
                    assertEquals("trade-1", response.getData().getId());
                });

        verify(tradeService, times(1)).create(createRequest);
    }

    @Test
    void updateTradeByIdFailedShouldReturnNotFound() throws Exception {
        TradeUpdateRequest updateRequest = new TradeUpdateRequest(
                "xxx",
                "Mr. inspector",
                "098098098",
                "update@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

//        when(tradeService.update(updateRequest)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));
        when(tradeService.update(updateRequest)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Trade not found"));

        mockMvc.perform(put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpectAll(status().isNotFound())
                .andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<String>>() {
                    });

                    assertNotNull(response.getError());
                });

        verify(tradeService, times(1)).update(updateRequest);
    }

    @Test
    void updateTradeByIdFailedShouldReturnBadRequest() throws Exception {
        TradeUpdateRequest updateRequest = new TradeUpdateRequest(
                "trade-1",
                "",
                "098098098",
                "update@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

        when(tradeService.update(updateRequest)).thenReturn(new TradeUpdateResponse("trade-1"));

        mockMvc.perform(put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
                ).andExpectAll(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void updateTradeByIdSuccessShouldReturnTradeId() throws Exception {
        TradeUpdateRequest updateRequest = new TradeUpdateRequest(
                "trade-1",
                "Mr. Inspector",
                "098098098",
                "update@email.com",
                "toyota",
                "avanza",
                "all new all",
                "2020",
                0,
                "bensin",
                "putih",
                "Jl dukuh bawah no. 3"
        );

        when(tradeService.update(updateRequest)).thenReturn(new TradeUpdateResponse("trade-1"));

        mockMvc.perform(put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest))
                ).andExpectAll(status().isOk())
                .andDo(result -> {
                    WebResponse<TradeUpdateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<WebResponse<TradeUpdateResponse>>() {
                    });

                    assertNull(response.getError());
                    assertEquals("trade-1", response.getData().getId());
                });

        verify(tradeService, times(1)).update(updateRequest);
    }

    @Test
    void deleteTradeByIdShouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/api/v1/trades/trade-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(print());

        verify(tradeService, times(1)).delete("trade-1");
    }

    @Test
    void deleteTradeByIdShouldReturnOK() throws Exception {
        doNothing().when(tradeService).delete("trade-1");

        mockMvc.perform(delete("/api/v1/trades/trade-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk())
                .andDo(print());

        verify(tradeService, times(1)).delete("trade-1");
    }
}