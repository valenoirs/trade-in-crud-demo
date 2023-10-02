package com.example.demo.controller;

import com.example.demo.entity.Trade;
import com.example.demo.model.request.TradeCreateRequest;
import com.example.demo.model.request.TradeUpdateRequest;
import com.example.demo.model.response.GetTradeResponse;
import com.example.demo.model.response.TradeCreateResponse;
import com.example.demo.model.response.TradeUpdateResponse;
import com.example.demo.model.response.WebResponse;
import com.example.demo.repository.TradeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        tradeRepository.deleteAll();
    }

    // 400 Create trade with empty customer name
    @Test
    void createTradeCustomerNameEmpty() throws Exception {
        TradeCreateRequest request = new TradeCreateRequest();
        request.setCustomerPhone("0123123123");
        request.setCustomerEmail("budi@email.com");
        request.setBrand("toyota");
        request.setModel("avanza");
        request.setType("all new all");
        request.setYear("2020");
        request.setMileage(0);
        request.setFuel("bensin");
        request.setColor("putih");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE
                }
        );
    }

    // 400 Create trade with empty customer phone
    @Test
    void createTradeCustomerPhoneEmpty() throws Exception {
        TradeCreateRequest request = new TradeCreateRequest();
        request.setCustomerName("budiyanto");
        request.setCustomerEmail("budi@email.com");
        request.setBrand("toyota");
        request.setModel("avanza");
        request.setType("all new all");
        request.setYear("2020");
        request.setMileage(0);
        request.setFuel("bensin");
        request.setColor("putih");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE
                }
        );
    }

    @Test
    void createTradeInvalidCustomerEmail() throws Exception{
        TradeCreateRequest request = new TradeCreateRequest();
        request.setCustomerName("budiyanto");
        request.setCustomerPhone("0898123894");
        request.setCustomerEmail("xxx");
        request.setBrand("toyota");
        request.setModel("avanza");
        request.setType("all new all");
        request.setYear("2020");
        request.setMileage(0);
        request.setFuel("bensin");
        request.setColor("putih");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE
                }
        );
    }

    // 201 Create trade success
    @Test
    void createTradeSuccess() throws Exception {
        TradeCreateRequest request = new TradeCreateRequest();
        request.setCustomerName("budiyanto");
        request.setCustomerPhone("0123123123");
        request.setCustomerEmail("budi@email.com");
        request.setBrand("toyota");
        request.setModel("avanza");
        request.setType("all new all");
        request.setYear("2020");
        request.setMileage(0);
        request.setFuel("bensin");
        request.setColor("putih");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                post("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isCreated()
        ).andDo(result -> {
                    WebResponse<TradeCreateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getError());

                    Trade trade = tradeRepository.findById(response.getData().getId()).orElse(null);
                    assertNotNull(trade);

                    assertEquals(response.getData().getId(), trade.getId());
                    assertEquals(request.getCustomerName(), trade.getCustomerName());
                    assertEquals(request.getCustomerPhone(), trade.getCustomerPhone());
                    assertEquals(request.getCustomerEmail(), trade.getCustomerEmail());
                    assertEquals(request.getBrand(), trade.getBrand());
                    assertEquals(request.getModel(), trade.getModel());
                    assertEquals(request.getType(), trade.getType());
                    assertEquals(request.getYear(), trade.getYear());
                    assertEquals(request.getMileage(), trade.getMileage());
                    assertEquals(request.getFuel(), trade.getFuel());
                    assertEquals(request.getColor(), trade.getColor());
                    assertEquals(request.getInspectionLocation(), trade.getInspectionLocation());
                }
        );
    }

    // 404 Update trade by id not found
    @Test
    void updateTradeByIdNotFound() throws Exception {
        TradeUpdateRequest request = new TradeUpdateRequest();
        request.setId("xxx");
        request.setInspectorName("Mr. inspector");
        request.setInspectorPhone("098098098");
        request.setInspectorEmail("update@email.com");
        request.setBrand("toyotaUpdated");
        request.setModel("avanzaUpdated");
        request.setType("all new");
        request.setYear("2024");
        request.setMileage(1000);
        request.setFuel("solar");
        request.setColor("hitam");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<TradeUpdateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE
                }
        );
    }

    // 400 Update trade by id with invalid payload
    @Test
    void updateTradeByIdBadRequest() throws Exception {
        Trade trade = new Trade();
        trade.setId("tradeId-0001");
        trade.setCustomerName("budiyanto");
        trade.setCustomerPhone("0123123123");
        trade.setCustomerEmail("budi@email.com");
        trade.setBrand("toyota");
        trade.setModel("avanza");
        trade.setType("all new all");
        trade.setYear("2020");
        trade.setMileage(0);
        trade.setFuel("bensin");
        trade.setColor("putih");
        trade.setInspectionLocation("Jl dukuh bawah no. 3");
        tradeRepository.save(trade);

        TradeUpdateRequest request = new TradeUpdateRequest();
        request.setId("tradeId-0001");
        request.setInspectorPhone("098098098");
        request.setInspectorEmail("update@email.com");
        request.setBrand("toyotaUpdated");
        request.setModel("avanzaUpdated");
        request.setType("all new");
        request.setYear("2024");
        request.setMileage(1000);
        request.setFuel("solar");
        request.setColor("hitam");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getError());
            // ADD MORE CASE
        });
    }

    // 200 Update trade by id success
    @Test
    void updateTradeByIdSuccess() throws Exception {
        Trade trade = new Trade();
        trade.setId("tradeId-0001");
        trade.setCustomerName("budiyanto");
        trade.setCustomerPhone("0123123123");
        trade.setCustomerEmail("budi@email.com");
        trade.setBrand("toyota");
        trade.setModel("avanza");
        trade.setType("all new all");
        trade.setYear("2020");
        trade.setMileage(0);
        trade.setFuel("bensin");
        trade.setColor("putih");
        trade.setInspectionLocation("Jl dukuh bawah no. 3");
        tradeRepository.save(trade);

        TradeUpdateRequest request = new TradeUpdateRequest();
        request.setId("tradeId-0001");
        request.setInspectorName("Mr. inspector");
        request.setInspectorPhone("098098098");
        request.setInspectorEmail("update@email.com");
        request.setBrand("toyotaUpdated");
        request.setModel("avanzaUpdated");
        request.setType("all new");
        request.setYear("2024");
        request.setMileage(1000);
        request.setFuel("solar");
        request.setColor("hitam");
        request.setInspectionLocation("Jl dukuh bawah no. 3");

        mockMvc.perform(
                put("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<TradeUpdateResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

            assertNull(response.getError());

            Trade dbTrade = tradeRepository.findById(response.getData().getId()).orElse(null);
            assertNotNull(dbTrade);

            assertEquals(response.getData().getId(), dbTrade.getId());
            assertEquals(request.getInspectorName(), dbTrade.getInspectorName());
            assertEquals(request.getInspectorPhone(), dbTrade.getInspectorPhone());
            assertEquals(request.getInspectorEmail(), dbTrade.getInspectorEmail());
            assertEquals(request.getBrand(), dbTrade.getBrand());
            assertEquals(request.getModel(), dbTrade.getModel());
            assertEquals(request.getType(), dbTrade.getType());
            assertEquals(request.getYear(), dbTrade.getYear());
            assertEquals(request.getMileage(), dbTrade.getMileage());
            assertEquals(request.getFuel(), dbTrade.getFuel());
            assertEquals(request.getColor(), dbTrade.getColor());
            assertEquals(request.getInspectionLocation(), dbTrade.getInspectionLocation());
                }
        );
    }

    // 200 Get all trade
    @Test
    void getAllTrade() throws Exception {
        for (int i = 0; i < 5; i++) {
            Trade trade = new Trade();
            trade.setId("tradeId-000" + i);
            trade.setCustomerPhone("012312312" + i);
            trade.setCustomerEmail("budi@email.com");
            trade.setBrand("toyota");
            trade.setModel("avanza");
            trade.setType("all new all");
            trade.setYear("202" + i);
            trade.setMileage(2000 + i);
            trade.setFuel("bensin");
            trade.setColor("putih");
            trade.setInspectionLocation("Jl dukuh bawah no. " + i);
            tradeRepository.save(trade);
        }

        mockMvc.perform(
                get("/api/v1/trades")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<List<GetTradeResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getError());

                    List<Trade> dbTrade = tradeRepository.findAll();
                    assertNotNull(dbTrade);

                }
        );
    }

    // 404 Get trade by id not found
    @Test
    void getTradeByIdNotFound() throws Exception {
        mockMvc.perform(
                get("/api/v1/trades/xxx")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<GetTradeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE (?)
                }
        );
    }

    // 200 Get trade by id success
    @Test
    void getTradeById() throws Exception {
            Trade trade = new Trade();
            trade.setId("tradeId-0001");
            trade.setCustomerPhone("0123123123");
            trade.setCustomerEmail("budi@email.com");
            trade.setBrand("toyota");
            trade.setModel("avanza");
            trade.setType("all new all");
            trade.setYear("2021");
            trade.setMileage(2000);
            trade.setFuel("bensin");
            trade.setColor("putih");
            trade.setInspectionLocation("Jl dukuh bawah no. 3");
            tradeRepository.save(trade);


        mockMvc.perform(
                get("/api/v1/trades/"+trade.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<GetTradeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getError());

                    Trade dbTrade = tradeRepository.findById(trade.getId()).orElse(null);
                    assertNotNull(dbTrade);
                }
        );
    }

    // 404 Delete trade by id not found
    @Test
    void deleteTradeByIdNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/v1/trades/xxx")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNotNull(response.getError());
                    // ADD MORE CASE (?)
                }
        );
    }

    // 200 Delete trade by id success
    @Test
    void deleteTradeByIdById() throws Exception {
        Trade trade = new Trade();
        trade.setId("tradeId-0001");
        trade.setCustomerPhone("0123123123");
        trade.setCustomerEmail("budi@email.com");
        trade.setBrand("toyota");
        trade.setModel("avanza");
        trade.setType("all new all");
        trade.setYear("2021");
        trade.setMileage(2000);
        trade.setFuel("bensin");
        trade.setColor("putih");
        trade.setInspectionLocation("Jl dukuh bawah no. 3");
        tradeRepository.save(trade);


        mockMvc.perform(
                delete("/api/v1/trades/"+trade.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
                    WebResponse<GetTradeResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
                    });

                    assertNull(response.getError());

                    Trade dbTrade = tradeRepository.findById(trade.getId()).orElse(null);
                    assertNull(dbTrade);
                }
        );
    }
}