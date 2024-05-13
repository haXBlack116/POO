import java.util.List;

public interface Gerenciador<T> {

public void cadastrar(T objeto);
public void editar(String id, T novoObjeto);
public List<T> listar();
public T buscarId(String id);
public boolean remover(String id);
public boolean existe(String id);


}


