package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.PaymentMethod;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.TimeStamps;

import java.util.List;

public record CreateReceiptRequest (
        String orderId,
        String clientId,
        String storeId,
        double originalAmount,
        double finalAmount,
        PaymentMethod paymentMethod,
        TimeStamps timeStamps,
        List<String> appliedPromotions) {
}
