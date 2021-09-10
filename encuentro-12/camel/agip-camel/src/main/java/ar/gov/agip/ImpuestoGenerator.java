package ar.gov.agip;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

@ApplicationScoped
@Named
public class ImpuestoGenerator implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("TEST");
        Random random = new Random();
        List<Impuesto> impuestos = new ArrayList<>();

        for (int i = 0; i < 100; i++) {

            Impuesto impuesto = new Impuesto(i, "test", "test");
            impuestos.add(impuesto);
        }

        exchange.getMessage().setBody(impuestos);
    }
}
