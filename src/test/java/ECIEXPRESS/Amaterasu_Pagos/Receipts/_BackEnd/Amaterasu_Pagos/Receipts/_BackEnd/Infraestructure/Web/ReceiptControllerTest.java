package ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web;

import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Application.Ports.ReceiptUseCases;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.OrderStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Domain.Model.Enums.ReceiptStatus;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptRequests.CreateReceiptRequest;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.CreateReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetQrReceiptResponse;
import ECIEXPRESS.Amaterasu_Pagos.Receipts._BackEnd.Amaterasu_Pagos.Receipts._BackEnd.Infraestructure.Web.Dto.ReceiptResponses.GetReceiptResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReceiptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ReceiptUseCases receiptUseCases;

    @InjectMocks
    private ReceiptController receiptController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(receiptController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createReceipt_ShouldReturnCreated() throws Exception {
        // Given
        CreateReceiptRequest request = new CreateReceiptRequest(
                "order123", "client123", "store456",
                100.0, 90.0, null, null, List.of("PROMO10")
        );

        CreateReceiptResponse response = new CreateReceiptResponse(
                "receipt123", "order123", "client123", "store456",
                90.0, ReceiptStatus.PENDING, "qr_code_123"
        );

        when(receiptUseCases.createReceipt(any(CreateReceiptRequest.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/v1/receipts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.receiptId").value("receipt123"))
                .andExpect(jsonPath("$.orderId").value("order123"))
                .andExpect(jsonPath("$.finalAmount").value(90.0));
    }

    @Test
    void getReceiptsByClientId_ShouldReturnReceipts() throws Exception {
        // Given
        String clientId = "client123";
        List<GetReceiptResponse> responses = Arrays.asList(
                new GetReceiptResponse("receipt1", "order1", clientId, "store1",
                        null, null, ReceiptStatus.PENDING, OrderStatus.PENDING, null),
                new GetReceiptResponse("receipt2", "order2", clientId, "store1",
                        null, null, ReceiptStatus.PAYED, OrderStatus.PENDING, null)
        );

        when(receiptUseCases.getReceiptsByClientId(any())).thenReturn(responses);

        // When & Then
        mockMvc.perform(get("/api/v1/receipts/client/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].receiptId").value("receipt1"))
                .andExpect(jsonPath("$[1].receiptId").value("receipt2"));
    }

    @Test
    void getReceiptByOrderId_ShouldReturnReceipt() throws Exception {
        // Given
        String orderId = "order123";
        GetReceiptResponse response = new GetReceiptResponse(
                "receipt123", orderId, "client123", "store456",
                null, null, ReceiptStatus.PENDING, OrderStatus.PENDING, null
        );

        when(receiptUseCases.getReceiptByOrderId(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/receipts/order/{orderId}", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.receiptId").value("receipt123"))
                .andExpect(jsonPath("$.orderId").value(orderId));
    }

    @Test
    void getQrCodeByOrderId_ShouldReturnQRCode() throws Exception {
        // Given
        String orderId = "order123";
        GetQrReceiptResponse response = new GetQrReceiptResponse("qr_code_content");

        when(receiptUseCases.getQrCodeByOrderId(any())).thenReturn(response);

        // When & Then
        mockMvc.perform(get("/api/v1/receipts/order/{orderId}/qr", orderId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.QRCode").value("qr_code_content"));
    }

    @Test
    void updateToPayed_ShouldReturnOk() throws Exception {
        // Given
        String orderId = "order123";
        when(receiptUseCases.updateToPayed(any())).thenReturn(true);

        // When & Then
        mockMvc.perform(patch("/api/v1/receipts/{orderId}/pay", orderId))
                .andExpect(status().isOk());
    }

    @Test
    void updateToPayed_ShouldReturnBadRequest() throws Exception {
        // Given
        String orderId = "order123";
        when(receiptUseCases.updateToPayed(any())).thenReturn(false);

        // When & Then
        mockMvc.perform(patch("/api/v1/receipts/{orderId}/pay", orderId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateToDelivered_ShouldReturnOk() throws Exception {
        // Given
        String orderId = "order123";
        when(receiptUseCases.updateToDelivered(any())).thenReturn(true);

        // When & Then
        mockMvc.perform(patch("/api/v1/receipts/{orderId}/deliver", orderId))
                .andExpect(status().isOk());
    }
}