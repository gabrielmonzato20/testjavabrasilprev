package com.brasilprev.resource;

import com.brasilprev.model.Client;
import com.brasilprev.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/v1/clients")
public class ClientResource {



        @Autowired
        private ClientService clientService;

        @GetMapping
        public ResponseEntity<List<Client>> findAll() {
            log.info("Start-findall");
            return ResponseEntity.ok().body(clientService.findall());

        }

        @GetMapping("/{id}")
        public ResponseEntity<Client> findById(@PathVariable Long id) {
            log.info("Start-findById");

            Client client = clientService.findById(id);
            log.info("End-findById");

            return ResponseEntity.ok().body(client);
        }

        @PostMapping
        public ResponseEntity<Void> save(@RequestBody Client client) {
            log.info("Start-save");
            try {
                client = clientService.save(client);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.notFound().build();

            }
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
            log.info("End-save");
            return ResponseEntity.created(uri).build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Client client) {
            log.info("Start-update");
            client.setId(id);
            try{
            clientService.update(client);
            }catch (Exception e){
                e.printStackTrace();
                return ResponseEntity.notFound().build();

            }
            log.info("Start-update");
            return ResponseEntity.noContent().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
            log.info("Start-delete");
            clientService.delete(id);
            log.info("Start-delete");
            return ResponseEntity.noContent().build();
        }

    }

