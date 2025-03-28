package br.prodata.gof.Service;

import br.prodata.gof.model.Endereco;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "viacep",url = "https://viacep.com.br/ws")
public interface ViaCepService {

    //poderia só colocar isso
    @GetMapping("/{cep}/json/")
    //@RequestMapping(method = RequestMethod.GET,value = "/{cep}/json/")
    Endereco consultarCep(@PathVariable("cep") String cep);
}
