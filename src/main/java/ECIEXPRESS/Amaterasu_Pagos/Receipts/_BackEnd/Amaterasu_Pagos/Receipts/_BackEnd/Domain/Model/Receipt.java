package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Receipt {

    private String receiptId;
    private String orderId;
    private String clientId;
    private String storeId;
    private PaymentDetail paymentDetail;
    private PaymentMethod paymentMethod;
    private ReceiptStatus receiptStatus;
    private TimeStamps timeStamps;
    public static Receipt createReceipt(CreateReceiptRequest request) {
        Receipt receipt = new Receipt();
        receipt.setReceiptId(UUID.randomUUID().toString());
        receipt.setOrderId(request.orderId());
        receipt.setClientId(request.clientId());
        receipt.setStoreId(request.storeId());
        receipt.setPaymentDetail(PaymentDetail.createPaymentDetail(request));
        receipt.setPaymentMethod(request.paymentMethod());
        receipt.setReceiptStatus(ReceiptStatus.PENDING);
        receipt.setTimeStamps(request.timeStamps());
        return receipt;
    }
}
