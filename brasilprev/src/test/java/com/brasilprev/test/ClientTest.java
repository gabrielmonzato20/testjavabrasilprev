package com.brasilprev.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.brasilprev.model.Client;
import com.brasilprev.repository.ClientRepository;
import com.brasilprev.service.ClientService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClientTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllClients() {

        List<Client> clients = new ArrayList<Client>();
        clients.add(new Client(1L, "TEST1", "65671827841","TEST2ADRESS"));
        clients.add(new Client(2L, "TEST2", "10142224197","TEST3ADRESS"));
        clients.add(new Client(3L, "TEST3", "86777385681","TEST4ADRESS"));
        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findall();
        assertEquals(3, result.size());
        assertEquals(1L, result.get(0).getId());

    }

    @Test
    public void testGetClientById() {
        Client client = new Client(1L, "TEST1", "65671827841","TEST2ADRESS");

       when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        Client result = clientService.findById(1L);
        assertEquals(1L, result.getId());

    }

    @Test
    public void saveClient() {
        Client client = new Client(1L, "TEST1", "65671827841","TEST2ADRESS");
        when(clientRepository.save(client)).thenReturn(client);
        Client result = clientService.save(client);
        assertEquals(   1L, result.getId());
        assertEquals("TEST1", result.getName());
        assertEquals("65671827841", result.getCpf());
        assertEquals("TEST2ADRESS", result.getAdress());
    }

    @Test
    public void deleteClient() {
        Client client = new Client(1L, "TEST1", "65671827841","TEST2ADRESS");
        clientService.delete(client.getId());
        verify(clientRepository, times(1)).deleteById(client.getId());

    }



}
