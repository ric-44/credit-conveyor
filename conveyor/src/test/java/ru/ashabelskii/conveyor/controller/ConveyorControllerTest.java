package ru.ashabelskii.conveyor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import ru.ashabelskii.conveyor.ConveyorApplication;
import ru.ashabelskii.conveyor.dto.*;
import ru.ashabelskii.conveyor.exception.ScoringException;
import ru.ashabelskii.conveyor.service.ConveyorService;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ConveyorApplication.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc
class ConveyorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ConveyorService conveyorService;

    @Test
    void whenCallOffersWithValidRequestThenResponseStatusShouldBe200() throws Exception {
        var request = getLoanApplicationRequestDtoBuilder().build();

        mockMvc.perform(post("/conveyor/offers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(conveyorService).generateOffers(request);
    }

    @Test
    void whenCallOffersWithNotValidRequestThenResponseStatusShouldBe400() throws Exception {
        var request = getLoanApplicationRequestDtoBuilder()
                .passportNumber("5643331")
                .build();

        mockMvc.perform(post("/conveyor/offers")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(conveyorService, never()).generateOffers(request);
    }

    @Test
    void whenCallCalculationWithValidRequestThenResponseStatusShouldBe200() throws Exception {
        var request = getScoringDataDtoBuilder().build();

        mockMvc.perform(post("/conveyor/calculation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(conveyorService).calculateCredit(request);
    }

    @Test
    void whenCallCalculationWithNotValidRequestThenResponseStatusShouldBe400() throws Exception {
        var request = getScoringDataDtoBuilder()
                .amount(BigDecimal.ZERO)
                .build();

        mockMvc.perform(post("/conveyor/calculation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(conveyorService, never()).calculateCredit(request);
    }

    @Test
    void whenCallCalculationWithScoringExceptionThenResponseStatusShouldBe409() throws Exception {
        var request = getScoringDataDtoBuilder().build();
        when(conveyorService.calculateCredit(request)).thenThrow(ScoringException.class);

        mockMvc.perform(post("/conveyor/calculation")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    private static LoanApplicationRequestDto.LoanApplicationRequestDtoBuilder getLoanApplicationRequestDtoBuilder() {
        return LoanApplicationRequestDto.builder()
                .firstName("Сергей")
                .lastName("Ершов")
                .middleName("Иванович")
                .passportNumber("564344")
                .passportSeries("4012")
                .birthdate(LocalDate.parse("1992-06-21"))
                .email("email@ya.ru")
                .amount(BigDecimal.valueOf(11000))
                .term(7);
    }

    private static ScoringDataDto.ScoringDataDtoBuilder getScoringDataDtoBuilder() {
        return ScoringDataDto.builder()
                .firstName("Сергей")
                .lastName("Ершов")
                .middleName("Иванович")
                .passportNumber("564333")
                .passportSeries("4012")
                .birthdate(LocalDate.parse("1992-06-21"))
                .passportIssueDate(LocalDate.parse("2006-06-21"))
                .passportIssueBranch("ТП №43 отдела УФМС России")
                .amount(BigDecimal.valueOf(30_000))
                .term(18)
                .dependentAmount(1)
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .gender(GenderDto.MALE)
                .employment(getEmployment())
                .maritalStatus(MaritalDto.MARRIED)
                .account("900000");
    }

    private static EmploymentDto getEmployment() {
        return new EmploymentDto(EmploymentStatusDto.UNEMPLOYED, "12313123", BigDecimal.valueOf(90_000),
                PositionDto.WORKER, 15, 15);
    }
}