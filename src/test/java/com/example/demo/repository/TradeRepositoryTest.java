package com.example.demo.repository;

import com.example.demo.entity.Trade;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    void setUp() {
        Trade trade1 = Trade.builder()
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

        Trade trade2 = Trade.builder()
                .id("trade-2")
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

        tradeRepository.save(trade1);
        tradeRepository.save(trade2);
    }

    @AfterEach
    void tearDown() {
        tradeRepository.deleteAll();
    }

    @Test
    public void shouldCreateTrade() {
        // Arrange
        Trade trade = Trade.builder()
                .id("trade-3")
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

        // Act
        Trade savedTrade = tradeRepository.save(trade);

        // Assert
        assertThat(savedTrade).isNotNull();
        assertThat(savedTrade.getId()).isEqualTo("trade-3");
    }

    @Test
    void shouldDeleteTrades() {
        tradeRepository.deleteById("trade-1");

        assertThat(tradeRepository.findById("trade-1")).isEmpty();
    }

    @Test
    void shouldGetAllTrades() {
        List<Trade> trades = tradeRepository.findAll();

        assertThat(trades.size()).isEqualTo(2);
    }

    @Test
    void shouldGetTradeById() {
        Optional<Trade> trade = tradeRepository.findById("trade-1");

        assertThat(trade).isNotNull();
        assertThat(trade.isPresent()).isEqualTo(true);
    }
}
