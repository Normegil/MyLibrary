package be.normegil.mylibrary.util.rest;

import be.normegil.mylibrary.ApplicationProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CollectionResource {

	private static final long FIRST_OFFSET = 0L;

	@Min(0)
	private long offset;
	@Min(1)
	private int limit;
	@Min(0)
	private long totalNumberOfItems;

	@NotNull
	private URI first;
	@NotNull
	private URI last;
	private URI previous;
	private URI next;

	private List<URI> items;

	protected CollectionResource(@Valid Init<?> init) {
		Helper helper = helper();
		offset = helper.getRealOffset(init.offset);
		if (!offsetIsConsistant(offset, init.totalNumberOfItems)) {
			throw new IllegalArgumentException("Offset inconsitancy [Offset=" + init.offset + ";TotalNumberOfItems=" + init.totalNumberOfItems + "]");
		}
		limit = helper.getRealLimit(init.limit);
		totalNumberOfItems = init.totalNumberOfItems;

		first = helper.getCollectionURL(init.baseURI, FIRST_OFFSET, limit);
		last = helper.generateLastURL(init.baseURI, limit, totalNumberOfItems);
		previous = helper.generatePreviousURL(init.baseURI, offset, limit);
		next = helper.generateNextURL(init.baseURI, offset, limit, totalNumberOfItems);

		items = init.items;
	}

	public CollectionResource(final CollectionResource resource) {
		offset = resource.getOffset();
		limit = resource.getLimit();
		first = resource.getUriToFirstPage();
		last = resource.getUriToLastPage();
		previous = resource.getUriToPreviousPage();
		next = resource.getUriToNextPage();
		items = new ArrayList<>(resource.getItems());
		totalNumberOfItems = resource.getTotalNumberOfItems();
	}

	public static Builder builder() {
		return new Builder();
	}

	public static Helper helper() {
		return new Helper();
	}

	public long getOffset() {
		return offset;
	}

	public int getLimit() {
		return limit;
	}

	public long getTotalNumberOfItems() {
		return totalNumberOfItems;
	}

	public URI getUriToFirstPage() {
		return first;
	}

	public URI getUriToPreviousPage() {
		return previous;
	}

	public URI getUriToNextPage() {
		return next;
	}

	public URI getUriToLastPage() {
		return last;
	}

	public List<URI> getItems() {
		return new ArrayList<>(items);
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
		CollectionResource rhs = (CollectionResource) obj;
		return new EqualsBuilder()
				.append(this.offset, rhs.offset)
				.append(this.limit, rhs.limit)
				.append(this.first, rhs.first)
				.append(this.last, rhs.last)
				.append(this.totalNumberOfItems, rhs.totalNumberOfItems)
				.append(this.previous, rhs.previous)
				.append(this.next, rhs.next)
				.append(this.items, rhs.items)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(offset)
				.append(limit)
				.append(first)
				.append(last)
				.append(totalNumberOfItems)
				.append(previous)
				.append(next)
				.append(items)
				.toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ApplicationProperties.TO_STRING_STYLE)
				.append("offset", offset)
				.append("limit", limit)
				.append("totalNumberOfItems", totalNumberOfItems)
				.toString();
	}

	private boolean offsetIsConsistant(final Long offset, final Long totalNumberOfItems) {
		return (offset == 0 && offset.equals(totalNumberOfItems))
				|| offset < totalNumberOfItems;
	}

	public abstract static class Init<E extends Init<E>> {
		private Long offset;
		private Integer limit;
		@NotNull
		@Min(0)
		private Long totalNumberOfItems;
		@NotNull
		private URI baseURI;

		private List<URI> items = new ArrayList<>();

		protected abstract E self();

		public E from(final CollectionResource resource) {
			items = new ArrayList<>(resource.getItems());
			offset = resource.getOffset();
			limit = resource.getLimit();
			totalNumberOfItems = resource.getTotalNumberOfItems();
			baseURI = helper().getBaseURL(resource.getUriToFirstPage());
			return self();
		}

		public E setOffset(@Min(0) final long offset) {
			this.offset = offset;
			return self();
		}

		public E setLimit(@Min(1) final int limit) {
			this.limit = limit;
			return self();
		}

		public E setBaseURI(@NotNull final URI baseURI) {
			this.baseURI = baseURI;
			return self();
		}

		public E setTotalNumberOfItems(@Min(0) final long totalNumberOfItems) {
			this.totalNumberOfItems = totalNumberOfItems;
			return self();
		}

		public E addAllItems(@NotNull final List<URI> items) {
			for (URI item : items) {
				addItem(item);
			}
			return self();
		}

		public E addItem(@NotNull final URI item) {
			items.add(item);
			return self();
		}

		public
		@Valid
		CollectionResource build() {
			return new CollectionResource(this);
		}
	}

	public static class Helper {
		private URI generatePreviousURL(@NotNull URI baseURL, long offset, int limit) {
			if (offset != FIRST_OFFSET) {
				long previousOffset;
				if (offset - limit < FIRST_OFFSET) {
					previousOffset = FIRST_OFFSET;
				} else {
					previousOffset = offset - limit;
				}
				return getCollectionURL(baseURL, previousOffset, limit);
			}
			return null;
		}

		private URI generateNextURL(URI baseURL, long offset, int limit, long totalNumberOfItems) {
			if (offset + limit < totalNumberOfItems) {
				return getCollectionURL(baseURL, offset + limit, limit);
			}
			return null;
		}

		private URI generateLastURL(URI baseURL, int limit, long totalNumberOfItems) {
			long numberOfPagesMinusOne = totalNumberOfItems / limit;
			if (totalNumberOfItems % limit == 0) {
				numberOfPagesMinusOne -= 1;
			}
			long lastOffset = numberOfPagesMinusOne * limit;
			return getCollectionURL(baseURL, lastOffset, limit);
		}

		public URI getCollectionURL(@NotNull final URI baseURI, final long offset, final int limit) {
			return URI.create(baseURI.toString() + "?offset=" + offset + "&limit=" + limit);
		}

		public URI getBaseURL(@NotNull final URI collectionURI) {
			String baseURIString = StringUtils.substringBefore(collectionURI.toString(), "/");
			return URI.create(baseURIString);
		}

		public long getRealOffset(final Long offset) {
			if (offset != null) {
				return offset;
			} else {
				return 0;
			}
		}

		public int getRealLimit(Integer limit) {
			if (limit == null) {
				return ApplicationProperties.REST.DEFAULT_LIMIT;
			} else if (ApplicationProperties.REST.MAX_LIMIT < limit) {
				return ApplicationProperties.REST.MAX_LIMIT;
			} else {
				return limit;
			}
		}
	}

	public static class Builder extends Init<Builder> {
		@Override
		protected Builder self() {
			return this;
		}
	}
}
