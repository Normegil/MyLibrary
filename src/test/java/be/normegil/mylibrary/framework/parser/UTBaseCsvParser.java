package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.framework.parser.adapter.LocalDateTimeJsonDeserializer;
import be.normegil.mylibrary.framework.parser.adapter.LocalDateTimeJsonSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.time.LocalDateTime;
import java.time.Month;

public class UTBaseCsvParser extends AbstractParserTest<UTBaseCsvParser.ParsableObject> {

	private static final Long DEFAULT_ID = 15L;
	public static final String DEFAULT_NAME = "ParsableObjectName";
	public static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(2015, Month.JANUARY, 20, 8, 41);
	private static long l = 0;

	public UTBaseCsvParser() {
		super(ParsableObject.class);
	}

	@Override
	protected ParsableObject initNewEntity() {
		l++;
		return new ParsableObject(l, DEFAULT_NAME + l, LocalDateTime.now());
	}

	@Override
	protected ParsableObject initDefaultEntity() {
		return new ParsableObject(DEFAULT_ID, DEFAULT_NAME, DEFAULT_DATE);
	}

	@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
	static class ParsableObject {
		protected long id;
		protected String name;
		@JsonSerialize(using = LocalDateTimeJsonSerializer.class)
		@JsonDeserialize(using = LocalDateTimeJsonDeserializer.class)
		protected LocalDateTime localDateTime;

		public ParsableObject() {
		}

		public ParsableObject(final Long id, final String name, final LocalDateTime localDateTime) {
			this.id = id;
			this.name = name;
			this.localDateTime = localDateTime;
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
			ParsableObject rhs = (ParsableObject) obj;
			return new EqualsBuilder()
					.append(this.id, rhs.id)
					.append(this.name, rhs.name)
					.append(this.localDateTime, rhs.localDateTime)
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder()
					.append(id)
					.append(name)
					.append(localDateTime)
					.toHashCode();
		}

		@Override
		public String toString() {
			return "ParsableObject{" +
					"id=" + id +
					", name='" + name + '\'' +
					", localDateTime=" + localDateTime +
					'}';
		}
	}
}
