package io.codelirium.unifiedpost.qms;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import io.codelirium.unifiedpost.qms.base.BaseDBTest;
import io.codelirium.unifiedpost.qms.domain.entity.QuoteEntity;
import io.codelirium.unifiedpost.qms.repository.QuoteRepository;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.github.springtestdbunit.annotation.DatabaseOperation.INSERT;
import static java.util.stream.IntStream.range;
import static java.util.stream.IntStream.rangeClosed;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


@ActiveProfiles("TEST")
public class QuoteRepositoryTests extends BaseDBTest {

	@Inject
	private QuoteRepository quoteRepository;


	@Test
	@DatabaseSetup(value = "/data-1.xml", type = INSERT)
	public void testThatRandomQuoteQueryIsCorrect() {

		range(0, 10).forEach(i -> {

			final Optional<QuoteEntity> optionalQuoteEntity = quoteRepository.findRandom();

			assertTrue(optionalQuoteEntity.isPresent());

			assertThat(optionalQuoteEntity.get().getId(), equalTo(1L));

		});
	}


	@Test
	@DatabaseSetup(value = "/data-5.xml", type = INSERT)
	public void testThatSearchQuotesByTermQueryIsCorrectCaseOne() {

		final List<QuoteEntity> quoteEntities = quoteRepository.findBySearchTerm("from");

		assertThat(quoteEntities.size(), equalTo(5));

	}


	@Test
	@DatabaseSetup(value = "/data-5.xml", type = INSERT)
	public void testThatSearchQuotesByTermQueryIsCorrectCaseTwo() {

		rangeClosed(1, 5).forEach(i -> {

			final List<QuoteEntity> quoteEntities = quoteRepository.findBySearchTerm(String.valueOf(i));

			assertThat(quoteEntities.size(), equalTo(1));

		});
	}
}
