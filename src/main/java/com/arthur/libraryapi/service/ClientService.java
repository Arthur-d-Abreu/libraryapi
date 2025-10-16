package com.arthur.libraryapi.service;

import com.arthur.libraryapi.model.Client;
import com.arthur.libraryapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhhaCriptografada);
        return clientRepository.save(client);
    }

    public Client obterClientPorId(String clientId){
        return clientRepository.findByClientId(clientId);
    }
}
