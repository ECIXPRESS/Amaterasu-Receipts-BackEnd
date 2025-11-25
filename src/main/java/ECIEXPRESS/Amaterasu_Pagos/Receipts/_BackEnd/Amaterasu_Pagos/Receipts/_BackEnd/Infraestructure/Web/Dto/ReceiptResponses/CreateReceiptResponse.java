package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentStatus;

public record CreateReceiptResponse (
        String receiptId,
        String orderId,
        String storeId,
        double finalAmount,
        PaymentStatus paymentStatus
){
}
