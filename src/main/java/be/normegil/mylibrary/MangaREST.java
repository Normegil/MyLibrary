package be.normegil.mylibrary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/mangas")
public class MangaREST {

	@GET
	@Produces(Constants.MediaType.JSON)
	public Manga get() {
		DatabaseDAO<Manga> dao = new DatabaseDAO<>(Manga.class);
		return dao.get(1L);
	}

}
