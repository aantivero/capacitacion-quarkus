package arg.gov.agip.service;

import arg.gov.agip.model.Tipificacion;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class TipificacionService {

    @Transactional(SUPPORTS)
    public List<Tipificacion> findAll() {
        return Tipificacion.listAll();
    }

    @Transactional(SUPPORTS)
    public Tipificacion findById(Long id) {
        return Tipificacion.findById(id);
    }


    public Tipificacion persistTipificacion(@Valid Tipificacion tipificacion) {
        //todo some business logic
        Tipificacion.persist(tipificacion);
        return tipificacion;
    }

    public Tipificacion updateTipificacion(@Valid Tipificacion tipificacion) {
        Tipificacion entity = Tipificacion.findById(tipificacion.id);
        entity.codigo = tipificacion.codigo;
        entity.descripcion = tipificacion.descripcion;
        return entity;
    }

    public void deleteTipificacion(Long id) {
        Tipificacion tipificacion = Tipificacion.findById(id);
        tipificacion.delete();
    }
}
