package speedorz.crm.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import speedorz.crm.repository.RepositorioVehiculo;

@Service
public class ServicioOrdenCompraConcesionarioImpl {

    private final RepositorioVehiculo repositorioVehiculo;

    @Autowired
    public ServicioOrdenCompraConcesionarioImpl(RepositorioVehiculo repositorioVehiculo) {
        this.repositorioVehiculo = repositorioVehiculo;
    }



}
