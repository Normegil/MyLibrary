package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.ApplicationProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDateTime;
import java.time.Month;

public class UTCsvParserWithIncompleteAnnotatedObject extends AbstractParserTest<UTCsvParserWithIncompleteAnnotatedObject.IncompleteAnnotatedParsableObject>{

	private static final Long DEFAULT_ID = 15L;
	public static final String ANNOTATED_PARSABLE_OBJECT_NAME = "AnnotatedParsableObjectName";
	public static final String ADDITIONAL_FIELD = "AdditionalField";
	private static Long l = 0L;

	public UTCsvParserWithIncompleteAnnotatedObject() {
		super(IncompleteAnnotatedParsableObject.class);
	}

	@Override
	protected IncompleteAnnotatedParsableObject initNewEntity() {
		IncompleteAnnotatedParsableObject object = new IncompleteAnnotatedParsableObject(new UTBaseCsvParser.ParsableObject(l, ANNOTATED_PARSABLE_OBJECT_NAME + l, LocalDateTime.now()));
		object.setAdditionalField(ADDITIONAL_FIELD + l);
		return object;
	}

	@Override
	protected IncompleteAnnotatedParsableObject initDefaultEntity() {
		IncompleteAnnotatedParsableObject object = new IncompleteAnnotatedParsableObject(new UTBaseCsvParser.ParsableObject(DEFAULT_ID, ANNOTATED_PARSABLE_OBJECT_NAME, LocalDateTime.of(2015, Month.APRIL, 5, 13, 31)));
		object.setAdditionalField(ADDITIONAL_FIELD);
		return object;
	}

	@CsvSchema(readOnly = false, useHeaders = false)
	static class IncompleteAnnotatedParsableObject extends UTBaseCsvParser.ParsableObject {
		private String additionalField;

		public IncompleteAnnotatedParsableObject() {
			super();
		}

		public IncompleteAnnotatedParsableObject(final UTBaseCsvParser.ParsableObject object) {
			super(object.id, object.name, object.localDateTime);
		}

		public void setAdditionalField(final String additionalField) {
			this.additionalField = additionalField;
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
			IncompleteAnnotatedParsableObject rhs = (IncompleteAnnotatedParsableObject) obj;
			return new EqualsBuilder()
					.appendSuper(super.equals(obj))
					.append(this.additionalField, rhs.additionalField)
					.isEquals();
		}

		@Override
		public int hashCode() {
			return new HashCodeBuilder()
					.appendSuper(super.hashCode())
					.append(additionalField)
					.toHashCode();
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
					.appendSuper(super.toString())
					.append("additionalField", additionalField)
					.toString();
		}
	}
}
