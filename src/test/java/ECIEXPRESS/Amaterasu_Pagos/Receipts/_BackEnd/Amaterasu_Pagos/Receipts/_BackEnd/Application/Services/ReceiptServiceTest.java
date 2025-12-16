package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.BankReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.CashReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Services.Strategy.WalletReceiptStrategy;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Bank;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Cash;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.TimeStamps;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Wallet;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.PaymentMethodType;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Receipt;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Port.ReceiptRepositoryProvider;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytRequests.ReceiptDocument;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Persistence.Dto.RepositorytResponses.ReceiptRepositoryResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.*;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assumptions;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

    @Mock
    private ReceiptRepositoryProvider receiptRepositoryProvider;

    @Mock
    private BankReceiptStrategy bankReceiptStrategy;

    @Mock
    private WalletReceiptStrategy walletReceiptStrategy;

    @Mock
    private CashReceiptStrategy cashReceiptStrategy;

    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptService(
                receiptRepositoryProvider,
                bankReceiptStrategy,
                walletReceiptStrategy,
                cashReceiptStrategy
        );
    }

    @Test
    void createReceipt_WithWalletPayment_ShouldUseWalletStrategy() {
        // Given
        Wallet wallet = new Wallet();
        wallet.setPaymentMethodType(PaymentMethodType.WALLET);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "orderW1", "clientW1", "storeW1",
                100.0, 100.0, wallet, new TimeStamps(), List.of("PROMO10")
        );

        CreateReceiptResponse expected = new CreateReceiptResponse(
                "receiptW1", "orderW1", "clientW1", "storeW1",
                100.0, ReceiptStatus.PAYED, "qr_wallet"
        );

        when(walletReceiptStrategy.createReceipt(request)).thenReturn(expected);

        // When
        CreateReceiptResponse result = receiptService.createReceipt(request);

        // Then
        assertNotNull(result);
        assertEquals("receiptW1", result.receiptId());
        verify(walletReceiptStrategy).createReceipt(request);
        verifyNoInteractions(bankReceiptStrategy);
        verifyNoInteractions(cashReceiptStrategy);
    }

    @Test
    void createReceipt_WithCashPayment_ShouldUseCashStrategy() {
        // Given
        Cash cash = new Cash();
        cash.setPaymentMethodType(PaymentMethodType.CASH);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "orderC1", "clientC1", "storeC1",
                50.0, 50.0, cash, new TimeStamps(), List.of()
        );

        CreateReceiptResponse expected = new CreateReceiptResponse(
                "receiptC1", "orderC1", "clientC1", "storeC1",
                50.0, ReceiptStatus.PENDING, "qr_cash"
        );

        when(cashReceiptStrategy.createReceipt(request)).thenReturn(expected);

        // When
        CreateReceiptResponse result = receiptService.createReceipt(request);

        // Then
        assertNotNull(result);
        assertEquals("receiptC1", result.receiptId());
        verify(cashReceiptStrategy).createReceipt(request);
        verifyNoInteractions(bankReceiptStrategy);
        verifyNoInteractions(walletReceiptStrategy);
    }

    @Test
    void getQrCodeByOrderId_ShouldWrapExceptionIntoRuntimeException() {
        // Given
        String orderId = "orderFail1";
        GetQrReceiptRequest request = new GetQrReceiptRequest(orderId);

        RuntimeException root = new RuntimeException("boom");
        when(receiptRepositoryProvider.getByOrderId(orderId)).thenThrow(root);

        // When
        RuntimeException ex = assertThrows(RuntimeException.class, () -> receiptService.getQrCodeByOrderId(request));

        // Then
        assertTrue(ex.getMessage().contains("Failed to generate QR code"));
        assertTrue(ex.getMessage().contains("boom"));
        assertSame(root, ex.getCause());
    }

    @Test
    void getReceiptsByClientId_WhenEmpty_ShouldReturnEmptyList() {
        // Given
        String clientId = "clientEmpty";
        GetReceiptByClientIdRequest request = new GetReceiptByClientIdRequest(clientId);

        when(receiptRepositoryProvider.getReceiptsByClientId(clientId)).thenReturn(List.of());

        // When
        List<?> result = receiptService.getReceiptsByClientId(request);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(receiptRepositoryProvider).getReceiptsByClientId(clientId);
    }

    @Test
    void updateToPayed_WhenAlreadyPayed_ShouldThrowAndNotSave() {
        // Given
        String orderId = "orderAlreadyPayed";
        UpdateToPayedRequest request = new UpdateToPayedRequest(orderId);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("r1");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setClientId("c1");
        receiptDoc.setStoreId("s1");
        receiptDoc.setReceiptStatus(ReceiptStatus.PAYED);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        // Receipt.updateToPayed() uses paymentMethodType internally; make sure it is present to avoid NPE.
        Cash cashPayment = new Cash();
        cashPayment.setPaymentMethodType(PaymentMethodType.CASH);
        receiptDoc.setPaymentMethod(cashPayment);

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);
        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);

        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> receiptService.updateToPayed(request));
        assertTrue(ex.getMessage().toLowerCase().contains("already payed"));

        verify(receiptRepositoryProvider, never()).save(any(Receipt.class));
    }

    @Test
    void updateToDelivered_WhenAlreadyDelivered_ShouldThrowAndNotSave() {
        // Given
        String orderId = "orderAlreadyDelivered";
        UpdateToDeliveredRequest request = new UpdateToDeliveredRequest(orderId);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("r2");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setClientId("c2");
        receiptDoc.setStoreId("s2");
        receiptDoc.setReceiptStatus(ReceiptStatus.PAYED);
        receiptDoc.setOrderStatus(OrderStatus.DELIVERED);

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);
        // When / Then
        RuntimeException ex = assertThrows(RuntimeException.class, () -> receiptService.updateToDelivered(request));
        assertTrue(ex.getMessage().toLowerCase().contains("delivered"));

        // Since the domain throws, the service must not persist.
        verify(receiptRepositoryProvider, never()).save(any(Receipt.class));
    }
    @Test
    void createReceipt_WithBankPayment_ShouldUseBankStrategy() {
        // Given
        Bank bank = new Bank();
        bank.setPaymentMethodType(PaymentMethodType.BANK);

        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 90.0, bank, new TimeStamps(), List.of("PROMO10")
        );

        CreateReceiptResponse expectedResponse = new CreateReceiptResponse(
                "receipt123", "order123", "client123", "store456",
                90.0, ReceiptStatus.PAYED, "qr_code"
        );

        when(bankReceiptStrategy.createReceipt(request)).thenReturn(expectedResponse);

        // When
        CreateReceiptResponse result = receiptService.createReceipt(request);

        // Then
        assertNotNull(result);
        assertEquals("receipt123", result.receiptId());
        verify(bankReceiptStrategy).createReceipt(request);
    }

    @Test
    void getReceiptsByClientId_ShouldReturnReceipts() {
        // Given
        String clientId = "client123";
        GetReceiptByClientIdRequest request = new GetReceiptByClientIdRequest(clientId);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt1");
        receiptDoc.setOrderId("order1");
        receiptDoc.setClientId(clientId);
        receiptDoc.setStoreId("store1");

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getReceiptsByClientId(clientId))
                .thenReturn(List.of(repoResponse));

        // When
        List<GetReceiptResponse> result = receiptService.getReceiptsByClientId(request);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("receipt1", result.get(0).receiptId());
    }

    @Test
    void getReceiptByOrderId_ShouldReturnReceipt() {
        // Given
        String orderId = "order123";
        GetReceiptByOrderIdRequest request = new GetReceiptByOrderIdRequest(orderId);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setClientId("client123");

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);

        // When
        GetReceiptResponse result = receiptService.getReceiptByOrderId(request);

        // Then
        assertNotNull(result);
        assertEquals("receipt123", result.receiptId());
        assertEquals(orderId, result.orderId());
    }

    @Test
    void getQrCodeByOrderId_ShouldReturnQRCode() throws Exception {
        // Given
        String orderId = "order123";
        GetQrReceiptRequest request = new GetQrReceiptRequest(orderId);

        // Create a cash payment method
        Cash cashPayment = new Cash();
        cashPayment.setPaymentMethodType(PaymentMethodType.CASH);

        // Create and set up time stamps
        TimeStamps timeStamps = new TimeStamps();
        String currentTimestamp = java.time.Instant.now().toString();
        timeStamps.setReceiptGeneratedDate(currentTimestamp);
        timeStamps.setPaymentProcessedAt(currentTimestamp);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setClientId("client123");
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);
        receiptDoc.setPaymentMethod(cashPayment);
        receiptDoc.setTimeStamps(timeStamps);  // Set the time stamps

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);

        // When
        GetQrReceiptResponse result = receiptService.getQrCodeByOrderId(request);

        // Then
        assertNotNull(result);
        assertNotNull(result.QRCode());
    }
    @Test
    void updateToPayed_ShouldUpdateStatus() {
        // Given
        String orderId = "order123";
        UpdateToPayedRequest request = new UpdateToPayedRequest(orderId);

        // Create a cash payment method
        Cash cashPayment = new Cash();
        cashPayment.setPaymentMethodType(PaymentMethodType.CASH);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setPaymentMethod(cashPayment);
        receiptDoc.setReceiptStatus(ReceiptStatus.PENDING); // Initial status

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);
        when(receiptRepositoryProvider.save(any(Receipt.class))).thenAnswer(invocation -> {
            Receipt savedReceipt = invocation.getArgument(0);
            // Update the document with the saved receipt's status
            receiptDoc.setReceiptStatus(savedReceipt.getReceiptStatus());
            return new ReceiptRepositoryResponse(receiptDoc);
        });

        // When
        boolean result = receiptService.updateToPayed(request);

        // Then
        assertTrue(result);
        assertEquals(ReceiptStatus.PAYED, receiptDoc.getReceiptStatus());
        verify(receiptRepositoryProvider).save(any(Receipt.class));
    }

    @Test
    void updateToDelivered_ShouldUpdateStatus() {
        // Given
        String orderId = "order123";
        UpdateToDeliveredRequest request = new UpdateToDeliveredRequest(orderId);

        ReceiptDocument receiptDoc = new ReceiptDocument();
        receiptDoc.setReceiptId("receipt123");
        receiptDoc.setOrderId(orderId);
        receiptDoc.setReceiptStatus(ReceiptStatus.PAYED);
        receiptDoc.setOrderStatus(OrderStatus.PENDING);

        ReceiptRepositoryResponse repoResponse = new ReceiptRepositoryResponse(receiptDoc);

        when(receiptRepositoryProvider.getByOrderId(orderId)).thenReturn(repoResponse);
        when(receiptRepositoryProvider.save(any(Receipt.class))).thenReturn(repoResponse);

        // When
        boolean result = receiptService.updateToDelivered(request);

        // Then
        assertTrue(result);
        verify(receiptRepositoryProvider).save(any(Receipt.class));
    }
}