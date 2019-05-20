package io.codelirium.unifiedpost.qms.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.util.Collection;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "status", "type", "count", "data" })
public class RESTSuccessResponseBody<T> extends RESTResponseBody {

	private static final long serialVersionUID = -829911228038138150L;


	private Integer       count;

	private Collection<T> data;


	public RESTSuccessResponseBody() {

		super();

	}
}
