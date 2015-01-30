package be.normegil.mylibrary.framework.rest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		UTCollectionResourceSafety.class,
		UTCollectionResource.class,
		UTCollectionResourceEquality.class,
		UTCollectionResourceBuilderSafety.class,
		UTCollectionResourceBuilder.class,
		UTCollectionResourceHelperSafety.class,
		UTCollectionResourceHelper.class,
})
public class CollectionResourceTestSuite {
}