package io.codelirium.unifiedpost.qms.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Data
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RESTFailureResponseBody<T> extends RESTResponseBody {

	private static final long serialVersionUID = -8372376590183501108L;


	private T message;


	public RESTFailureResponseBody() {

		super();

	}
}
