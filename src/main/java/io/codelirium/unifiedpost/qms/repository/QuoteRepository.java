package io.codelirium.unifiedpost.qms.repository;

import io.codelirium.unifiedpost.qms.domain.entity.QuoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface QuoteRepository extends JpaRepository<QuoteEntity, Long> {

	String SQL_QUERY_GET_QUOTES_BY_SEARCH_TERM = "SELECT "      +
													"*  "       +
												 "FROM "        +
													"QUOTES Q " +
												 "WHERE "       +
													"LOWER(Q.TEXT) LIKE CONCAT('%', LOWER(?1), '%')";

	@Query(nativeQuery = true, value = SQL_QUERY_GET_QUOTES_BY_SEARCH_TERM)
	List<QuoteEntity> findBySearchTerm(final String searchTerm);


	String SQL_QUERY_GET_RANDOM_QUOTE = "SELECT "       +
											"* "        +
										"FROM "         +
											"QUOTES Q " +
										"ORDER BY "     +
											"RAND() "   +
										"LIMIT 1";

	@Query(nativeQuery = true, value = SQL_QUERY_GET_RANDOM_QUOTE)
	Optional<QuoteEntity> findRandom();

}
