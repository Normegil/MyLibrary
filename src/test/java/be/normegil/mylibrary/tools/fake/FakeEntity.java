package be.normegil.mylibrary.tools.fake;

import be.normegil.mylibrary.framework.Entity;
import be.normegil.mylibrary.framework.rest.RESTService;

import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.UUID;

public class FakeEntity extends Entity {

	@Path("/fakeentities")
	public static class RestService implements RESTService<FakeEntity> {
		@Override
		public Response getAll(@Context final UriInfo info, final Long offset, final Integer limit) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response get(@Context final UriInfo info, final UUID id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response create(@Context final UriInfo info, final FakeEntity entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response updateByPUT(final UUID id, final FakeEntity entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response updateByPOST(@Context final UriInfo info, final UUID id, final FakeEntity entity) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Response delete(final UUID id) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Class<? extends FakeEntity> getSupportedClass() {
			return FakeEntity.class;
		}
	}

}
