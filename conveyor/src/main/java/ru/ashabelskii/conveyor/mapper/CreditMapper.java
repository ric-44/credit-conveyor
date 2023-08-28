package ru.ashabelskii.conveyor.mapper;

import org.springframework.stereotype.Component;
import ru.ashabelskii.conveyor.dto.CreditDto;
import ru.ashabelskii.conveyor.dto.PaymentScheduleElementDto;
import ru.ashabelskii.conveyor.model.CreditInfo;
import ru.ashabelskii.conveyor.model.PaymentScheduleElement;

import java.util.List;

@Component
public class CreditMapper {

    public CreditDto mapCreditDto(CreditInfo creditInfo) {
        return new CreditDto(
                creditInfo.amount(),
                creditInfo.term(),
                creditInfo.monthlyPayment(),
                creditInfo.rate(),
                creditInfo.psk(),
                creditInfo.isInsuranceEnabled(),
                creditInfo.isSalaryClient(),
                mapPaymentScheduleDto(creditInfo.paymentSchedule())
        );
    }

    private List<PaymentScheduleElementDto> mapPaymentScheduleDto(List<PaymentScheduleElement> list) {
        return list.stream()
                .map(this::mapPaymentDto)
                .toList();
    }

    private PaymentScheduleElementDto mapPaymentDto(PaymentScheduleElement payment) {
        return new PaymentScheduleElementDto(
                payment.number(),
                payment.date(),
                payment.totalPayment(),
                payment.interestPayment(),
                payment.debtPayment(),
                payment.remainingDebt()
        );
    }
}
