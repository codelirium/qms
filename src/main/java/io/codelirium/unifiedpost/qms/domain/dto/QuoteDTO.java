package io.codelirium.unifiedpost.qms.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode.PROPERTIES;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@Builder
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = QuoteDTO.QuoteDTOBuilder.class)
@AllArgsConstructor(onConstructor = @__(@JsonCreator(mode = PROPERTIES)))
public class QuoteDTO implements Serializable {

	private static final long serialVersionUID = -8258185855202945067L;


	private Long id;

	private String text;

	private String author;

	@JsonPOJOBuilder(withPrefix = "")
	public static class QuoteDTOBuilder { }
}
