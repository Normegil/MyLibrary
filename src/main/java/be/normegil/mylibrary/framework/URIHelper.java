package be.normegil.mylibrary.framework;

import org.apache.commons.lang3.StringUtils;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;
import java.net.URI;

@Stateless
@LocalBean
public class URIHelper {

	public URI removeParameters(@NotNull final URI uri) {
		String uriWithoutParameters = StringUtils.substringBeforeLast(uri.toString(), "?");
		return URI.create(uriWithoutParameters);
	}

}
