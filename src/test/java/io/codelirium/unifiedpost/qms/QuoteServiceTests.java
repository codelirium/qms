package io.codelirium.unifiedpost.qms;

import io.codelirium.unifiedpost.qms.domain.dto.QuoteDTO;
import io.codelirium.unifiedpost.qms.domain.entity.QuoteEntity;
import io.codelirium.unifiedpost.qms.repository.QuoteRepository;
import io.codelirium.unifiedpost.qms.service.QuoteService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Random;

import static ch.qos.logback.core.CoreConstants.EMPTY_STRING;
import static io.codelirium.unifiedpost.qms.util.DomainUtil.getQuoteEntities;
import static io.codelirium.unifiedpost.qms.util.DomainUtil.getQuoteEntity;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static net.bytebuddy.utility.RandomString.make;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.rules.ExpectedException.none;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


public class QuoteServiceTests {

	@Rule
	public ExpectedException expectedException = none();

	@Mock
	private QuoteRepository quoteRepository;

	private QuoteService quoteService;


	{

		initMocks(this);

	}


	@Before
	public void setUp() {

		reset(quoteRepository);

		quoteService = new QuoteService(new ModelMapper(), quoteRepository);

	}


	@Test
	public void testThatNewQuoteComesFromTheRepository() {

		final QuoteEntity theQuoteEntity = getQuoteEntity();


		when(quoteRepository.saveAndFlush(any(QuoteEntity.class))).thenReturn(theQuoteEntity);


		final QuoteDTO inputQuoteDTO = QuoteDTO.builder()
													.text(theQuoteEntity.getText())
													.author(theQuoteEntity.getAuthor())
													.build();

		final QuoteDTO outputQuoteDTO = quoteService.create(inputQuoteDTO);


		assertEquals(inputQuoteDTO.hashCode(), outputQuoteDTO.hashCode());

		verify(quoteRepository, times(1)).saveAndFlush(any(QuoteEntity.class));
	}


	@Test
	public void testThatReadQuoteByIdIsNotSupported() {

		expectedException.expect(UnsupportedOperationException.class);

		quoteService.read(new Random().nextLong());

	}


	@Test
	public void testThatRandomQuoteComesFromTheNonEmptyRepository() {

		final QuoteEntity theQuoteEntity = getQuoteEntity();


		when(quoteRepository.findRandom()).thenReturn(of(theQuoteEntity));


		final QuoteDTO outputQuoteDTO = quoteService.readRandom();

		final QuoteDTO expectedQuoteDTO = QuoteDTO.builder()
													.text(theQuoteEntity.getText())
													.author(theQuoteEntity.getAuthor())
													.build();


		assertEquals(outputQuoteDTO.hashCode(), expectedQuoteDTO.hashCode());

		verify(quoteRepository, times(1)).findRandom();
	}


	@Test
	public void testThatRandomThrowsExceptionForEmptyRepository() {

		when(quoteRepository.findRandom()).thenReturn(empty());

		expectedException.expect(RuntimeException.class);


		quoteService.readRandom();

		verify(quoteRepository, times(1)).findRandom();
	}


	@Test
	public void testThatAllQuotesComeFromTheRepository() {

		final List<QuoteEntity> theQuoteEntities = getQuoteEntities(10);


		when(quoteRepository.findAll()).thenReturn(theQuoteEntities);


		final List<QuoteDTO> outputQuoteDTOs = quoteService.readAll();


		assertEquals(theQuoteEntities.size(), outputQuoteDTOs.size());

		verify(quoteRepository, times(1)).findAll();
	}


	@Test
	public void testThatSearchedQuotesComeFromTheRepository() {

		final List<QuoteEntity> theQuoteEntities = getQuoteEntities(10);


		when(quoteRepository.findBySearchTerm(anyString())).thenReturn(theQuoteEntities);


		final List<QuoteDTO> outputQuoteDTOs = quoteService.readBySearchTerm(make(10));


		assertEquals(theQuoteEntities.size(), outputQuoteDTOs.size());

		verify(quoteRepository, times(1)).findBySearchTerm(anyString());
	}


	@Test
	public void testThatBlankSearchTermResultsToEmptyListResult() {

		final List<QuoteEntity> theQuoteEntities = getQuoteEntities(10);


		when(quoteRepository.findBySearchTerm(anyString())).thenReturn(theQuoteEntities);


		final List<QuoteDTO> outputQuoteDTOs = quoteService.readBySearchTerm(EMPTY_STRING);


		assertThat(outputQuoteDTOs.size(), equalTo(0));

		verify(quoteRepository, times(0)).findBySearchTerm(anyString());
	}


	@Test
	public void testThatUpdatedQuoteComesFromTheRepository() {

		final long quoteId = new Random().nextLong();

		final QuoteEntity theQuoteEntity = getQuoteEntity();

		theQuoteEntity.setId(quoteId);


		when(quoteRepository.saveAndFlush(any(QuoteEntity.class))).thenReturn(theQuoteEntity);


		final QuoteDTO inputQuoteDTO = QuoteDTO.builder()
													.id(quoteId)
													.text(theQuoteEntity.getText())
													.author(theQuoteEntity.getAuthor())
													.build();

		final QuoteDTO outputQuoteDTO = quoteService.update(inputQuoteDTO);


		assertEquals(inputQuoteDTO.hashCode(), outputQuoteDTO.hashCode());

		verify(quoteRepository, times(1)).saveAndFlush(any(QuoteEntity.class));
	}


	@Test
	public void testDeletionOfQuote() {

		quoteService.delete(new Random().nextLong());

		verify(quoteRepository, times(1)).deleteById(anyLong());

	}
}
