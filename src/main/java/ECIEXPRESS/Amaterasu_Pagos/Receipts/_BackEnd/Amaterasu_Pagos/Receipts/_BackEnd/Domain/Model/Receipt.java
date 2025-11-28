package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    private OrderStatus orderStatus;
    private TimeStamps timeStamps;
    public static Receipt createReceipt(CreateReceiptRequest request) {
        Receipt receipt = new Receipt();
        receipt.setReceiptId(IdGenerator.generate());
        receipt.setOrderId(request.orderId());
        receipt.setClientId(request.clientId());
        receipt.setStoreId(request.storeId());
        receipt.setPaymentDetail(PaymentDetail.createPaymentDetail(request));
        receipt.setPaymentMethod(request.paymentMethod());
        receipt.setReceiptStatus(ReceiptStatus.PENDING);
        receipt.setOrderStatus(OrderStatus.PENDING);
        receipt.setTimeStamps(request.timeStamps());
        return receipt;
    }

    public boolean updateToPayed() {
        if(this.receiptStatus == ReceiptStatus.PAYED){
            log.error("Receipt {} already payed", this.receiptId);
            throw new RuntimeException("Receipt already payed");
        }
        if(this.orderStatus == OrderStatus.REFUNDED){
            log.error("Receipt {} already refunded", this.receiptId);
            throw new RuntimeException("Receipt already refunded");
        }
        this.receiptStatus = ReceiptStatus.PAYED;
        return true;
    }

    public boolean updateToDelivered() {
        if(this.receiptStatus == ReceiptStatus.DELIVERED){
            log.error("Receipt {} already delivered", this.receiptId);
            throw new RuntimeException("Receipt already delivered");
        }
        else if(this.orderStatus == OrderStatus.DELIVERED){
            log.error("Receipt {} already delivered", this.receiptId);
            throw new RuntimeException("Receipt already delivered");
        }
        if(this.receiptStatus == ReceiptStatus.REFUNDED){
            log.error("Receipt {} already refunded", this.receiptId);
            throw new RuntimeException("Receipt already refunded");
        }
        if (this.orderStatus == OrderStatus.REFUNDED){
            log.error("Receipt {} already refunded", this.receiptId);
            throw new RuntimeException("Receipt already refunded");
        }
        if(this.orderStatus == OrderStatus.PENDING && this.receiptStatus == ReceiptStatus.PAYED){
            this.receiptStatus = ReceiptStatus.DELIVERED;
            this.orderStatus = OrderStatus.DELIVERED;
            return true;
        }
        return false;
    }
}
