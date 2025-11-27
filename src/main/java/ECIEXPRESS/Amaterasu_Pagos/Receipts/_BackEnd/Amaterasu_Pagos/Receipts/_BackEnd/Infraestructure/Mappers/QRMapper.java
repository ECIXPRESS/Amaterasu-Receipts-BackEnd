package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Mappers;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Clients.QRValidator.Dto.QRValidatorRequests.CreateQRValidatorRequest;

public class QRMapper {
    public static CreateQRValidatorRequest mapToCreateQRValidatorRequest(Receipt receipt){
        return new CreateQRValidatorRequest(
                receipt.getOrderId(),
                receipt.getTimeStamps().getReceiptGeneratedDate(),
                receipt.getTimeStamps().getPaymentProcessedAt(),
                receipt.getPaymentMethod().getPaymentMethodType(),
                receipt.getReceiptStatus(),
                receipt.getOrderStatus()
        );
    }
}
