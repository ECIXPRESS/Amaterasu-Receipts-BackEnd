package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;

public record CreateReceiptResponse (
        String receiptId,
        String orderId,
        String clientId,
        String storeId,
        double finalAmount,
        ReceiptStatus receiptStatus
){
}
