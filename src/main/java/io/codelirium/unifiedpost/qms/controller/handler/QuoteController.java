package io.codelirium.unifiedpost.qms.controller.handler;

import io.codelirium.unifiedpost.qms.domain.dto.QuoteDTO;
import io.codelirium.unifiedpost.qms.domain.dto.response.RESTSuccessResponseBody;
import io.codelirium.unifiedpost.qms.service.QuoteService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.List;

import static io.codelirium.unifiedpost.qms.controller.mapping.UrlMappings.*;
import static io.codelirium.unifiedpost.qms.domain.dto.response.builder.RESTResponseBodyBuilder.success;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping(API_PATH_ROOT)
public class QuoteController {

	private static final Logger LOGGER = getLogger(QuoteController.class);


	private QuoteService quoteService;


	@Inject
	public QuoteController(final QuoteService quoteService) {

		this.quoteService = quoteService;

	}


	@ResponseStatus(CREATED)
	@PostMapping(value = API_ENDPOINT_QUOTES, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> create(@RequestBody final QuoteDTO quoteDTO) {

		final QuoteDTO createdQuote = quoteService.create(quoteDTO);


		LOGGER.debug("Building response for quote creation ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), singletonList(createdQuote));

		LOGGER.debug("Response for quote creation was built successfully.");


		return new ResponseEntity<>(body, CREATED);
	}


	public QuoteDTO read(final Long quoteId) {

		return quoteService.read(quoteId);

	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_QUOTES + "/" + PATH_SPECIFIER_RANDOM, produces = APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> readRandom() {

		final QuoteDTO randomQuote = quoteService.readRandom();


		LOGGER.debug("Building response for random quote retrieval ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), singletonList(randomQuote));

		LOGGER.debug("Response for random quote retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_QUOTES, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> readAll() {

		final List<QuoteDTO> allQuotes = quoteService.readAll();


		LOGGER.debug("Building response for all quotes retrieval ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), allQuotes);

		LOGGER.debug("Response for all quotes retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@GetMapping(value = API_ENDPOINT_QUOTES, params = REQ_PARAM_SEARCH_TERM, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> readBySearchTerm(@RequestParam(REQ_PARAM_SEARCH_TERM) final String searchTerm) {

		final List<QuoteDTO> searchedQuotes = quoteService.readBySearchTerm(searchTerm);


		LOGGER.debug("Building response for searched quotes retrieval ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), searchedQuotes);

		LOGGER.debug("Response for searched quotes retrieval was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@PostMapping(value = API_ENDPOINT_QUOTES + "/{" + PATH_PARAM_QUOTE_ID + "}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> update(@PathVariable final Long quoteId, @RequestBody final QuoteDTO quoteDTO) {

		quoteDTO.setId(quoteId);

		final QuoteDTO updatedQuote = quoteService.update(quoteDTO);


		LOGGER.debug("Building response for quote update ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), singletonList(updatedQuote));

		LOGGER.debug("Response for quote update was built successfully.");


		return new ResponseEntity<>(body, OK);
	}


	@ResponseStatus(OK)
	@DeleteMapping(value = API_ENDPOINT_QUOTES + "/{" + PATH_PARAM_QUOTE_ID + "}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<RESTSuccessResponseBody<QuoteDTO>> delete(@PathVariable final Long quoteId) {

		quoteService.delete(quoteId);


		LOGGER.debug("Building response for quote deletion ...");

		final RESTSuccessResponseBody<QuoteDTO> body = success(QuoteDTO.class.getSimpleName(), emptyList());

		LOGGER.debug("Response for quote deletion was built successfully.");


		return new ResponseEntity<>(body, OK);
	}
}
