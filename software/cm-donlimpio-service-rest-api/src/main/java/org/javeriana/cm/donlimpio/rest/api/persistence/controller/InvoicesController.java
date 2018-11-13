package org.javeriana.cm.donlimpio.rest.api.persistence.controller;

import org.javeriana.cm.donlimpio.rest.api.persistence.entity.Invoices;
import org.javeriana.cm.donlimpio.rest.api.persistence.repository.InvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class InvoicesController {

    @Autowired
    private InvoicesRepository invoicesRepository;

    public Invoices saveInvoice(Invoices invoices) {
        return invoicesRepository.save(invoices);
    }
}
