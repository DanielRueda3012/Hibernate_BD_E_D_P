package repositories.departamento;

import java.util.List;
import java.util.logging.Logger;

import dao.HibernateManager;
import io.IO;
import jakarta.persistence.TypedQuery;
import model.Departamento;

public class DepartamanetosRepositoriesImpl implements DepartamentosRepositories {
	private final Logger logger = Logger.getLogger(DepartamanetosRepositoriesImpl.class.getName());

	@Override
	public List<Departamento> findAll() {
		logger.info("findAll()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		TypedQuery<Departamento> query = hb.getManager().createNamedQuery("Departamento.findAll", Departamento.class);
		List<Departamento> list = query.getResultList();
		hb.close();
		return list;
	}

	@Override
	public Departamento findById(Integer id) {
		logger.info("findById()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			Departamento depatartamento = hb.getManager().find(Departamento.class, id);
			hb.close();
			return depatartamento;
		   } catch (Exception e) {
	            IO.print(e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
		return null;
	}

	@Override
	public Departamento save(Departamento entity) {
		logger.info("save()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		hb.getTransaction().begin();

		try {
			hb.getManager().merge(entity);
			hb.getTransaction().commit();
			hb.close();
			return entity;
		   } catch (Exception e) {
	            IO.print(e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
		return null;
	}

	@Override
	public Boolean delete(Departamento entity) {
		logger.info("delete()");
		HibernateManager hb = HibernateManager.getInstance();
		hb.open();
		try {
			hb.getTransaction().begin();
			// Ojo que borrar implica que estemos en la misma sesión y nos puede dar
			// problemas, por eso lo recuperamos otra vez
			entity = hb.getManager().find(Departamento.class, entity.getId());
			hb.getManager().remove(entity);
			hb.getTransaction().commit();
			hb.close();
			return true;
		   } catch (Exception e) {
	            IO.print(e.getMessage());
		} finally {
			if (hb.getTransaction().isActive()) {
				hb.getTransaction().rollback();
			}
		}
		return false;
	}
}