package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        this.dao = new EmployeeDAO();
    }

    @Override
    public List<Employee> listar() {
        return dao.listar();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {
        return dao.buscar(id).orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        if(listar().stream().anyMatch(a -> a.getEmail().equals(employee.getEmail()))){
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        return dao.salvar(employee);
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        return null;
    }

    @Override
    public void apagar(long id) throws BusinessException {
        buscar(id);
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}