/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.examples.productorders.repositories;

import edu.eci.cosw.examples.productorders.services.ServicesException;
import edu.eci.cosw.samples.model.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/**
 *
 * @author Desarrollo
 */
public interface ClientsRepository extends JpaRepository <Cliente, Integer> {
    
    @Query("select ped.cliente from Pedido ped left join ped.detallesPedidos as det left join det.producto as pro with pro.precio > :n")
    public List<Cliente> getClientsByPrice(@Param("n") Long n) throws ServicesException;
}
