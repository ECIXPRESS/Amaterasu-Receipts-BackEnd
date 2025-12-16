package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CashReceiptStrategyTest {

    @Mock
    private ReceiptRepositoryProvider receiptRepositoryProvider;

    private CashReceiptStrategy cashReceiptStrategy;

    @BeforeEach
    void setUp() {
        cashReceiptStrategy = new CashReceiptStrategy(receiptRepositoryProvider);
    }

    @Test
    void createReceipt_WithValidCashPayment_ShouldCreateReceiptSuccessfully() {
        // Given
        Cash cash = new Cash();
        cash.setPaymentMethodType(PaymentMethodType.CASH);

        // Set up timestamps with a future payment date
        TimeStamps timeStamps = new TimeStamps();
        String paymentDate = DateUtils.formatDate(new Date(System.currentTimeMillis() + 1000), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setPaymentProcessedAt(paymentDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, cash, timeStamps, List.of()
        );

        // Mock the repository to return a valid response
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId("order123");
        receiptDoc.setClientId("client123");
        receiptDoc.setStoreId("store456");
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        // Set up the expected timestamps in the response
        TimeStamps savedTimeStamps = new TimeStamps();
        String now = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        savedTimeStamps.setReceiptGeneratedDate(now);
        savedTimeStamps.setPaymentProcessedAt(paymentDate);
        receiptDoc.setTimeStamps(savedTimeStamps);

        when(receiptRepositoryProvider.save(any(Receipt.class)))
                .thenAnswer(invocation -> {
                    Receipt savedReceipt = invocation.getArgument(0);
                    // Update the receipt document with the saved receipt's timestamps
                    receiptDoc.setTimeStamps(savedReceipt.getTimeStamps());
                    return new ReceiptRepositoryResponse(receiptDoc);
                });


        // When
        CreateReceiptResponse response = cashReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        assertEquals("order123", response.orderId());
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }

    @Test
    void createReceipt_WithPayedCashReceipt_ShouldCreateSuccessfully() {
        // Given
        Cash cash = new Cash();
        cash.setPaymentMethodType(PaymentMethodType.CASH);

        // Create empty timestamps - let the strategy handle the receiptGeneratedDate
        TimeStamps timeStamps = new TimeStamps();

        // Set payment processed at a time after the receipt will be generated
        String paymentDate = DateUtils.formatDate(new Date(System.currentTimeMillis() + 1000), DateUtils.TIMESTAMP_FORMAT);
        timeStamps.setPaymentProcessedAt(paymentDate);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 100.0, cash, timeStamps, List.of()
        );

        // Mock response with the same timestamps
        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        // The strategy will set the receiptGeneratedDate
        TimeStamps savedTimeStamps = new TimeStamps();
        String now = DateUtils.formatDate(new Date(), DateUtils.TIMESTAMP_FORMAT);
        savedTimeStamps.setReceiptGeneratedDate(now);
        savedTimeStamps.setPaymentProcessedAt(paymentDate);
        receiptDoc.setTimeStamps(savedTimeStamps);

        when(receiptRepositoryProvider.save(any(Receipt.class)))
                .thenAnswer(invocation -> {
                    Receipt savedReceipt = invocation.getArgument(0);
                    // Update the receipt document with the saved receipt's timestamps
                    receiptDoc.setTimeStamps(savedReceipt.getTimeStamps());
                    return new ReceiptRepositoryResponse(receiptDoc);
                });


        // When
        CreateReceiptResponse response = cashReceiptStrategy.createReceipt(request);

        // Then
        assertNotNull(response);
        verify(receiptRepositoryProvider, times(1)).save(any(Receipt.class));
    }
}