package demo.isarbela.projectpatterns.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import demo.isarbela.projectpatterns.model.Address;

@FeignClient(name = "viacep", url = "http://viacep.com.br/ws")
public interface ViaCepService {

    @GetMapping(value = "/{cep}/json/")
    Address searchCep(@PathVariable("cep") String cep);
}
