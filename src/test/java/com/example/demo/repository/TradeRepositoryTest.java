package com.example.demo.repository;

import com.example.demo.entity.Trade;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.MockMvcBuilder.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


@Disabled
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Test
    public void shouldSaveTradeReturnOK() {
        // Arrange
        Trade trade = Trade.builder()
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

        // Act
        Trade savedTrade = tradeRepository.save(trade);

        // Assert
        assertThat(savedTrade).isNotNull();
        assertThat(savedTrade.getId()).isEqualTo("trade-1");
    }

    // More Test to Write
}
