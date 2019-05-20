package io.codelirium.unifiedpost.qms.util;

import io.codelirium.unifiedpost.qms.domain.dto.QuoteDTO;
import io.codelirium.unifiedpost.qms.domain.entity.QuoteEntity;
import java.util.List;

import static com.google.common.collect.Lists.newArrayListWithExpectedSize;
import static java.lang.Integer.toUnsignedLong;
import static java.util.stream.IntStream.range;
import static net.bytebuddy.utility.RandomString.make;


public class DomainUtil {

	private DomainUtil() { }


	public static QuoteDTO getQuoteDTO(final Long id) {

		return new QuoteDTO(id, make(10), make(10));

	}


	public static List<QuoteDTO> getQuoteDTOs(final int size) {

		final List<QuoteDTO> quoteDTOs = newArrayListWithExpectedSize(size);

		range(0, size).forEach(i -> quoteDTOs.add(getQuoteDTO(toUnsignedLong(i))));


		return quoteDTOs;
	}


	public static QuoteEntity getQuoteEntity() {

		return new QuoteEntity(make(10), make(10));

	}


	public static List<QuoteEntity> getQuoteEntities(final int size) {

		final List<QuoteEntity> quoteEntities = newArrayListWithExpectedSize(size);

		range(0, size).forEach(i -> quoteEntities.add(getQuoteEntity()));


		return quoteEntities;
	}
}
