/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cosw.examples.productorders.repositories;

import edu.eci.cosw.examples.productorders.services.ServicesException;
import edu.eci.cosw.samples.model.Vehiculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Desarrollo
 */
public interface VehiclesRepository extends JpaRepository<Vehiculo, String>{
    
    @Query("select des.vehiculo.placa from Despacho des inner join des.pedidos as ped  inner join ped.detallesPedidos as det inner join det.producto as pro with pro.id = :pi")
    public List<Vehiculo> getVehiclesByProductId(@Param("pi") Integer pi) throws ServicesException;
}
