package rn;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

public abstract class AbstractRN<T> {
    private Class<T> entidadeClasse;
    
    protected abstract EntityManager getManager();
    
    public AbstractRN(Class<T> entidadeClasse){
        this.entidadeClasse = entidadeClasse;
    }
    
    public void adicionar(T entidade){
        getManager().persist(entidade);
    }
    
    public void atualizar(T entidade){
        getManager().merge(entidade);
    }
    
    public void remover(T entidade){
        getManager().remove(getManager().merge(entidade));
    }
    
    public T buscar(Long id){
        return getManager().find(entidadeClasse, id);
    }
    
    public List<T> listar(){
        CriteriaQuery cq = 
                getManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entidadeClasse));
        return(getManager().createQuery(cq).getResultList());
    }
}
