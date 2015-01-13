package be.normegil.mylibrary.framework.parser;

import be.normegil.mylibrary.framework.exception.IORuntimeException;
import be.normegil.mylibrary.framework.exception.ReadOnlyRuntimeException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CsvParser<E> {

	private static final char SEPARATOR = ';';

	private Class<? extends E> entityClass;

	public CsvParser(Class<? extends E> entityClass) {
		this.entityClass = entityClass;
	}

	public List<E> from(@NotNull final InputStream stream) {
		CsvMapper mapper = new CsvMapper();
		com.fasterxml.jackson.dataformat.csv.CsvSchema schema = getSchema(mapper)
				.withSkipFirstDataRow(useHeader());
		ObjectReader reader = mapper.reader(entityClass).with(schema);
		MappingIterator<Object> iterator;
		try {
			iterator = reader.readValues(stream);
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}

		List<E> entities = new ArrayList<>();
		while (iterator.hasNext()) {
			entities.add((E) iterator.next());
		}
		return entities;
	}

	public void to(@NotNull final List<E> entities, @NotNull final File file) {
		if (isReadOnly()) {
			throw new ReadOnlyRuntimeException("Entity of type " + entityClass.getSimpleName() + " cannot be printed in CSV (But you can read them from CSV)");
		} else {
			CsvMapper mapper = new CsvMapper();
			com.fasterxml.jackson.dataformat.csv.CsvSchema schema = getSchema(mapper)
					.withUseHeader(useHeader());
			ObjectWriter writer = mapper.writer(schema);
			try {
				writer.writeValue(file, entities);
			} catch (IOException e) {
				throw new IORuntimeException(e);
			}
		}
	}

	protected boolean isReadOnly() {
		CsvSchema csvSchema = entityClass.getAnnotation(CsvSchema.class);
		if (csvSchema != null && csvSchema.columns().length > 0) {
			return csvSchema.readOnly();
		} else {
			return CsvSchema.DEFAULT_READ_ONLY;
		}
	}

	protected com.fasterxml.jackson.dataformat.csv.CsvSchema getSchema(CsvMapper mapper) {
		CsvSchema csvSchema = entityClass.getAnnotation(CsvSchema.class);
		if (csvSchema != null && csvSchema.columns().length > 0) {
			com.fasterxml.jackson.dataformat.csv.CsvSchema.Builder builder = com.fasterxml.jackson.dataformat.csv.CsvSchema.builder();
			String[] columns = csvSchema.columns();
			for (String column : columns) {
				builder.addColumn(column);
			}
			return builder.setColumnSeparator(SEPARATOR).build();
		} else {
			return mapper.schemaFor(entityClass);
		}
	}

	protected boolean useHeader() {
		CsvSchema csvSchema = entityClass.getAnnotation(CsvSchema.class);
		if (csvSchema != null) {
			return csvSchema.useHeaders();
		} else {
			return CsvSchema.DEFAULT_USE_HEADER;
		}
	}
}
