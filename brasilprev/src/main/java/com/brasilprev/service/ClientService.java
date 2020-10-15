package com.brasilprev.service;

import com.brasilprev.model.Client;
import com.brasilprev.repository.ClientRepository;
import com.brasilprev.service.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;
    public List<Client> findall() {
        return clientRepository.findAll();
    }
    public Client findById(Long id) {

        Client client = clientRepository.findById(id).get();

        if(client == null) {

            /* if the client is null the method trow a custom exception */
            throw new ClientException("Client not found");
        }

        return client;
    }

    public Client save(Client client) {

        /* this method save users its just take from payload and save it
         * validate if cpf is in right pattern
         * */
        validateClient(client);
        return clientRepository.save(client);
    }

    /**
     * Method that check if the client exist if true return itself else throw a clientnotfound exception
     * @param client
     */

    public void checkClient(Client client) {
        findById(client.getId());
    }
public void validateClient(Client client){
    if(!isvalidCPF(client.getCpf())){
        throw new ClientException("Cpf not in right pattern");
    }
}
    public void update(Client client) {

        /* vericando se o vinho realmente existe, poderia ter
         * chamado o metodo buscarPeloId direto, mas criei o metodo
         *  verificar existencia para ajudar na legibilidade do codigo */
        checkClient(client);
        validateClient(client);
        clientRepository.save(client);
    }

    public void delete(Long id) {

        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ClientException("Client not found");
        }
    }
    public static boolean isvalidCPF(String CPF) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (CPF.equals("00000000000") ||
                CPF.equals("11111111111") ||
                CPF.equals("22222222222") || CPF.equals("33333333333") ||
                CPF.equals("44444444444") || CPF.equals("55555555555") ||
                CPF.equals("66666666666") || CPF.equals("77777777777") ||
                CPF.equals("88888888888") || CPF.equals("99999999999") ||
                (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
                num = (int)(CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else dig11 = (char)(r + 48);

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return(true);
            else return(false);
        } catch (InputMismatchException erro) {
            return(false);
        }
    }
}
