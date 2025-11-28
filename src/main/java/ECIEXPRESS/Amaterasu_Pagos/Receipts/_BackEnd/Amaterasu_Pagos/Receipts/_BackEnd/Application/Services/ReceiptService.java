package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Mappers.ReceiptMapper;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Ports.ReceiptUseCases;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.BankReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.CashReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.ReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.WalletReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@NoArgsConstructor
@Service
public class ReceiptService implements ReceiptUseCases {
    private ReceiptRepositoryProvider receiptRepositoryProvider;
    private final Map<PaymentMethodType, ReceiptStrategy> strategyMap =Map.of(
            PaymentMethodType.BANK, new BankReceiptStrategy(),
            PaymentMethodType.WALLET, new WalletReceiptStrategy(),
            PaymentMethodType.CASH, new CashReceiptStrategy());
    @Override
    public CreateReceiptResponse createReceipt(CreateReceiptRequest request) {
        ReceiptStrategy strategy = strategyMap.get(request.paymentMethod().getPaymentMethodType());
        return strategy.createReceipt(request);
    }
    @Override
    public List<GetReceiptResponse> getReceiptsByClientId(GetReceiptByClientIdRequest request) {
        List<Receipt> receipts = receiptRepositoryProvider.getReceiptsByClientId(request.clientId()).stream()
                                    .map(ReceiptMapper::createReceipt)
                                    .collect(Collectors.toList());
        return receipts.stream()
                .map(ReceiptMapper::receiptToGetReceiptResponse)
                .collect(Collectors.toList());
    }

    @Override
    public GetReceiptResponse getReceiptByOrderId(GetReceiptByOrderIdRequest request) {
        Receipt receipt = ReceiptMapper.createReceipt(receiptRepositoryProvider.getByOrderId(request.orderId()));
        return ReceiptMapper.receiptToGetReceiptResponse(receipt);
    }

    @Override
    public GetQrReceiptResponse getQrCodeByOrderId(GetQrReceiptRequest request) {
        try{
            Receipt receipt = ReceiptMapper.createReceipt(receiptRepositoryProvider.getByOrderId(request.orderId()));
            return ReceiptMapper.receiptToGetQrReceiptResponse(receipt);
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean updateToDelivered(UpdateToDeliveredRequest request) {
        Receipt receipt = ReceiptMapper.createReceipt(receiptRepositoryProvider.getByOrderId(request.orderId()));
        receipt.updateToDelivered();
        receipt.setReceiptStatus(ReceiptStatus.DELIVERED);
        receipt.setOrderStatus(OrderStatus.DELIVERED);
        return true;
    }
}
