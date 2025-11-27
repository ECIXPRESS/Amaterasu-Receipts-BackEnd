package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Clients.QRValidator.Dto.QRValidatorRequests;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;

public record CreateQRValidatorRequest(
        String orderId,
        String receiptGeneratedDate,
        String paymentProcessedAt,
        PaymentMethodType paymentMethodType,
        ReceiptStatus receiptStatus,
        OrderStatus orderStatus
) {
}
