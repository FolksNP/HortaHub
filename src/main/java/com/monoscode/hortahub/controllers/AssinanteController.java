package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.repositories.AssinanteRepository;
import com.monoscode.hortahub.services.AssinanteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("assinante")
public class AssinanteController {

    private AssinanteService service;

    public AssinanteController(AssinanteService service) {
        this.service = service;
    }

    public AssinanteController() {
    }

    @PostMapping("/verificar-numero")
    public HttpStatus validarCadastroTelefone(@PathVariable String numero){

        if(service.verificarCadastro(numero)){
            return HttpStatus.NOT_ACCEPTABLE;
        }else{
            return HttpStatus.ACCEPTED;
        }

    }

    @PostMapping("/cadastrar-assinante")
    public HttpStatus cadastrarAssinante(@RequestBody Assinante assinante){
        if(service.cadastrarNovoCliente(assinante)){
            return HttpStatus.CREATED;
        }else {
            return HttpStatus.UNPROCESSABLE_CONTENT;
        }
    }



}
