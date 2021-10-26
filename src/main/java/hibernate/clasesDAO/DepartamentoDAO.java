package hibernate.clasesDAO;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.query.Query;

import hibernate.ejercicioHibernate.Departamento;
import hibernate.principal.HibernateUtil;

public class DepartamentoDAO {

	public static Departamento getDepartamento(Session s, int codigo) {
		return s.get(Departamento.class, codigo);
	}
	
	public static void insertDepartamento(Session s, Departamento departamento) {
		departamento.setCodigo(getNewCodigo(s));
		s.save(departamento);
		HibernateUtil.logger.info("Realizada inserccion.");
	}
	
	public static void deleteDepartamento(Session s, Departamento departamento) {
		s.delete(departamento);
		HibernateUtil.logger.info("Realizado borrado.");
	}
	
	public static void updateDepartamento(Session s, Departamento departamento) {
		s.update(departamento);
		HibernateUtil.logger.info("Realizada actualizacion.");
	}
	
	public static int getNewCodigo(Session s) {
		String hql= "SELECT max(codigo) from Departamento";
		Query query = s.createQuery(hql);
		return (int)query.getSingleResult()+1;
	}
	
	public static ArrayList<Departamento> getLista(Session s) {
		String hql= "from Departamento";
		Query query = s.createQuery(hql);
		return (ArrayList<Departamento>) query.list();
	}
}
