package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private String bankReceiptNumber;
    private double originalAmount;
    private double finalAmount;
    private List<String> appliedPromotions;

    public static PaymentDetail createPaymentDetail(CreateReceiptRequest createReceiptRequest) {
        log.info("Creating payment detail");
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setBankReceiptNumber(createReceiptRequest.paymentMethod().getBankReceiptNumber());
        double originalAmount = createReceiptRequest.originalAmount();
        paymentDetail.setOriginalAmount(originalAmount);
        double finalAmount = createReceiptRequest.finalAmount();
        if(finalAmount < originalAmount){
            log.error("Final amount cannot be less than the original amount");
            throw new IllegalArgumentException("Final amount cannot be less than the original amount");
        }
        paymentDetail.setFinalAmount(finalAmount);
        paymentDetail.setAppliedPromotions(createReceiptRequest.appliedPromotions());
        return paymentDetail;
    }
}
