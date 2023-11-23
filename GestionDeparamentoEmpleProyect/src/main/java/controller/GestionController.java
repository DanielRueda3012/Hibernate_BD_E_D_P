package controller;

import java.util.List;
import java.util.logging.Logger;

import model.Departamento;
import model.Empleado;
import model.Proyecto;
import repositories.departamento.DepartamentosRepositories;
import repositories.empleado.EmpleadosRepository;
import repositories.proyecto.ProyectosRepositories;

public class GestionController {
    private final Logger logger = Logger.getLogger(GestionController.class.getName());

    // Mis dependencias
    private final DepartamentosRepositories depRepository;
    private final EmpleadosRepository empleRepository;
    private final ProyectosRepositories proyectoRepository;

    public GestionController(DepartamentosRepositories deparatamentosRepository, 
    		EmpleadosRepository empleRepository, ProyectosRepositories proyRepository) {
        this.depRepository = deparatamentosRepository;
        this.empleRepository = empleRepository;
        this.proyectoRepository = proyRepository;
    }

    // Departamentos
    public List<Departamento> mostrarDepartamentos() {
        logger.info("Mostrando departamentos");
        return depRepository.findAll();
    }

    public Departamento crearDepartamento(Departamento departamento) {
        logger.info("Creando departamento");
        return depRepository.save(departamento);
    }

    public Departamento mostrarDepartamentoPorId(Integer id) {
        logger.info("Mostrando departamento con ID: " + id);
        return depRepository.findById(id);
    }

    public Departamento actualizarDepartamento(Departamento departamento) {
        logger.info("Actualizando departamento con ID: " + departamento.getId());
        return depRepository.save(departamento);
    }

    public Boolean borrarDepartamento(Departamento dep) {
        logger.info("Eliminando departamento");
        return depRepository.delete(dep);
    }

    // Empleado
    public List<Empleado> mostrarEmpleados() {
        logger.info("Mostrando empleados");
        return empleRepository.findAll();
    }

    public Empleado crearEmpleado(Empleado empleado) {
        logger.info("Creando empleado");
        return empleRepository.save(empleado);
    }

    public Empleado mostrarEmpleadoPorId(Integer id) {
        logger.info("Mostrando empleado con ID: " + id);
        return empleRepository.findById(id);
    }
    
    public Empleado actualizarEmpleado(Empleado empleado) {
        logger.info("Actualizando empleado con ID: " + empleado.getId());
        return empleRepository.save(empleado);
    }

    public Boolean borrarEmpleado(Empleado empleado) {
        logger.info("Eliminando empleado con ID: " + empleado.getId());
        return empleRepository.delete(empleado);
    }
    
 // Proyecto
    public List<Proyecto> mostrarProyectos() {
        logger.info("Obteniendo proyectos");
        return proyectoRepository.findAll();
    }

    public Proyecto crearProyecto(Proyecto proyecto) {
        logger.info("Creando proyecto");
        return proyectoRepository.save(proyecto);
    }

    public Proyecto mostrarProyectoPorId(Integer id) {
        logger.info("Obteniendo proyecto con ID: " + id);
        return proyectoRepository.findById(id);
    }

    public Proyecto actualizarProyecto(Proyecto proyecto) {
        logger.info("Actualizando proyecto con ID: " + proyecto.getId());
        return proyectoRepository.save(proyecto);
    }

    public Boolean borrarProyecto(Proyecto proyecto) {
        logger.info("Eliminando proyecto con ID: " + proyecto.getId());
        return proyectoRepository.delete(proyecto);
    }
}