import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BankTransferIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BankTransferService bankTransferService;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankTransferController bankTransferController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bankTransferController).build();
    }

    @Test
    public void testTransferBetweenBanks() throws Exception {
        BankAccount senderAccount = new BankAccount("senderAccount", BigDecimal.valueOf(1000));
        BankAccount receiverAccount = new BankAccount("receiverAccount", BigDecimal.valueOf(500));

        TransferRequest transferRequest = new TransferRequest("senderAccount", "receiverAccount", BigDecimal.valueOf(200));


        when(bankTransferService.transferBetweenBanks(transferRequest)).thenReturn(true);
        when(bankAccountRepository.findByAccountNumber("senderAccount")).thenReturn(senderAccount);
        when(bankAccountRepository.findByAccountNumber("receiverAccount")).thenReturn(receiverAccount);


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequest));

        // on execute la requête ici
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // et la reponse du statut ici
        assertEquals(200, response.getStatus());
    }
}


// CE TEST EST POUR TESTER EN GLOBAL LES CLASSES SI Y EN AVAIT, HELAS

