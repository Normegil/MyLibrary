package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.Constants;
import be.normegil.mylibrary.dao.DatabaseDAO;
import be.normegil.mylibrary.manga.Manga;

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
