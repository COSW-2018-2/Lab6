/*
 * Copyright (C) 2016 Pivotal Software, Inc.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.cosw.examples.productorders.services;

import edu.eci.cosw.examples.productorders.repositories.ClientsRepository;
import edu.eci.cosw.examples.productorders.repositories.DispatchRepository;
import edu.eci.cosw.examples.productorders.repositories.OrdersRepository;
import edu.eci.cosw.examples.productorders.repositories.ProductsRepository;
import edu.eci.cosw.examples.productorders.repositories.VehiclesRepository;
import edu.eci.cosw.samples.model.Cliente;
import edu.eci.cosw.samples.model.Despacho;
import edu.eci.cosw.samples.model.Producto;
import edu.eci.cosw.samples.model.Vehiculo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import edu.eci.cosw.samples.model.Pedido;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hcadavid
 */
@Service
public class ApplicationServicesImpl implements ApplicationServices{
   
    @Autowired
    private OrdersRepository ordrepo;

    @Autowired
    private ProductsRepository prorepo;
    
    @Autowired
    private DispatchRepository disprepo;
    
    @Autowired
    private VehiclesRepository vehirepo;
    
    @Autowired
    private ClientsRepository clierepo;
    
    @Override
    public List<Pedido> getAllOrders() throws ServicesException{
        List<Pedido> p=ordrepo.findAll();
        return p;
    }

    @Override
    public List<Producto> getAllProducts() throws ServicesException{
        return  prorepo.findAll();
    }

    @Override
    public Pedido orderById(Integer id) throws ServicesException{
        return ordrepo.findOne(id);
    }
    
    
    @Override
    public Despacho dispatchByID(Integer id) throws ServicesException{
        return disprepo.findOne(id);
    }
    
    @Override
    public List<Vehiculo> getVehiclesByProductId(Integer productId) throws ServicesException {
        return vehirepo.getVehiclesByProductId(productId);
    }
    
    @Override
    public List<Cliente> getClientsByPrice(Long price) throws ServicesException {
        return clierepo.getClientsByPrice(price);
    }

    @Override
    public void addDispatch(MultipartHttpServletRequest request, int idPedido, String idVehiculo) throws SQLException, IOException, ServicesException {
        Iterator<String> itr = request.getFileNames();
        while (itr.hasNext()) {
            String uploadedFile = itr.next();
            MultipartFile file = request.getFile(uploadedFile);

            Pedido p = ordrepo.findOne(idPedido);
            Vehiculo v = vehirepo.findOne(idVehiculo);

            Despacho d = new Despacho(p, v);

            d.setQrcode(new SerialBlob(StreamUtils.copyToByteArray(file.getInputStream())));

            disprepo.save(d);
        }
        
    }

    
}
