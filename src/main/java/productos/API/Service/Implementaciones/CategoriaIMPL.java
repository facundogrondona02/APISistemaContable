package productos.API.Service.Implementaciones;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productos.API.Model.DAO.ICategoriaDAO;
import productos.API.Model.DAO.IUserDAO;
import productos.API.Model.DTO.CategoriaDTO;
import productos.API.Model.Entity.Categoria;
import productos.API.Model.Entity.User;
import productos.API.Service.ICategoriaService;

import java.util.ArrayList;

@Service
public class CategoriaIMPL implements ICategoriaService {


    @Autowired
    private ICategoriaDAO categoriaDAO;

    @Autowired
    private ObtenerUsernameToken obtenerUsernameToken;

    @Autowired
    private IUserDAO userDAO;

    @Transactional
    @Override
    public Categoria save(CategoriaDTO categoriaDTO) {
        String username = obtenerUsernameToken.findUserByToken();
        User user = userDAO.findByUsername(username).orElseThrow();
        Categoria categoriaSave = Categoria.builder()
                .ID_Categoria(categoriaDTO.getID_Categoria())
                .Categoria(categoriaDTO.getCategoria())
                .user(user)
                .build();

        return categoriaDAO.save(categoriaSave);
    }



    @Transactional(readOnly = true)
    @Override
    public Categoria findById(Integer id) {
        return categoriaDAO.findById(id).orElse(null);
    }


    @Transactional
    @Override
    public void delete(Categoria categoria) {
         categoriaDAO.delete(categoria);
    }

    @Transactional
    @Override
    public boolean existsById(Integer id) {
        return categoriaDAO.existsById(id);
    }

    @Transactional
    @Override
    public Iterable<Categoria> findAll() {
        String username = obtenerUsernameToken.findUserByToken();
    return categoriaDAO.findByUser_Username(username);
    }

    @Transactional
    @Override
    public ArrayList<Categoria> findByName(String categoria) {
        String nombreMayus = categoria.toUpperCase();
        Iterable<Categoria> listaCat = categoriaDAO.findAll();
        ArrayList<Categoria> catEncontrada = new ArrayList<>();
        for(Categoria cat : listaCat){

            String nombreCategoria = cat.getCategoria();
            if(nombreCategoria != null && nombreCategoria.contains(nombreMayus)){
                catEncontrada.add(cat);
            }

        }
        return catEncontrada;
    }


}
