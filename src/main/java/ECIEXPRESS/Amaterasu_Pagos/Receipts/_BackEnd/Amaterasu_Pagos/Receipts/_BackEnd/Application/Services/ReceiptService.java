package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Ports.ReceiptUseCases;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.BankReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.CashReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.ReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.WalletReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Service
public class ReceiptService implements ReceiptUseCases {
    private final Map<PaymentMethodType, ReceiptStrategy> strategyMap =Map.of(
            PaymentMethodType.BANK, new BankReceiptStrategy(),
            PaymentMethodType.WALLET, new WalletReceiptStrategy(),
            PaymentMethodType.CASH, new CashReceiptStrategy());
    @Override
    public CreateReceiptResponse createReceipt(CreateReceiptRequest request) {
        ReceiptStrategy strategy = strategyMap.get(request.paymentMethod().getPaymentMethodType());
        return strategy.createReceipt(request);
    }
}
