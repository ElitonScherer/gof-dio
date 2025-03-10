package br.prodata.gof.Service.impl;

import br.prodata.gof.Service.ClienteService;
import br.prodata.gof.Service.ViaCepService;
import br.prodata.gof.model.Cliente;
import br.prodata.gof.model.ClienteRepository;
import br.prodata.gof.model.Endereco;
import br.prodata.gof.model.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;
    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente= clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {

        salvarCliente(cliente);


    }

    private void salvarCliente(Cliente cliente) {
        String cep= cliente.getEndereco().getCep();

        Endereco endereco= enderecoRepository.findById(cep).orElseGet(()->{
            //caso não exista intregamos com o viacep
            Endereco novoEndereco=viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;
        });

        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> optionalCliente=clienteRepository.findById(id);
        if(optionalCliente.isPresent()){
            salvarCliente(cliente);

        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
