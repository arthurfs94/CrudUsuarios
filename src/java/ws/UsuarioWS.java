package ws;

import entidades.Usuario;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import rn.UsuarioRN;

@Path("usuarios")
public class UsuarioWS {

    @EJB
    private UsuarioRN usuarioRN;

    @Context
    private UriInfo context;

    public UsuarioWS() {
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Usuario> getUsuarios() {
        return usuarioRN.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Usuario getUsuario(@PathParam("id") long id) {
        return usuarioRN.buscar(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void adicionarUsuario(Usuario u, @Context final HttpServletResponse response) {
        usuarioRN.salvar(u);
        //Alterar o codigo para 201 (Created)
        response.setStatus(HttpServletResponse.SC_CREATED);
        try {
            response.flushBuffer();
        } catch (IOException e) {
            //Erro 500
            throw new InternalServerErrorException();
        }

    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public void alterarUsuario(@PathParam("id") long id, Usuario usuario) {
        Usuario u =usuarioRN.buscar(id);
        u.setNome(usuario.getNome());
        u.setEmail(usuario.getEmail());
        u.setTelefone(usuario.getTelefone());
        usuarioRN.atualizar(u);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Usuario removerUsuario(@PathParam("id") long id) {
        Usuario u =usuarioRN.buscar(id);
        usuarioRN.remover(u);
        return u;
    }
}
