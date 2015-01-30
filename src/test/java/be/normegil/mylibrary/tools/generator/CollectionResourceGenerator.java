package be.normegil.mylibrary.tools.generator;

import be.normegil.mylibrary.framework.rest.CollectionResource;
import be.normegil.mylibrary.tools.Generator;
import be.normegil.mylibrary.tools.GeneratorRepository;
import be.normegil.mylibrary.tools.IGenerator;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Generator(CollectionResource.class)
public class CollectionResourceGenerator implements IGenerator<CollectionResource> {

	public static final int DEFAULT_HASHCODE = -642412415;
	private static final IGenerator<URI> GENERATOR = GeneratorRepository.get(URI.class);
	private static final int DEFAULT_LIMIT = 3;
	private static final long DEFAULT_OFFSET = DEFAULT_LIMIT + 1;
	private static final String[] uuids = {
			"01910002-719b-4746-bbad-a439afb2bbfc", "51e2f0d1-eb5a-4f73-bb5f-df7956b165c3", "1c5111b2-8168-47e1-9e1f-73644d588269", "d1de16f6-bb60-4aaf-b14d-804b9ea4fa66",
			"1ac1af8a-c3c5-44a2-bf57-b112372fbf1f", "7f4bf6d2-3c96-4744-b016-9b57c56ad0a6", "35e75692-6b0a-4535-b7cb-54120c649eb6", "3f5b2408-a415-482d-9535-627dba450201",
			"3231a6b8-def5-43b8-b272-8a47b9bd7547", "2f59c29d-8bd0-469a-ab27-8921b42a5d15", "0ce27b9d-ad5c-45e5-bebd-b4afccc26941", "31be9b57-875e-4d6d-8a49-e7830878954b",
			"d28ee857-dd2b-4059-b25f-7fa0d9207f39", "e4ee1e16-88eb-4927-b8e3-9b121fdf9c89", "25df4b59-4851-46ef-86fb-13b85b5543d8", "5c322e9f-25a4-498f-b4b5-78e0be5fe595",
			"7b1c5470-2187-4ce2-aa69-b9004c052f2f", "0474ae3d-4aff-4f88-839a-8419dbc6b438", "6679dc91-1ab7-434c-a35b-52c8620d0d9f", "59e7b469-f382-4a8c-916a-9dedbe04c70e",
			"474f93ac-db7d-4d69-b71a-4bb5d5c269aa", "a4ecfced-0add-4479-bc05-0d2fefcb3836", "0545f75f-68e3-44ae-a620-b212158e87e2", "e81f433d-5ad8-4639-9e74-7ae697e6db2a",
			"a8a9d386-1ebe-46e7-b59f-9cc05ec94825", "f0419dc7-091f-4098-88c5-f8218c8621bf", "5f2e3330-6e0e-4d9e-b987-02ea4feb2e53", "1fffb710-e25e-45b8-a75a-f357c4156735",
			"c62054d9-819e-44f6-8dd6-b74f59115c79", "b2225327-555c-451c-8228-b0a2b63d7795", "33d4df35-207d-4194-9367-48cd336b029c", "8c1626d2-eb23-4488-9759-255fe5d23466",
			"732a5d8e-874b-45d1-9004-78983b41f83c", "0ff9ee90-251d-435a-a2b1-02b3f53d08f0", "319cfe6f-3aab-41d1-9fa1-6076238cc29c", "6f481b41-20d5-41c8-96c6-d9818d31764e",
			"f5da7d50-6216-4d44-97a8-26ea1fc9ee82", "e9811896-7935-4136-9a9e-ee00d4f69b6f", "defec041-9e46-4e53-82b4-c2ab4ead46b4", "3ca2b772-bfcd-4f46-8378-203b4a38446a",
			"f71c893c-7c8f-4a5b-a655-dfaaf61ba0fe", "b964c5f6-fb84-4e83-91f8-b0433010cec0", "cac3c045-9fb3-4535-bae0-ed7b3671ad0e", "19338e24-07ae-4154-86bc-292d45c36432",
			"f694a7e0-777b-48f7-b2c5-c1764806869b", "efc538ea-ffba-4a1e-9ade-bd4e6430183a", "962686e4-03d5-4d19-8b2b-e207dd90c7ae", "fd54a259-8f38-460f-9402-c77573060552",
			"6d31f198-d25b-44e5-9faf-424dbf5bc568", "e894f514-4a39-492f-ada0-d4f872b963d3"
	};

	@Override
	public CollectionResource getDefault(final boolean withLink, final boolean withIds) {
		URI uri = GENERATOR.getDefault(false, false);
		return generateCollectionResource(uri);
	}

	@Override
	public CollectionResource getNew(final boolean withLink, final boolean withIds) {
		URI uri = GENERATOR.getNew(false, false);
		return generateCollectionResource(uri);
	}

	private CollectionResource generateCollectionResource(final URI uri) {
		List<URI> uris = new ArrayList<>();
		for (int i = 0; i < DEFAULT_LIMIT; i++) {
			int index = i % uuids.length;
			uris.add(URI.create(uri.toString() + "/" + uuids[index]));
		}
		return CollectionResource.builder()
				.addAllItems(uris)
				.setOffset(DEFAULT_OFFSET)
				.setLimit(uris.size())
				.setBaseURI(uri)
				.setTotalNumberOfItems(DEFAULT_LIMIT * 3 + 17)
				.build();
	}
}
