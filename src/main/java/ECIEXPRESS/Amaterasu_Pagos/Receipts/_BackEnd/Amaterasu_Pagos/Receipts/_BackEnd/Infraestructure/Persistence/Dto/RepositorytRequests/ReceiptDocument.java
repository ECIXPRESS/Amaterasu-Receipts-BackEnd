package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.PaymentDetail;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.PaymentMethod;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.TimeStamps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Receipts")
@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDocument {
    @Id
    private String receiptId;
    @Field("orderId")
    private String orderId;
    @Field("clientId")
    private String clientId;
    @Field("storeId")
    private String storeId;
    @Field("paymentDetail")
    private PaymentDetail paymentDetail;
    @Field("paymentMethod")
    private PaymentMethod paymentMethod;
    @Field("receiptStatus")
    private ReceiptStatus receiptStatus;
    @Field("orderStatus")
    private OrderStatus orderStatus;
    @Field("timestamps")
    private TimeStamps timeStamps;
}
