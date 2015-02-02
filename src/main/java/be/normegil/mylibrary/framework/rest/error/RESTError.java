package be.normegil.mylibrary.framework.rest.error;

import be.normegil.mylibrary.ApplicationProperties;
import be.normegil.mylibrary.WarningTypes;
import be.normegil.mylibrary.framework.constraint.NotEmpty;
import be.normegil.mylibrary.framework.parser.CsvSchema;
import be.normegil.mylibrary.framework.parser.adapter.LocalDateTimeJsonSerializer;
import be.normegil.mylibrary.framework.rest.HttpStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDateTime;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@CsvSchema(readOnly = true, columns = {
		"code",
		"httpStatus",
		"moreInfoUri",
		"message",
		"developerMessage"
})
public class RESTError {

	@NotNull
	private int httpStatus;
	@Min(1)
	private int code;
	@NotEmpty
	private String message;
	@NotEmpty
	private String developerMessage;
	@NotNull
	@Valid
	private URI moreInfoUri;
	@JsonSerialize(using = LocalDateTimeJsonSerializer.class)
	private LocalDateTime time;

	private RESTError(@NotNull @Valid final Builder builder) {
		code = builder.code;
		httpStatus = builder.status;
		message = builder.message;
		developerMessage = builder.developerMessage;
		moreInfoUri = builder.moreInfoUri;
		time = builder.time;
	}

	public RESTError(@NotNull @Valid final RESTError error) {
		code = error.code;
		httpStatus = error.httpStatus;
		message = error.message;
		developerMessage = error.developerMessage;
		moreInfoUri = error.moreInfoUri;
		time = error.time;
	}

	// For Parsers
	@SuppressWarnings(WarningTypes.UNUSED)
	public RESTError() {
	}

	public int getCode() {
		return code;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public static Builder builder() {
		return new Builder();
	}

	public RESTError withTime(@NotNull @Valid LocalDateTime time) {
		RESTError error = new RESTError(this);
		error.time = time;
		return error;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		RESTError rhs = (RESTError) obj;
		return new EqualsBuilder()
				.append(this.code, rhs.code)
				.append(this.time, rhs.time)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(code)
				.append(time)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
				.append("httpStatus", httpStatus)
				.append("code", code)
				.append("message", message)
				.append("developerMessage", developerMessage)
				.append("moreInfoUri", moreInfoUri)
				.append("time", time)
				.toString();
	}

	public static class Builder {
		@NotNull
		private int status;
		@Min(1)
		private int code;
		@NotEmpty
		private String message;
		@NotEmpty
		private String developerMessage;
		@NotNull
		private URI moreInfoUri;
		private LocalDateTime time;

		public Builder from(@NotNull @Valid RESTError error) {
			code = error.code;
			status = error.httpStatus;
			message = error.message;
			developerMessage = error.developerMessage;
			moreInfoUri = error.moreInfoUri;
			time = error.time;
			return this;
		}

		public Builder setCode(@Min(1) final int code) {
			this.code = code;
			return this;
		}

		public Builder setHttpStatus(@NotNull final HttpStatus status) {
			this.status = status.value();
			return this;
		}

		public Builder setMessage(@NotEmpty final String message) {
			this.message = message;
			return this;
		}

		public Builder setDeveloperMessage(@NotEmpty final String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public Builder setMoreInfoURL(@NotNull @Valid final URI moreInfoUri) {
			this.moreInfoUri = moreInfoUri;
			return this;
		}

		public Builder setTime(@NotNull final LocalDateTime time) {
			this.time = time;
			return this;
		}

		public
		@Valid
		RESTError build() {
			return new RESTError(this);
		}
	}
}
