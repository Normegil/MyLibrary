package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.security.rightsmanagement.resource.SpecificResource;
import be.normegil.mylibrary.manga.MangaREST;
import be.normegil.mylibrary.tools.EntityHelper;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;
import be.normegil.mylibrary.user.User;

import java.util.UUID;


@Generator(SpecificResource.class)
public class SpecificResourceGenerator implements IGenerator<SpecificResource> {

	private static final IGenerator<User> USER_GENERATOR = GeneratorRepository.get(User.class);
	private static int index = 0;

	@Override
	public SpecificResource getDefault(final boolean withLink, final boolean withIds) {
		SpecificResource specificResource = new SpecificResource(
				MangaREST.class,
				"1"
		);

		if (withLink) {
			specificResource = new SpecificResource(
					specificResource.getRestService(),
					specificResource.getRessourceID(),
					USER_GENERATOR.getDefault(false, withIds)
			);
		}

		if (withIds) {
			new EntityHelper().setId(specificResource, UUID.fromString("f6ead09e-e011-4c0c-b7dd-eceb9ae0924c"));
		}
		return specificResource;
	}

	@Override
	public SpecificResource getNew(final boolean withLink, final boolean withIds) {
		int index = getIndex();
		SpecificResource specificResource = new SpecificResource(
				MangaREST.class,
				Integer.toString(index)
		);

		if (withLink) {
			specificResource = new SpecificResource(
					specificResource.getRestService(),
					specificResource.getRessourceID(),
					USER_GENERATOR.getNew(false, withIds)
			);
		}

		if (withIds) {
			new EntityHelper().setId(specificResource, UUID.randomUUID());
		}
		return specificResource;
	}

	public synchronized int getIndex() {
		index += 1;
		return index;
	}
}
