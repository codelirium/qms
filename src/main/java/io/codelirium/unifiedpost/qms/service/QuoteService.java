package io.codelirium.unifiedpost.qms.service;

import io.codelirium.unifiedpost.qms.domain.dto.QuoteDTO;
import io.codelirium.unifiedpost.qms.domain.entity.QuoteEntity;
import io.codelirium.unifiedpost.qms.repository.QuoteRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.util.Assert.notNull;


@Service
@Transactional
public class QuoteService {

	private static final Logger LOGGER = getLogger(QuoteService.class);


	private ModelMapper modelMapper;

	private QuoteRepository quoteRepository;


	@Inject
	public QuoteService(final ModelMapper modelMapper, final QuoteRepository quoteRepository) {

		this.modelMapper = modelMapper;

		this.quoteRepository = quoteRepository;

	}


	public QuoteDTO create(final QuoteDTO quoteDTO) {

		notNull(quoteDTO, "The quote DTO cannot be null.");


		final QuoteEntity actualQuoteEntity = modelMapper.map(quoteDTO, QuoteEntity.class);

		final QuoteEntity persistedQuoteEntity = quoteRepository.saveAndFlush(actualQuoteEntity);


		return modelMapper.map(persistedQuoteEntity, QuoteDTO.class);
	}



	public QuoteDTO read(final Long quoteId) {

		throw new UnsupportedOperationException("The read operation is not supported for the quote resource.");

	}


	public QuoteDTO readRandom() {

		return quoteRepository.findRandom()
								.map(quoteEntity -> modelMapper.map(quoteEntity, QuoteDTO.class))
								.orElseThrow(() -> new RuntimeException("No quote could be found."));

	}


	public List<QuoteDTO> readAll() {

		return quoteRepository.findAll()
								.parallelStream()
									.map(quoteEntity -> modelMapper.map(quoteEntity, QuoteDTO.class))
									.collect(toList());
	}


	public List<QuoteDTO> readBySearchTerm(final String searchTerm) {

		notNull(searchTerm, "The search term cannot be null.");


		return !searchTerm.isBlank() ? quoteRepository.findBySearchTerm(searchTerm)
												.parallelStream()
													.map(quoteEntity -> modelMapper.map(quoteEntity, QuoteDTO.class))
													.collect(toList())
									: emptyList();
	}


	public QuoteDTO update(final QuoteDTO quoteDTO) {

		notNull(quoteDTO, "The quote DTO cannot be null.");


		final QuoteEntity actualQuoteEntity = modelMapper.map(quoteDTO, QuoteEntity.class);

		final QuoteEntity persistedQuoteEntity = quoteRepository.saveAndFlush(actualQuoteEntity);


		return modelMapper.map(persistedQuoteEntity, QuoteDTO.class);
	}


	public void delete(final Long quoteId) {

		notNull(quoteId, "The quote id cannot be null.");


		quoteRepository.deleteById(quoteId);
	}
}
