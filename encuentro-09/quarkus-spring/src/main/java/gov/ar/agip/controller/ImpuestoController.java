package gov.ar.agip.controller;

import gov.ar.agip.model.Impuesto;
import gov.ar.agip.repository.ImpuestoJPARepository;
import java.util.List;

import javax.transaction.Transactional;

import gov.ar.agip.service.ImpuestoMessageProducer;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/impuesto-spring")
public class ImpuestoController {

    private final ImpuestoMessageProducer impuestoMessageProducer;

    private final ImpuestoJPARepository impuestoRepository;

    public ImpuestoController(ImpuestoMessageProducer impuestoMessageProducer,
                              ImpuestoJPARepository impuestoRepository) {
        this.impuestoMessageProducer = impuestoMessageProducer;
        this.impuestoRepository = impuestoRepository;
    }

    @GetMapping("/mensaje")
    public String getMensaje() {
        return impuestoMessageProducer.getHeaderMensaje();
    }

    @GetMapping
    public List<Impuesto> findAll() {
        return this.impuestoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Impuesto findById(@PathVariable("id") Long id) {
        return this.impuestoRepository.findById(id).get();
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody Impuesto impuesto) {
        this.impuestoRepository.save(impuesto);
    }

    @PostMapping
    @Transactional
    public Impuesto create(@RequestBody Impuesto impuesto) {
        return this.impuestoRepository.save(impuesto);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable("id") Long id) {
        this.impuestoRepository.deleteById(id);
    }
}
