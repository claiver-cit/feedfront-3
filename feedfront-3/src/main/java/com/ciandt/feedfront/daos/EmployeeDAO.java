package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {

    EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("feedfront");
    EntityManager entityManager;

    public EmployeeDAO() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public List<Employee> listar() {
        List<Employee> listEmployee = entityManager.createQuery("from Employee").getResultList();
        return listEmployee;
    }

    @Override
    public Optional<Employee> buscar(long id) {
        Employee em = entityManager.find(Employee.class, id);
        return Optional.ofNullable(em);
    }

    @Override
    public Employee salvar(Employee employee) {
        entityManager.getTransaction().begin();
        entityManager.persist(employee);
        entityManager.getTransaction().commit();
        entityManager.clear();
        return employee;
    }

    @Override
    public boolean apagar(long id)  {
        Optional<Employee> employee = buscar(id);

        if(employee.isPresent()){
            entityManager.getTransaction().begin();
            entityManager.remove(employee.get());
            entityManager.getTransaction().commit();
            entityManager.clear();
            return true;
        }

        return false;

    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager  = entityManager;
    }
}
