package com.brasilprev.service.exceptions;

public class ClientException  extends RuntimeException{

        private static final long serialVersionUID = 8712699959450716707L;

        public ClientException(String mensagem) {
            super(mensagem);
        }

        public ClientException(String mensagem, Throwable causa) {
            super(mensagem, causa);
        }

    }

