package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

class ReceiptTest {

    @Test
    void createReceipt_ShouldSetAllFields() {
        // Given
        String receiptId = "receipt123";
        String orderId = "order123";
        String clientId = "client123";
        String storeId = "store456";
        ReceiptStatus status = ReceiptStatus.PENDING;
        OrderStatus orderStatus = OrderStatus.PENDING;
        TimeStamps timeStamps = new TimeStamps();
        PaymentMethod paymentMethod = new Cash();

        // When
        Receipt receipt = new Receipt();
        receipt.setReceiptId(receiptId);
        receipt.setOrderId(orderId);
        receipt.setClientId(clientId);
        receipt.setStoreId(storeId);
        receipt.setReceiptStatus(status);
        receipt.setOrderStatus(orderStatus);
        receipt.setTimeStamps(timeStamps);
        receipt.setPaymentMethod(paymentMethod);

        // Then
        assertAll(
                () -> assertEquals(receiptId, receipt.getReceiptId()),
                () -> assertEquals(orderId, receipt.getOrderId()),
                () -> assertEquals(clientId, receipt.getClientId()),
                () -> assertEquals(storeId, receipt.getStoreId()),
                () -> assertEquals(status, receipt.getReceiptStatus()),
                () -> assertEquals(orderStatus, receipt.getOrderStatus()),
                () -> assertEquals(timeStamps, receipt.getTimeStamps()),
                () -> assertEquals(paymentMethod, receipt.getPaymentMethod())
        );
    }

    @Test
    void createReceipt_FromCreateReceiptRequest_ShouldSetAllFields() {
        // Given
        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123",
                "client123",
                "store456",
                90.0,
                100.0,
                new Cash(),
                new TimeStamps(),
                Collections.emptyList()
        );

        // When
        Receipt receipt = Receipt.createReceipt(request);

        // Then
        assertAll(
                () -> assertEquals(request.orderId(), receipt.getOrderId()),
                () -> assertEquals(request.clientId(), receipt.getClientId()),
                () -> assertEquals(request.storeId(), receipt.getStoreId()),
                () -> assertEquals(ReceiptStatus.PENDING, receipt.getReceiptStatus()),
                () -> assertEquals(OrderStatus.PENDING, receipt.getOrderStatus()),
                () -> assertNotNull(receipt.getTimeStamps())
        );
    }
}