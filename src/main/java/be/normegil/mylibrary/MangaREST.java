package be.normegil.mylibrary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/mangas")
public class MangaREST {

	@GET
	public String get(){
		return "test";
	}

}
