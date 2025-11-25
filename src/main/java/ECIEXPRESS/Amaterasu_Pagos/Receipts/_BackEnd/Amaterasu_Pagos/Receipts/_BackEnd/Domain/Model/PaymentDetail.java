package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetail {
    private String bankReceiptNumber;
    private double originalAmount;
    private double finalAmount;
    private List<String> appliedPromotions;

    public PaymentDetail createPaymentDetail(CreateReceiptRequest createReceiptRequest) {
        PaymentDetail paymentDetail = new PaymentDetail();
        paymentDetail.setBankReceiptNumber(createReceiptRequest.getBankReceiptNumber());
        paymentDetail.setOriginalAmount(100.0);
        paymentDetail.setFinalAmount(100.0);
        paymentDetail.setAppliedPromotions(List.of("Promotion 1", "Promotion 2"));
        return paymentDetail;
    }
}
