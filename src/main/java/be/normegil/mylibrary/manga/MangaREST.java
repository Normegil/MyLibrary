package be.normegil.mylibrary.manga;

import be.normegil.mylibrary.Constants;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.UUID;

@Path("/mangas")
public class MangaREST {

	@Inject
	private MangaDatabaseDAO dao;

	@GET
	@Produces(Constants.MediaType.JSON)
	public Manga get() {
		return dao.get(UUID.fromString("02ecae23-7989-43a5-b26a-086e25ddd988"));
	}

}
