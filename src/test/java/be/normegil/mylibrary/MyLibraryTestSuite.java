package be.normegil.mylibrary;

import be.normegil.mylibrary.manga.MangaPackageTestSuite;
import be.normegil.mylibrary.framework.dao.DAOPackageTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		MangaPackageTestSuite.class,
		DAOPackageTestSuite.class
})
public class MyLibraryTestSuite {
}
