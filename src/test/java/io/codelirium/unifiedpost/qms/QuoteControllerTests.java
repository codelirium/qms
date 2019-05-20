package io.codelirium.unifiedpost.qms;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.codelirium.unifiedpost.qms.controller.error.QMSErrorHandler;
import io.codelirium.unifiedpost.qms.controller.handler.QuoteController;
import io.codelirium.unifiedpost.qms.domain.dto.QuoteDTO;
import io.codelirium.unifiedpost.qms.service.QuoteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Random;

import static io.codelirium.unifiedpost.qms.controller.mapping.UrlMappings.*;
import static io.codelirium.unifiedpost.qms.domain.dto.response.builder.RESTResponseBodyBuilder.success;
import static io.codelirium.unifiedpost.qms.util.DomainUtil.getQuoteDTO;
import static io.codelirium.unifiedpost.qms.util.DomainUtil.getQuoteDTOs;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static net.bytebuddy.utility.RandomString.make;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class QuoteControllerTests {

	@Mock
	private QuoteService quoteService;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();


	{

		initMocks(this);

	}


	@Before
	public void setUp() {

		reset(quoteService);

		mockMvc = standaloneSetup(new QuoteController(quoteService))
								.setControllerAdvice(new QMSErrorHandler(objectMapper))
								.build();
	}


	@Test
	public void testThatPostQuoteIsCreatedWithContent() throws Exception {

		final QuoteDTO theQuoteDTO = getQuoteDTO(0L);

		when(quoteService.create(any(QuoteDTO.class))).thenReturn(theQuoteDTO);

		mockMvc.perform(post(API_PATH_ROOT + API_ENDPOINT_QUOTES)
							.content(objectMapper.writeValueAsString(theQuoteDTO))
							.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), singletonList(theQuoteDTO)))));

		verify(quoteService, times(1)).create(any(QuoteDTO.class));
	}


	@Test
	public void testThatGetRandomQuoteIsOkWithContent() throws Exception {

		final QuoteDTO theQuoteDTO = getQuoteDTO(0L);

		when(quoteService.readRandom()).thenReturn(theQuoteDTO);

		mockMvc.perform(get(API_PATH_ROOT + API_ENDPOINT_QUOTES + "/" + PATH_SPECIFIER_RANDOM))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), singletonList(theQuoteDTO)))));

		verify(quoteService, times(1)).readRandom();
	}


	@Test
	public void testThatGetAllQuotesIsOkWithContent() throws Exception {

		final List<QuoteDTO> theQuoteDTOs = getQuoteDTOs(10);

		when(quoteService.readAll()).thenReturn(theQuoteDTOs);

		mockMvc.perform(get(API_PATH_ROOT + API_ENDPOINT_QUOTES))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), theQuoteDTOs))));

		verify(quoteService, times(1)).readAll();
	}


	@Test
	public void testThatGetQuotesBySearchTermIsOkWithContent() throws Exception {

		final List<QuoteDTO> theQuoteDTOs = getQuoteDTOs(10);

		when(quoteService.readBySearchTerm(anyString())).thenReturn(theQuoteDTOs);

		mockMvc.perform(get(API_PATH_ROOT + API_ENDPOINT_QUOTES)
							.param(REQ_PARAM_SEARCH_TERM, make(10)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), theQuoteDTOs))));

		verify(quoteService, times(1)).readBySearchTerm(anyString());
	}


	@Test
	public void testThatPostQuoteIsUpdatedWithContent() throws Exception {

		final QuoteDTO theQuoteDTO = getQuoteDTO(0L);

		when(quoteService.update(any(QuoteDTO.class))).thenReturn(theQuoteDTO);

		mockMvc.perform(post(API_PATH_ROOT + API_ENDPOINT_QUOTES + "/{" + PATH_PARAM_QUOTE_ID + "}", new Random().nextInt())
							.content(objectMapper.writeValueAsString(theQuoteDTO))
							.contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), singletonList(theQuoteDTO)))));

		verify(quoteService, times(1)).update(any(QuoteDTO.class));
	}


	@Test
	public void testThatDeleteQuoteIsOkWithContent() throws Exception {

		final QuoteDTO theQuoteDTO = getQuoteDTO(0L);

		mockMvc.perform(delete(API_PATH_ROOT + API_ENDPOINT_QUOTES + "/{" + PATH_PARAM_QUOTE_ID + "}", new Random().nextInt()))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(content().json(objectMapper.writeValueAsString(success(QuoteDTO.class.getSimpleName(), emptyList()))));

		verify(quoteService, times(1)).delete(anyLong());
	}
}
