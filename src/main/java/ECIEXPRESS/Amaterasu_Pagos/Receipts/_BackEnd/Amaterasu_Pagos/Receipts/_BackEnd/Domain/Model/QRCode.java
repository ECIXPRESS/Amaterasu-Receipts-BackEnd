package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.DateUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRCode {
    private String orderId;
    private String receiptGeneratedDate;
    private String paymentProcessedAt;
    private PaymentMethodType paymentMethodType;
    private ReceiptStatus receiptStatus;
    private OrderStatus orderStatus;

    public String createQrCode (Receipt receipt) throws Exception {
        this.orderId = receipt.getOrderId();
        this.paymentMethodType = receipt.getPaymentMethod().getPaymentMethodType();
        this.receiptGeneratedDate = receipt.getTimeStamps().getReceiptGeneratedDate();
        this.paymentProcessedAt = receipt.getTimeStamps().getPaymentProcessedAt();
        this.receiptStatus = receipt.getReceiptStatus();
        this.orderStatus = receipt.getOrderStatus();
        if(this.receiptStatus == ReceiptStatus.DELIVERED){
            log.error("Receipt can't be delivered in creation");
            throw new Exception("Receipt can't be delivered in creation");
        }
        if(this.receiptStatus == ReceiptStatus.REFUNDED){
            if (this.orderStatus == OrderStatus.DELIVERED){
                log.error("Receipt can't be refunded and delivered in creation");
                throw new Exception("Receipt can't be refunded and delivered in creation");
            }
            log.error("Receipt can't be refunded in creation");
            throw new Exception("Receipt can't be refunded in creation");
        }
        if(this.orderStatus == OrderStatus.DELIVERED){
            if(this.receiptStatus == ReceiptStatus.PENDING){
                log.error("Receipt can't be pending and delivered");
                throw new Exception("Receipt can't be pending and delivered");
            }
            log.error("Receipt can't be delivered in creation");
            throw new Exception("Receipt can't be delivered in creation");
        }
        Date paymentProcessedAt =  DateUtils.parseDate(this.paymentProcessedAt, DateUtils.TIMESTAMP_FORMAT);
        Date receiptGeneratedDate = DateUtils.parseDate(this.receiptGeneratedDate, DateUtils.TIMESTAMP_FORMAT);
        if (this.paymentMethodType == PaymentMethodType.CASH){
            if(this.receiptStatus == ReceiptStatus.PAYED){
                log.error("Receipt can't be payed as cash in creation");
                throw new Exception("Receipt can't be payed as cash in creation");
            }
            if(paymentProcessedAt.before(receiptGeneratedDate)){
                log.error("Payment processed at can't be before receipt generated date");
                throw new Exception("Payment processed at can't be before receipt generated date");
            }
        }
        else if(this.paymentMethodType == PaymentMethodType.BANK){
            if(paymentProcessedAt.after(receiptGeneratedDate)){
                log.error("Payment processed at can't be after receipt generated date");
                throw new Exception("Payment processed at can't be after receipt generated date");
            }
        }
        return orderId+"_"+receiptGeneratedDate+"_"+paymentProcessedAt+"_"+paymentMethodType+"_"+receiptStatus+"_"+orderStatus;
    }
}
